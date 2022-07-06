package themion7.my_chat.backend.dto.validation;

import javax.validation.ConstraintValidatorContext;

public class ValidationUtils {
    public static final String 
        REGEXP = "^[a-zA-Z_][a-zA-Z0-9_]*$",
        chatroomTitleLengthMsg = "채팅방의 제목 이름은 3자 이상 100자 이하여야 합니다.",
        duplicateUsernameMsg = "이미 존재하는 사용자 이름입니다.",
        usernameNotExistMsg = "존재하지 않는 사용자 이름입니다.",
        notBlankMsg = "필수 항목입니다.",
        patternMsg = "알파벳 대소문자와 숫자, 밑줄만 사용 가능합니다.",
        usernameLengthMsg = "6자 이상 30자 이하여야 합니다.";

    public static boolean setContextViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
            message
        ).addConstraintViolation();

        return false;
    }

    public static boolean setContextViolation(ConstraintValidatorContext context) {
        return ValidationUtils.setContextViolation(context, context.getDefaultConstraintMessageTemplate());
    }
}
