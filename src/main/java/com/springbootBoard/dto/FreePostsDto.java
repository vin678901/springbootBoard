package com.springbootBoard.dto;

import com.springbootBoard.entity.BaseEntity;
import com.springbootBoard.entity.FreePosts;
import com.springbootBoard.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreePostsDto extends BaseEntity {

    private Long id;

    @NotEmpty(message = "제목을 입력해 주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;

    private String nickName;


    public FreePosts toEntity() {
        return FreePosts.builder()
                .title(title)
                .content(content)
                .nickName(nickName)
                .build();
    }
}
