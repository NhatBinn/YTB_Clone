package com.work.youtobe.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "comment")
public class Comment {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String content;

     private LocalDateTime commentDate;

     @PrePersist
     public void prePersist() {
          commentDate = LocalDateTime.now();
     }

     @ManyToOne
     @JoinColumn(name = "video_id")
     private Video video;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     public void setVideoForComment(Video video) {
          this.video = video;
          video.getComments().add(this);
     }

     // Add method to set user for comment
     public void setUserForComment(User user) {
          this.user = user;
          user.getComments().add(this);
     }
}
