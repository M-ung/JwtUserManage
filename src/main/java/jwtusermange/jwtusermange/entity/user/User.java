package jwtusermange.jwtusermange.entity.user;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 유저 고유 식별자

    @Column(nullable = false, length = 50)
    private String userName; // 유저 이름

    @Column(nullable = false, unique = true, length = 100)
    private String userEmail; // 유저 이메일

    @Column(nullable = false, length = 100)
    private String userPassword; // 유저 비밀번호

    @Column(nullable = false, length = 200)
    private String userAddress; // 유저 주소

    @Column(nullable = false)
    private Integer userMileage; // 유저 마일리지

}
