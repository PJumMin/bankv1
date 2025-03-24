package com.metacoding.bankv1.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    // 유저 네임 찾기
    public User findByUsername(String username) {
        Query query = em.createNativeQuery("SELECT * FROM user_tb WHERE username=?", User.class);
        query.setParameter(1, username);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }


    }

    public void save(String username, String password, String fullname) {
        Query query = em.createNativeQuery("insert into user_tb (username, password,fullname,created_at) values (?, ?, ?,now())");
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, fullname);
        query.executeUpdate();
    }
}
