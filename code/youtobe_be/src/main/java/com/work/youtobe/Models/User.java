package com.work.youtobe.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     private String email;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
     private List<Comment> comments = new ArrayList<>();

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
     private List<Video> videos = new ArrayList<>();

     // Add method to add a comment to the user
     public void addComment(Comment comment) {
          comments.add(comment);
          comment.setUser(this);
     }

     // Add method to remove a comment from the user
     public void removeComment(Comment comment) {
          comments.remove(comment);
          comment.setUser(null);
     }

     // Add method to add a video to the user
     public void addVideo(Video video) {
          videos.add(video);
          video.setUser(this);
     }

     // Add method to remove a video from the user
     public void removeVideo(Video video) {
          videos.remove(video);
          video.setUser(null);
     }
}
