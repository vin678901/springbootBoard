package com.springbootBoard.controller;

import com.springbootBoard.dto.FreePostsDto;
import com.springbootBoard.dto.PostsSearchDto;
import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.entity.FreePosts;
import com.springbootBoard.service.BoardUserService;
import com.springbootBoard.service.FreePostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class FreePostsController {

    private final FreePostsService freePostsService;

    private final BoardUserService boardUserService;

    @GetMapping("/free")
    public String free(PostsSearchDto postsSearchDto, Model model, Optional<Integer> page) {
        Pageable pageable = Pageable.ofSize(5);
        Page<FreePostsDto> freePosts = freePostsService.getFreePosts(pageable);
        model.addAttribute("freePosts", freePosts);
        model.addAttribute("postsSearchDto", postsSearchDto);
        model.addAttribute("maxPage", 5);
        return "posts/freePosts";
    }

    @GetMapping("/free/write")
    public String freeWrite(Model model) {
        model.addAttribute("freePostsDto", new FreePostsDto());
        return "posts/freeWrite";
    }

    @PostMapping("/free/write")
    public String postFreeWrite(@Valid FreePostsDto freePostsDto, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "posts/freeWrite";
        }

        try {//title이랑 content만 전달이 되는 상태. title, content, nickName 필요
            String email = principal.getName();
            BoardUser boardUser = boardUserService.getUserByEmail(email);
            freePostsDto.setNickName(boardUser.getNickName());
            FreePosts freePosts = freePostsDto.toEntity();
            freePostsService.saveFreePosts(freePosts);//
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/posts/free";
    }

    @GetMapping("/free/{id}")
    public String freeDetail(@PathVariable("id") Long id, Model model) {
        FreePosts freePosts = freePostsService.getFreePostsDetail(id);
        freePostsService.freeViewCountPlus(id);
        model.addAttribute("freePosts", freePosts);
        return "posts/freeDetail";
    }

    @PostMapping("/free/{id}/like")
    public @ResponseBody ResponseEntity freePostsLike(@PathVariable("id") Long id, Principal principal) {
        String email = principal.getName();
        freePostsService.freePostsLike(id, email);

        return ResponseEntity.ok().build();
    }
}
