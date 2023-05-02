package com.springbootBoard.controller;

import com.springbootBoard.dto.UserFormDto;
import com.springbootBoard.dto.UserUpdateDto;
import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.service.BoardUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final BoardUserService boardUserService;


    @GetMapping("/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    @PostMapping("/new")
    public String userForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/userForm";
        }

        try {
            userFormDto.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
            BoardUser boardUser = userFormDto.toEntity();
            boardUserService.save(boardUser);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/";
    }


    @GetMapping("/login")
    public String loginUser() {
        return "/user/userLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "Login Error");
        return "/user/userLoginForm";
    }

    @GetMapping("/update")
    public String updateForm(Model model) {
        model.addAttribute("userUpdateDto", new UserUpdateDto());
        return "user/userUpdateForm";
    }

    @PostMapping("/update")
    public String postUpdateForm(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult,
                                 Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "user/userUpdateForm";
        }
        try {
            String email = principal.getName();
            boardUserService.updateBoardUser(email, userUpdateDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userUpdateForm";
        }

        return "redirect:/";
    }

}
