package com.work.youtobe.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "video")
public class Video {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String title;

     private String description;

     private int likes = 0; // Số lượt like ban đầu là 0

     private int disLikes = 0; // Số lượt dislike ban đầu là 0

     private String type;

     private LocalDateTime createdAt;

     @Lob
     @Column(name = "data", columnDefinition = "LONGBLOB")
     private byte[] data;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user = new User();

     public User getUser() {
          return user; // Return the associated User object or handle null case in the calling code
     }

     @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
     private List<Comment> comments = new ArrayList<>();

     public void addComment(Comment comment) {
          comments.add(comment);
          comment.setVideo(this);
     }

     public void removeComment(Comment comment) {
          comments.remove(comment);
          comment.setVideo(null);
     }

     @PrePersist
     public void prePersist() {
          createdAt = LocalDateTime.now();
     }
}
