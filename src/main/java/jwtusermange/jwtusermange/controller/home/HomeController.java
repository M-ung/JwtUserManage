package jwtusermange.jwtusermange.controller.home;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    // 메인 페이지로 이동
    @GetMapping("/main")
    public ResponseEntity<String> main(Authentication authentication) {
        return ResponseEntity.ok().body("인증된 유저는: " + authentication.getName());
    }

}
