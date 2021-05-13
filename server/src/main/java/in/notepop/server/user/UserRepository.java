package in.notepop.server.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Modifying
//    @Query("create table app_user(id serial primary key, name text);")
//    void createUserTable();
//
//    @Modifying
//    @Query("create table note(id serial primary key, value text,user_id int, foreign key(user_id) references app_user(id) on delete set null);")
//    void createNoteTable();
//
//    @Modifying
//    @Query("create table session(id serial primary key,expiry date,refresh_token text);")
//    void createSessionTable();
}
