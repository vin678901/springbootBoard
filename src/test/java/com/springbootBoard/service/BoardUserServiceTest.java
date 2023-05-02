package com.springbootBoard.service;

import com.springbootBoard.constant.Role;
import com.springbootBoard.dto.UserUpdateDto;
import com.springbootBoard.entity.BoardUser;
import com.springbootBoard.repository.BoardUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardUserServiceTest {

    @Autowired
    private BoardUserRepository boardUserRepository;
    @Autowired
    private BoardUserService boardUserService;

    BoardUser createBoardUser() {
        BoardUser boardUser = BoardUser.builder()
                .email("signUpTest@test.com")
                .password("testtest")
                .name("singUpTest")
                .nickName("singUpTest")
                .role(Role.CUSTOMER)
                .build();
        return boardUser;
    }

    @Test
    public void signUpTest() {
        BoardUser boardUser = createBoardUser();
        boardUserRepository.save(boardUser);

        BoardUser result = boardUserRepository.findByEmail(boardUser.getEmail());
        assertThat(result).isEqualTo(boardUser);
    }

    @Test
    public void updateTest() {
        BoardUser boardUser = createBoardUser();
        boardUserRepository.save(boardUser);

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setPassword("updateTest1");
        userUpdateDto.setName("updateTest2");
        userUpdateDto.setNickName("updateTest3");

        String email = boardUser.getEmail();

        boardUserService.updateBoardUser(email, userUpdateDto);
        
        assertThat(userUpdateDto.getNickName()).isEqualTo(boardUser.getNickName());
        assertThat(userUpdateDto.getName()).isEqualTo(boardUser.getName());
        assertThat(userUpdateDto.getPassword()).isEqualTo(boardUser.getPassword());
    }
}