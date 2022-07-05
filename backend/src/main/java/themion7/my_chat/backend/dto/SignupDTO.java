package themion7.my_chat.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import themion7.my_chat.backend.dto.validation.UniqueUsername;

import static themion7.my_chat.backend.dto.validation.ValidationUtils.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignupDTO {

    @NotBlank(message = notBlankMsg)
    @Size(min = 6, max = 30, message = usernameLengthMsg)
    @Pattern(regexp = REGEXP, message = patternMsg)
    @UniqueUsername
    private String username;

    @NotBlank(message = notBlankMsg)
    @Pattern(regexp = REGEXP, message = patternMsg)
    private String password;

    @NotBlank(message = notBlankMsg)
    @Pattern(regexp = REGEXP, message = patternMsg)
    @JsonProperty(value = "password_check")
    private String passwordCheck;

}
