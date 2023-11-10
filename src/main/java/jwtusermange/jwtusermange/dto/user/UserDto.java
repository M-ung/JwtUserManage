package jwtusermange.jwtusermange.dto.user;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userAddress;
}
