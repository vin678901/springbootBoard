package com.springbootBoard.repository;

import com.springbootBoard.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    BoardUser findByEmail(String email);
}
