package com.work.youtobe.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.youtobe.Models.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
     public List<Video> findByOrderByCreatedAtDesc();
}
