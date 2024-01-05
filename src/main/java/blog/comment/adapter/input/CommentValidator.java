package blog.comment.adapter.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentValidator implements Validator {
    private final SpringValidatorAdapter springValidatorAdapter;
    @Override
    public boolean supports(Class<?> clazz) {
        return !List.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        springValidatorAdapter.validate(target,errors);
        if (errors.hasErrors()) throw new RuntimeException(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
    }
}
