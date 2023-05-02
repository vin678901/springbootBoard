package com.springbootBoard.dto;

import com.springbootBoard.entity.BoardUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserUpdateDto {

    @NotNull(message = "변경할 이름을 입력해 주세요.")
    private String name;

    @NotNull(message = "변경할 닉네임을 입력해 주세요.")
    @Length(min = 1, max = 12, message = "닉네임은 12글자 이하로 입력해 주세요.")
    private String nickName;

    @NotNull(message = "변경할 비밀번호를 입력해 주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    public BoardUser toEntity() {
        return BoardUser.builder()
                .name(name)
                .nickName(nickName)
                .password(password)
                .build();
    }
}
