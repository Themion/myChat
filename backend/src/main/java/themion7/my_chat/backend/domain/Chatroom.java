package themion7.my_chat.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Chatroom {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String title;

    @Column
    @Builder.Default
    private Long population = 0L;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @Builder.Default
    private List<MemberChatroom> members = new ArrayList<>();

}
