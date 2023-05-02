package com.springbootBoard.entity;

import com.springbootBoard.constant.Category;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    public void plusLikeCount() {
        this.likeCount++;
    }

    public void minusLikeCount() {
        this.likeCount--;
    }
}
