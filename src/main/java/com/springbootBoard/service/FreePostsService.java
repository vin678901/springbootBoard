package com.springbootBoard.service;

import com.springbootBoard.dto.FreePostsDto;
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

    public FreePosts getFreePosts(Long id) {
        return freePostsRepository.findById(id).orElseThrow();
    }


    public int freeViewCountPlus(Long id) {
        return freePostsRepository.viewCountPlus(id);
    }

    @Transactional(readOnly = true)
    public Page getFreePostsPage(FreePostsDto freePostsDto, Pageable pageable) {
        return freePostsRepository.getFreePostsPage(freePostsDto, pageable);
    }

    public void freePostsLike(Long id, String email) {
        BoardUser boardUser = boardUserService.getUserByEmail(email);
        if (boardUser.getLikedList().contains(id)) {
            freePostsRepository.getById(id).minusLikeCount();//좋아요를 -- 해주고
            boardUser.getLikedList().remove(id);//boardUser의 likedList에서 해당 id를 삭제해야 한다.
        } else {
            freePostsRepository.getById(id).plusLikeCount();
            boardUser.getLikedList().add(id);
        }
    }

    public void updateFreePosts(Long id, FreePostsDto freePostsDto, String email) {
        FreePosts freePosts = freePostsRepository.findById(id).orElseThrow();
        if (freePosts == null) {
            throw new IllegalArgumentException("게시글 정보가 없습니다.");
        }
        if (!freePosts.getCreatedBy().equals(email)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        BoardUser boardUser = boardUserService.getUserByEmail(email);//사용자가 닉네임을 바꿧을 수도 있으니 또 가져온다.
        freePosts.update(freePostsDto.getTitle(), freePostsDto.getContent(), boardUser.getNickName());
        //nickname이 null값이 전달됨.
    }

    public FreePostsDto getFreePostsDetail(Long id) {
        FreePosts freePosts = freePostsRepository.findById(id).orElseThrow();
        return new FreePostsDto(freePosts.getId(), freePosts.getTitle(), freePosts.getContent(), freePosts.getNickName());
    }

    public void deleteFreePosts(Long id, String email) {
        FreePosts freePosts = freePostsRepository.findById(id).orElseThrow();
        if (!freePosts.getCreatedBy().equals(email)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        freePostsRepository.delete(freePosts);
    }
}
