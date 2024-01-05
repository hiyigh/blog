package blog.category.adapter.input;

public class InvalidCategoryRequestException extends RuntimeException{
    public InvalidCategoryRequestException(String message) {
        super(message);
    }
}
