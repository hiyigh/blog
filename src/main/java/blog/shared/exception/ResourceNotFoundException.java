package blog.shared.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException{
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // for rest
    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.NOT_FOUND;
    };
    // for monitoring
    @Override
    public boolean isNecessaryToLog(){
        return false;
    };
}
