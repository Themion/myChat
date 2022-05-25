package themion7.my_chat.backend.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignupDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @JsonProperty(value = "password_check")
    private String passwordCheck;

}
