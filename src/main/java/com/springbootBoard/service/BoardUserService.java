package com.springbootBoard.service;

import com.springbootBoard.dto.UserUpdateDto;
import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.repository.BoardUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardUserService implements UserDetailsService {

    private final BoardUserRepository boardUserRepository;

    private final PasswordEncoder passwordEncoder;

    public BoardUser save(BoardUser boardUser) {
        validateEmail(boardUser.getEmail());
        return boardUserRepository.save(boardUser);
    }

    public BoardUser getUserByEmail(String email) {
        return boardUserRepository.findByEmail(email);
    }

    public void validateEmail(String email) {
        if (boardUserRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("이미 가입한 이메일입니다.");
        }
    }

    public void updateBoardUser(String email, UserUpdateDto userUpdateDto) {
        BoardUser boardUser = boardUserRepository.findByEmail(email);
        String password = passwordEncoder.encode(userUpdateDto.getPassword());
        userUpdateDto.setPassword(password);
        boardUser.updateBoardUser(userUpdateDto);
    }

    public BoardUser findByEmail(String email) {
        return boardUserRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BoardUser boardUser = boardUserRepository.findByEmail(email);

        if (boardUser == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(boardUser.getEmail())
                .password(boardUser.getPassword())
                .roles(boardUser.getRole().toString())
                .build();
    }
    //구현만 하고 직접 사용하지는 않는다. 스프링 시큐리티가 인증하는 과정에서 필요한 과정을 처리해준 것
}
