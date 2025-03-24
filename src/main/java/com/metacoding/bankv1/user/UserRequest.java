package com.metacoding.bankv1.user;


import lombok.Data;

// 데이터를 클래스로 받기
public class UserRequest {

    @Data // Getter, Setter, toString
    public static class JoinDTO {
        private String username;
        private String password;
        private String fullname;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

}
