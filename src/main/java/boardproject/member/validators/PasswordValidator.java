package boardproject.member.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PasswordValidator {
    default boolean alphaCheck(String password, boolean caseIncentive){
        if(caseIncentive){
            Pattern pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(password).find();
        }

        Pattern pattern1 = Pattern.compile("[a-z]+");
        Pattern pattern2 = Pattern.compile("[A-Z]+");
        return pattern1.matcher(password).find() && pattern2.matcher(password).find();
    }

    /**
     * 숫자가 포함된 패턴인지 체크
     *
     * @param password
     * @return
     */
    default boolean numberCheck(String password) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    /**
     * 특수문자가 포함된 패턴인지 체크
     * @param password
     * @return
     */
    default boolean specialCharsCheck(String password) {
        Pattern pattern = Pattern.compile("[`~!#$%\\^&\\*()-_+=]+");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
