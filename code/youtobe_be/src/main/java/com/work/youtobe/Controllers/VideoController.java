package com.work.youtobe.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.work.youtobe.Exceptions.UploadVideoException;
import com.work.youtobe.Models.User;
import com.work.youtobe.Models.Video;
import com.work.youtobe.Services.UserService;
import com.work.youtobe.Services.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

     @Autowired
     private VideoService videoService;

     @Autowired
     private UserService userService;

     @GetMapping("")
     public ResponseEntity<?> getAllVideo() {
          return ResponseEntity.ok(videoService.getAllFiles());
     }

     // Xem ảnh
     @GetMapping("{id}")
     public ResponseEntity<?> readVideo(@PathVariable Long id) {
          Video file = videoService.getFile(id);
          return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getType()))
                    .body(file.getData());
     }

     // Upload ảnh
     @PostMapping("")
     public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file,
               @RequestParam("description") String description,
               @RequestParam("userId") Long userId) {
          try {
               // Check if the user exists
               User user = userService.getUserById(userId);
               if (user == null) {
                    return ResponseEntity.badRequest().body("User not found with ID: " + userId);
               }

               Video uploadedVideo = videoService.uploadFile(file, description, userId);

               // Trả về các thông tin cần thiết của video, bao gồm cả số lượt thích (likes) và
               // không thích (dislikes)
               return ResponseEntity.ok(Map.of("id", uploadedVideo.getId(), "title", uploadedVideo.getTitle(),
                         "description", uploadedVideo.getDescription(), "like", uploadedVideo.getLikes(),
                         "dislike", uploadedVideo.getDisLikes(), "userId", userId));
          } catch (UploadVideoException e) {
               return new ResponseEntity<>("Upload file error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
     }

     // Download ảnh
     @GetMapping("/download/{id}")
     public ResponseEntity<?> downloadVideo(@PathVariable Long id) {
          Video video = videoService.getFile(id);
          return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(video.getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getTitle() + "\"")
                    .body(new ByteArrayResource(video.getData()));
     }

     // Xóa ảnh
     @DeleteMapping("{id}")
     public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
          videoService.deleteFile(id);
          return ResponseEntity.noContent().build(); // 204
     }

}