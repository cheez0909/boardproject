package boardproject.member.controllers;

import boardproject.commons.ExceptionProcessor;
import boardproject.commons.JSONData;
import boardproject.commons.Utils;
import boardproject.commons.exceptions.BadRequestException;
import boardproject.member.MemberInfo;
import boardproject.member.entites.Member;
import boardproject.member.services.MemberLoginService;
import boardproject.member.services.MemberSaveService;
import jakarta.persistence.PostRemove;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController implements ExceptionProcessor {

    private final MemberSaveService saveService;
    private final MemberLoginService memberLoginService;

    @PostMapping
    public ResponseEntity<JSONData<Object>> join(@RequestBody @Valid RequestJoin form, Errors errors){
        saveService.save(form, errors);

        errorProcess(errors);

        JSONData<Object> data = new JSONData<>();
        data.setStatus(HttpStatus.CREATED);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping("/token")
    public ResponseEntity<JSONData<Object>> tocken(@RequestBody @Valid RequestLogin form, Errors errors){
        errorProcess(errors);

        String accessTocken = memberLoginService.login(form);

        JSONData<Object> data = new JSONData<>(HttpStatus.OK, true, accessTocken, null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + accessTocken);

        return ResponseEntity.status(data.getStatus()).headers(headers).body(data);
    }

    @GetMapping("/info")
    public JSONData<Object> info(@AuthenticationPrincipal MemberInfo memberInfo){
        Member member = memberInfo.getMember();

        JSONData<Object> data = new JSONData<>();
        data.setData(member);

        return data;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "관리자 페이지 접속....";
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(Utils.getMessages(errors));
        }
    }
}

