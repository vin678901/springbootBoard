package com.springbootBoard.dto;

import com.springbootBoard.constant.Role;
import com.springbootBoard.entity.BaseTimeEntity;
import com.springbootBoard.entity.BoardUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserFormDto extends BaseTimeEntity {

    @NotNull(message = "이메일을 입력해 주세요")
    private String email;

    @NotNull(message = "비밀번호를 입력해 주세요")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotNull(message = "이름을 입력해 주세요")
    private String name;

    @NotNull(message = "닉네임을 입력해 주세요")
    private String nickName;

    @NotNull(message = "일반 회원인지 모임장인지 선택해 주세요")
    private Role role;

    public BoardUser toEntity() {
        return BoardUser.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickName(nickName)
                .role(role)
                .build();
    }
}
