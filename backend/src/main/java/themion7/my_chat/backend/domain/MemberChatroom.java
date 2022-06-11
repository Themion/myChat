package themion7.my_chat.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"member_id", "chatroom_id"}
    )
})
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class MemberChatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chatroom chatroom;
}
