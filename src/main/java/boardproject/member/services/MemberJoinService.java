package boardproject.member.services;

import boardproject.member.MemberType;
import boardproject.member.controllers.RequestJoin;
import boardproject.member.entites.Member;
import boardproject.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(RequestJoin join){
        Member member = new ModelMapper().map(join, Member.class);
        String password = passwordEncoder.encode(join.password());
        member.setPassword(password);
        member.setType(MemberType.USER);

        save(member);
    }

    public void save(Member member) {
        memberRepository.saveAndFlush(member);
    }
}
