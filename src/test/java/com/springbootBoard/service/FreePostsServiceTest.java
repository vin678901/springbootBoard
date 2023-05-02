package com.springbootBoard.service;

import com.springbootBoard.dto.FreePostsDto;
import com.springbootBoard.entity.FreePosts;
import com.springbootBoard.repository.FreePostsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FreePostsServiceTest {

    @Autowired
    private FreePostsRepository freePostsRepository;

    @Autowired
    private FreePostsService freePostsService;

    private FreePosts createFreePosts() {
        FreePostsDto freePostsDto = new FreePostsDto();
        freePostsDto.setTitle("testTitle");
        freePostsDto.setContent("testContent");
        return freePostsDto.toEntity();
    }

    @Test
    public void freeTest() {
        FreePosts freePosts = createFreePosts();

        freePostsService.saveFreePosts(freePosts);

        FreePosts result = freePostsRepository.findById(freePosts.getId()).orElseThrow();

        assertThat(freePosts.getTitle()).isEqualTo(result.getTitle());
        assertThat(freePosts.getContent()).isEqualTo(result.getContent());
    }
}