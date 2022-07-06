package themion7.my_chat.backend.dto.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.Payload;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.repository.MemberRepository;

import static themion7.my_chat.backend.dto.validation.ValidationUtils.*;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default duplicateUsernameMsg;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
@AllArgsConstructor
class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        boolean result = memberRepository.findByUsername(username).isEmpty();
        if (!result) return setContextViolation(context);
        return result;
    }
    
}

