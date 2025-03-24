package com.metacoding.bankv1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServeice {
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
}
