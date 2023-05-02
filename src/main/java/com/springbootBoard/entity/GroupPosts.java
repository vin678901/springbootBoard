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
public class GroupPosts extends Posts {

    @Column(columnDefinition = "integer default 0")
    private Integer joinUsers;
    //독서모임 참가 인원, 모임게시판 전용

}
