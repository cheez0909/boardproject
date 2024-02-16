package boardproject.admin.musical.entites;


import boardproject.commons.entities.BaseMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class Musical extends BaseMember {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 100, nullable = false)
    private String mTitle; // 뮤지컬 명
    private String sDate; // 개막 일자
    private String eDate; // 막공 일자
    private boolean active; // 노출 여부



}
