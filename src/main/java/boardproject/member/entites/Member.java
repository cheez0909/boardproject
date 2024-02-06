package boardproject.member.entites;

import boardproject.commons.entities.BaseEntity;
import boardproject.member.MemberType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long seq;

    @Column(length = 60, unique = true, nullable = false)
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private MemberType type = MemberType.USER;
}
