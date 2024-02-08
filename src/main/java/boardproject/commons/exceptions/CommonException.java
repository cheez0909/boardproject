package boardproject.commons.exceptions;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;

public class CommonException extends RuntimeException{
    private HttpStatus status;

    private Map<String, List<String>> messages;

    public CommonException(Map<String, List<String>> messages, HttpStatus status) {
        super();
        this.status = status;
        this.messages = messages;
    }


    public CommonException(HttpStatus status){
        super();
        this.status = status;
    }

    public CommonException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return this.status;
    }
    public Map<String, List<String>> getMessages() {
        return messages;
    }
}
