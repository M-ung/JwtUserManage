package jwtusermange.jwtusermange.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMyPageDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userAddress;
    private Integer userMileage;
}
