package com.ioteam.auth;

import com.ioteam.domain.user.dto.SeniorRegisterRequest;
import com.ioteam.domain.user.dto.SeniorRegisterResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.entity.User.Gender;
import com.ioteam.domain.user.entity.User.Role;
import com.ioteam.domain.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User processOAuth2User(Long kakaoId, String email, String name) {
        return userRepository.findByKakaoId(kakaoId)
            .orElseGet(() -> {
                User newUser = User.createKakaoUser(kakaoId, name, email);
                return userRepository.save(newUser);
            });
    }

    public SeniorRegisterResponse registerSenior(SeniorRegisterRequest request, Long guardianId) {
        User guardian = userRepository.findById(guardianId)
            .orElseThrow(() -> new UsernameNotFoundException("보호자를 찾을 수 없습니다."));

        User senior = User.builder()
            .name(request.getName())
            .birthDate(LocalDate.parse(request.getBirthDate()))
            .gender(Gender.valueOf(request.getGender()))
            .address(request.getAddress())
            .medicalHistory(request.getMedicalHistory())
            .bloodType(request.getBloodType())
            .profileImage(request.getProfileImage())
            .role(Role.SENIOR)
            .guardian(guardian)
            .build();

        userRepository.save(senior);

        return new SeniorRegisterResponse(
            senior.getId(),
            senior.getName(),
            senior.getBirthDate().toString(),
            senior.getGender().name(),
            senior.getAddress(),
            senior.getMedicalHistory(),
            senior.getBloodType(),
            senior.getProfileImage()
        );
    }

    public SeniorRegisterResponse updateSenior(Long seniorId, SeniorRegisterRequest request, Long guardianId) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new IllegalArgumentException("피보호자를 찾을 수 없습니다."));

        if (!senior.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 피보호자에 대한 권한이 없습니다.");
        }

        senior.updateSeniorInfo(
            request.getName(),
            LocalDate.parse(request.getBirthDate()),
            Gender.valueOf(request.getGender()),
            request.getAddress(),
            request.getMedicalHistory(),
            request.getBloodType(),
            request.getProfileImage()
        );

        userRepository.save(senior);

        return new SeniorRegisterResponse(
            senior.getId(),
            senior.getName(),
            senior.getBirthDate().toString(),
            senior.getGender().name(),
            senior.getAddress(),
            senior.getMedicalHistory(),
            senior.getBloodType(),
            senior.getProfileImage()
        );
    }

    public void deleteSenior(Long seniorId, Long guardianId) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new IllegalArgumentException("피보호자를 찾을 수 없습니다."));

        if (!senior.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 피보호자에 대한 권한이 없습니다.");
        }

        userRepository.delete(senior);
    }

    public List<SeniorRegisterResponse> findSeniorsByGuardian(Long guardianId) {
        List<User> seniors = userRepository.findByGuardianIdAndRole(guardianId, Role.SENIOR);

        return seniors.stream().map(senior -> new SeniorRegisterResponse(
            senior.getId(),
            senior.getName(),
            senior.getBirthDate().toString(),
            senior.getGender().name(),
            senior.getAddress(),
            senior.getMedicalHistory(),
            senior.getBloodType(),
            senior.getProfileImage()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void updateFirebaseToken(Long userId, String token) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateFirebaseToken(token);
    }
}