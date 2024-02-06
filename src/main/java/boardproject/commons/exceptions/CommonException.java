package boardproject.commons.exceptions;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.http.HttpStatus;
public class CommonException extends RuntimeException{
    private HttpStatus status;

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
}
