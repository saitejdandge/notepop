package in.notepop.server.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noteId;
    private String value;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    //    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "id")
//    private User user;

    @Column(nullable = false)
    private Long userId;
}
