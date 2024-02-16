package boardproject.admin.theater.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Theater {
    @Id
    @GeneratedValue
    private Long seq;
    private String mTheater; // 극장 명
    private String mLocation; // 극장 주소

    private String section; // 1관, 2관, 3관..
}
