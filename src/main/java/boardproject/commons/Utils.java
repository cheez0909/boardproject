package boardproject.commons;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Utils {

    /**
     * 스태틱 구간에 초기화를 시킴
     */
    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    /**
     *
     * @param code : 키값
     * @param type : 메세지 타입 commons, errors, validations
     * @return
     * 타입에 따라서 다른 번들을 가져옴
     */
    public static String getMessage(String code, String type){
        type = StringUtils.hasText(type) ? type : "validations";

        ResourceBundle bundle = null;

        if(type.equals("commons")){
            bundle =commonsBundle;
        } else if(type.equals("errors")){
            bundle=errorsBundle;
        } else {
            bundle=validationsBundle;
        }
        return bundle.getString(code);
    }

    public static Map<String, List<String>> getMessages(Errors errors) {
        try {
            Map<String, List<String>> data = new HashMap<>();
            for (FieldError error : errors.getFieldErrors()) {
                String field = error.getField();
                List<String> messages = Arrays.stream(error.getCodes()).sorted(Comparator.reverseOrder())
                        .map(c -> getMessage(c, "validation"))
                        .filter(c -> c != null)
                        .toList();

                data.put(field, messages);
            }

            return data;

        } catch (Exception e) {
            return null;
        }


    }
}
