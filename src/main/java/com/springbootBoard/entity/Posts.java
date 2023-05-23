package com.springbootBoard.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)//자식 클래스에서만 해당 생성자를 사용 가능
@AllArgsConstructor
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(length = 30, nullable = false)
    protected String title;


    @Column(columnDefinition = "TEXT", nullable = false)
    @Lob
    protected String content;

    @Column(nullable = false)
    protected String nickName;

    protected Integer viewCount;

    protected Integer likeCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts", cascade = CascadeType.ALL)
    protected List<Comment> comment = new ArrayList<>();

    public void plusLikeCount() {
        this.likeCount++;
    }

    public void minusLikeCount() {
        this.likeCount--;
    }

    public void addComment(Comment comment) {
        this.comment.add(comment);
        comment.addPosts(this);
    }


}
