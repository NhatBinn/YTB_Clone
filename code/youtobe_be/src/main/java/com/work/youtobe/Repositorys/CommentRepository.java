package com.work.youtobe.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.youtobe.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
