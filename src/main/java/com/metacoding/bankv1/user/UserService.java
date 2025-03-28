package com.metacoding.bankv1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 있는지 검사
        User user = userRepository.findByUsername(joinDTO.getUsername());
        // 2. 있으면, exception 터트리기
        if (user != null) throw new RuntimeException("동일한 username가 있습니다");
        // 3. 없으면 회원가입하기
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }

    // 로그인
    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. 해당 username이 있나?
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 필터링 (username, password가 불일치하는 것들)
        if (user == null) {
            throw new RuntimeException("해당 Username이 없습니다");
        }

        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
            throw new RuntimeException("해당 Password가 틀렸습니다");
        }
        return user;
    }


}
