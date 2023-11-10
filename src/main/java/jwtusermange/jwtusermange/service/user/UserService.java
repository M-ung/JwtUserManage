package jwtusermange.jwtusermange.service.user;

import jwtusermange.jwtusermange.dto.token.TokenDto;
import jwtusermange.jwtusermange.dto.user.UserDto;
import jwtusermange.jwtusermange.entity.user.User;
import jwtusermange.jwtusermange.repository.user.UserRepository;
import jwtusermange.jwtusermange.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L; // 1시간

    public String save(UserDto userDto) {
        Optional<User> byUserEmail = userRepository.findByUserEmail(userDto.getUserEmail());
        if (byUserEmail.isPresent()) {
            return "error 1";
        }
        User user = User.builder()
                .userName(userDto.getUserName())
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword())) // 암호
                .userAddress(userDto.getUserAddress())
                .userMileage(0)
                .build();

        userRepository.save(user);
        return "success";
    }

    public TokenDto login(UserDto userDto) {
        Optional<User> byUserEmail = userRepository.findByUserEmail(userDto.getUserEmail());
        if(byUserEmail.isPresent()) {
            if (passwordEncoder.matches(userDto.getUserPassword(), byUserEmail.get().getUserPassword())) {
                System.out.println("[LOIGN]");
                System.out.println(userDto.getUserEmail());
                System.out.println(secretKey);
                System.out.println(expiredMs);
                String jwt = JwtUtil.createJwt(userDto.getUserEmail(), secretKey, expiredMs);
                return new TokenDto(jwt); // TokenDto 객체 생성하여 반환
            }
        }
        return null;
    }

    public String update(String currentUserEmail, String newAddress) {
        Optional<User> user = userRepository.findByUserEmail(currentUserEmail);
        if(!user.isPresent()) {
            return "error";
        }
        else {
            user.get().setUserAddress(newAddress);
            userRepository.save(user.get());
            return "success";
        }
    }

    public String delete(String currentUserEmail) {
        Optional<User> user = userRepository.findByUserEmail(currentUserEmail);
        if(!user.isPresent()) {
            return "error";
        }
        else {
            userRepository.delete(user.get());
            return "success";
        }
    }
}
