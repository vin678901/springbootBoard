package com.springbootBoard.service;

import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.entity.FreePosts;
import com.springbootBoard.repository.BoardUserRepository;
import com.springbootBoard.repository.FreePostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FreePostsService {

    private final BoardUserService boardUserService;

    private final FreePostsRepository freePostsRepository;

    //FreePosts 기능 구현
    public void saveFreePosts(FreePosts freePosts) {
        freePostsRepository.save(freePosts);
    }

    public Page getFreePosts(Pageable pageable) {
        return freePostsRepository.findAll(pageable);
    }

    public FreePosts getFreePostsDetail(Long id) {
        return freePostsRepository.findById(id).orElseThrow();
    }

    public int freeViewCountPlus(Long id) {
        return freePostsRepository.viewCountPlus(id);
    }

    public void freePostsLike(Long id, String email) {
        BoardUser boardUser = boardUserService.getUserByEmail(email);
        if (boardUser.getLikedList().contains(id)) {
            freePostsRepository.getById(id).minusLikeCount();
        } else {
            freePostsRepository.getById(id).plusLikeCount();
        }
    }
}
