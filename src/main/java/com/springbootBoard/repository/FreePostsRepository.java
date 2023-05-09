package com.springbootBoard.repository;

import com.springbootBoard.dto.FreePostsDto;
import com.springbootBoard.entity.FreePosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface FreePostsRepository extends JpaRepository<FreePosts, Long>,
        FreePostsRepositoryCustom, QuerydslPredicateExecutor<FreePosts> {
    Page<FreePosts> findAll(Pageable pageable);

    @Modifying//트랜잭션 안에서 수행됨. update 혹은 delete 사용할 때 사용
    @Query("update FreePosts p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int viewCountPlus(Long id);

}
