package com.springbootBoard.controller;

import com.springbootBoard.dto.FreePostsDto;
import com.springbootBoard.dto.PostsSearchDto;
import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.entity.FreePosts;
import com.springbootBoard.service.BoardUserService;
import com.springbootBoard.service.FreePostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
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

        FreePosts freePosts = freePostsService.getFreePosts(id);
        freePostsService.freeViewCountPlus(id);
        model.addAttribute("freePosts", freePosts);
        return "posts/freeDetail";
    }

    @PostMapping("/free/{id}/like")
    public @ResponseBody ResponseEntity freePostsLike(@PathVariable("id") Long id, Principal principal) {
        String email = principal.getName();
        freePostsService.freePostsLike(id, email);
//기능 작동. 다만 프론트 문제로 즉시반영이 안됨
        return ResponseEntity.ok().build();
    }

    @GetMapping("/free/edit/{id}")
    public String freePostsEdit(@PathVariable("id") Long id, Model model) {
        try {
            FreePostsDto freePostsDto = freePostsService.getFreePostsDetail(id);
            model.addAttribute("freePostsDto", freePostsDto);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "posts/freeWrite";
        }
        return "posts/freeWrite";
    }

    @PostMapping("/free/edit/{id}")
    public String PostFreePostsUpdate(@Valid FreePostsDto freePostsDto, BindingResult bindingResult,
                                      Model model, Principal principal) {
        String email = principal.getName();

        if (bindingResult.hasErrors()) {
            return "posts/freeWrite";
        }

        try {
            FreePosts freePosts = freePostsDto.toEntity();
            freePosts.setId(freePostsDto.getId());
            freePostsService.updateFreePosts(freePosts.getId(), freePostsDto, email);//여기서 nickname
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "posts/freeWrite";
        }
        return "redirect:/posts/free";
    }

    @GetMapping("/free/delete")
    public String freePostsDelete(@RequestParam("id") Long id, Principal principal) {
        String email = principal.getName();
        freePostsService.deleteFreePosts(id, email);
        return "redirect:/posts/free";
    }

}
