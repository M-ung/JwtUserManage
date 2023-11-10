package jwtusermange.jwtusermange.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateDto {
    private Long userId;
    private String newAddress;
}
