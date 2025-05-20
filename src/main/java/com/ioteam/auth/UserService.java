package com.ioteam.auth;

import com.ioteam.auth.dto.LoginRequest;
import com.ioteam.auth.dto.SignupRequest;
import com.ioteam.auth.dto.AuthResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getPhone())
            .birthDate(request.getBirthDate())
            .gender(User.Gender.valueOf(request.getGender().toUpperCase()))
            .address(request.getAddress())
            .profileImage(request.getProfileImage())
            .role(User.Role.GUARDIAN)
            .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateToken(user.getEmail());
        String refreshToken = jwtProvider.generateToken(user.getEmail());

        return new AuthResponse(
            accessToken,
            refreshToken,
            user.getId(),
            user.getName(),
            user.getRole().name()
        );
    }
}
