package boardproject.member.services;

import boardproject.member.MemberType;
import boardproject.member.controllers.RequestJoin;
import boardproject.member.entites.Member;
import boardproject.member.repositories.MemberRepository;
import boardproject.member.validators.JoinValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JoinValidator joinValidator;

    public void save(RequestJoin form, Errors errors) {
        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        String hash = passwordEncoder.encode(form.password());
        Member member = Member.builder()
                .email(form.email())
                .name(form.name())
                .password(hash)
                .mobile(form.mobile())
                .type(MemberType.USER)
                .build();

//        Member member = new ModelMapper().map(form, Member.class);
//        member.setPassword(hash);
//        member.setType(MemberType.USER);

        save(member);
    }

    public void save(Member member) {
        if (member != null) {
            String mobile = member.getMobile();

            if (mobile != null) {
                mobile = mobile.replaceAll("\\D", "");
                member.setMobile(mobile);
            }

            repository.saveAndFlush(member);
        }
    }
}
