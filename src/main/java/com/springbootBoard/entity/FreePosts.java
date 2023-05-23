package com.springbootBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "free_posts")
@AllArgsConstructor
public class FreePosts extends Posts {

    @Builder
    public FreePosts(String title, String content, String nickName) {
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.viewCount = 0;
        this.likeCount = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(String title, String content, String nickName) {
        this.title = title;
        this.content = content;
        this.nickName = nickName;
    }


}
