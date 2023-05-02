package com.springbootBoard.entity;

import com.springbootBoard.dto.FreePostsDto;
import com.springbootBoard.repository.FreePostsRepository;
import com.springbootBoard.service.FreePostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostsTest {

    @Autowired
    private FreePostsService freePostsService;

    @Autowired
    private FreePostsRepository freePostsRepository;

    private FreePosts createFreePosts() {
        FreePostsDto freePostsDto = new FreePostsDto();
        freePostsDto.setTitle("testTitle");
        freePostsDto.setContent("testContent");
        return freePostsDto.toEntity();
    }

    @Test
    void defaultColumnTest() {
        FreePosts freePosts = createFreePosts();

        freePostsService.saveFreePosts(freePosts);

        FreePosts result = freePostsRepository.findById(freePosts.getId()).orElseThrow();

        assertThat(freePosts.getLikeCount()).isEqualTo(0);
        assertThat(freePosts.getViewCount()).isEqualTo(0);

    }

}