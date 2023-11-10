package jwtusermange.jwtusermange.controller.user;

import jwtusermange.jwtusermange.dto.token.TokenDto;
import jwtusermange.jwtusermange.dto.user.UpdateDto;
import jwtusermange.jwtusermange.dto.user.UserDto;
import jwtusermange.jwtusermange.service.auth.AuthenticationService;
import jwtusermange.jwtusermange.service.token.TokenService;
import jwtusermange.jwtusermange.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        TokenDto jwt = userService.login(userDto);
        return jwt != null ?
                ResponseEntity.ok(jwt) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        String signupResult = userService.save(userDto);
        return "error".equals(signupResult) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하는 이메일 입니다.") :
                ResponseEntity.ok(signupResult);
    }

    @PostMapping("/out")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return tokenService.extractToken(request.getHeader("Authorization"))
                .map(token -> {
                    tokenService.blacklistToken(token);
                    return ResponseEntity.ok("로그아웃 성공");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰을 찾을 수 없습니다."));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAddress(@RequestBody UpdateDto updateDTO) {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String updateResult = userService.update(currentUserEmail, updateDTO.getNewAddress());


        if (updateResult == "error") {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.");
        }
        else {
            return ResponseEntity.ok("주소를 바꿨습니다.");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete() {
        String currentUserEmail = authenticationService.getCurrentAuthenticatedUserEmail();
        String deleteResult = userService.delete(currentUserEmail);
        return "error".equals(deleteResult) ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.") :
                ResponseEntity.ok("회원탈퇴 완료");
    }

}
