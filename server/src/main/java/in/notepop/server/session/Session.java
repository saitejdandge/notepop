package in.notepop.server.session;


import in.notepop.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime expiry;
    private String refreshToken;
    private String accessToken;
    private String role;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "username")
    private User user;
    private String userType;

    public static class SessionBuilder {
        private Long id;
        private ZonedDateTime expiry;
        private String refreshToken;
        private String accessToken;
        private String role;
        private User user;
        private String userType;

        public SessionBuilder id(Long id) {
            this.id = id;
            return this;
        }


        public SessionBuilder expiry(ZonedDateTime expiry) {
            this.expiry = expiry;
            return this;
        }


        public SessionBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }


        public SessionBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        public SessionBuilder role(String role) {
            this.role = role;
            return this;
        }


        public SessionBuilder user(User user) {
            this.user = user;
            return this;
        }


        public SessionBuilder userType(String userType) {
            this.userType = userType;
            return this;
        }


        public Session build() {
            return new Session(id, expiry, refreshToken, accessToken, role, user, userType);
        }


    }

}
