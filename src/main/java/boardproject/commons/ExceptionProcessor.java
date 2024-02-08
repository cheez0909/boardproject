package boardproject.commons;

import boardproject.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

public interface ExceptionProcessor {
    @ExceptionHandler(Exception.class)
    default ResponseEntity<JSONData<Object>> errorHandler(Exception e){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        } else if(e instanceof BadCredentialsException){
            status = HttpStatus.UNAUTHORIZED;
        } else if(e instanceof AccessDeniedException){
            status = HttpStatus.FORBIDDEN;
        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setMessage(e.getMessage());
        data.setStatus(status);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }
}
