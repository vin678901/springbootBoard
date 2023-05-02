package com.springbootBoard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "group_posts")
public class ReviewPosts extends Posts {
    @Column(columnDefinition = "integer default 0")
    private Integer rating;
    //별점 기능, 후기게시판 전용

}
