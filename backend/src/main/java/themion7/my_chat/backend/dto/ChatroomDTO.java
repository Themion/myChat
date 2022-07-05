package themion7.my_chat.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static themion7.my_chat.backend.dto.validation.ValidationUtils.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChatroomDTO {
    @NotBlank(message = notBlankMsg)
    @Size(min = 3, max = 100, message = chatroomTitleLengthMsg)
    private String title;
}
