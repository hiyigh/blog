package blog.category.outsideApp.input;

public class InvalidCategoryRequestException extends RuntimeException{
    public InvalidCategoryRequestException(String message) {
        super(message);
    }
}
