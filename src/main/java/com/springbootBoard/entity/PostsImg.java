package com.springbootBoard.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;

@Entity
@Table(name = "posts_img")
@Getter
public class PostsImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @Builder
    public PostsImg(String imgName, String oriImgName, String imgUrl, String repImg, Posts posts) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImg = repImg;
        this.posts = posts;
    }

    public void updatePostsImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
