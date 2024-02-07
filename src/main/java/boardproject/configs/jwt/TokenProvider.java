package boardproject.configs.jwt;

import boardproject.commons.Utils;
import boardproject.member.MemberInfo;
import boardproject.member.services.MemberInfoService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class TokenProvider {

    private final String secret;
    private final long tokenValidityInSeconds;

    private Key key;

    @Autowired
    private MemberInfoService memberInfoService;

    public TokenProvider(String secret, long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;

        // 시크릿 값을 복호화(decode) 하여 키 변수에 할당
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds * 1000);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS512) // HMAC + SHA512
                .setExpiration(validity)
                .compact();
    }


    /**
     * 토큰을 받아 클레임을 생성
     * 클레임에서 권한 정보를 가져와서 시큐리티 UserDetails 객체를 만들고
     * Authentication 객체 반환
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getPayload();

        String email = claims.getSubject();

        MemberInfo userDetails = (MemberInfo)memberInfoService.loadUserByUsername(email);

        String auth = claims.get("auth").toString();
        List<? extends GrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new).toList();
        userDetails.setAuthorities(authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, authorities);

        return authentication;

    }

    /**
     * 토큰 유효성 체크
     *
     * @param token
     * @return
     */
    public void validateToken(String token) throws BadRequestException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new BadRequestException(Utils.getMessage("EXPIRED.JWT_TOKEN", "validation"));
        } catch (UnsupportedJwtException e) {
            throw new BadRequestException(Utils.getMessage("UNSUPPORTED.JWT_TOKEN", "validation"));
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException e) {
            throw new BadRequestException(Utils.getMessage("INVALID_FORMAT.JWT_TOKEN", "validation"));
        }
    }
}