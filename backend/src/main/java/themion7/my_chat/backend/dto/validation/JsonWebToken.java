package themion7.my_chat.backend.dto.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.repository.MemberRepository;

import static themion7.my_chat.backend.dto.validation.ValidationUtils.*;

@Constraint(validatedBy = TokenValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonWebToken {
    String message() default usernameNotExistMsg;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
@AllArgsConstructor
class TokenValidator implements ConstraintValidator<JsonWebToken, String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String token, ConstraintValidatorContext context) {
        DecodedJWT decode;

        if (token.split(".").length != 3)
            return setContextViolation(context, "토큰의 형식이 잘못되었습니다.");

        try {
            decode = JWT.decode(token);
        } catch (Exception e) {
            return setContextViolation(context, "토큰 해독에 실패하였습니다.");
        }

        if (decode.getExpiresAt().getTime() < System.currentTimeMillis())
            return setContextViolation(context, "이미 만료된 토큰입니다.");
    
        String username = decode.getSubject();
        if (memberRepository.findByUsername(username).isEmpty())
            return setContextViolation(context);
        
        return true;
    }
    
}

