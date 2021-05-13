package in.notepop.server.session;


import in.notepop.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor

@Entity
@Table(name = "session")
public class Session {

    public Session() {
        expiry = LocalDate.of(2018, 12, 2);
        refreshToken = new Date().getTime() + "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate expiry;

    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
