package com.springbootBoard.entity;

import com.springbootBoard.constant.Role;
import com.springbootBoard.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Length(min = 1, max = 12, message = "닉네임은 12글자 이하로 입력해 주세요.")
    private String nickName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    private List<Long> likedList;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public BoardUser(String name, String nickName, String email, String password, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateBoardUser(UserUpdateDto userUpdateDto) {
        this.name = userUpdateDto.getName();
        this.nickName = userUpdateDto.getNickName();
        this.password = userUpdateDto.getPassword();
    }

}
