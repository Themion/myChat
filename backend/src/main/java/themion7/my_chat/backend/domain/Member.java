package themion7.my_chat.backend.domain;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
}
