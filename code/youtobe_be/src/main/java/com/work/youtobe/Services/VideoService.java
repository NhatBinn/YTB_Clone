package com.work.youtobe.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.work.youtobe.Exceptions.NotFoundException;
import com.work.youtobe.Exceptions.UploadVideoException;
import com.work.youtobe.Models.User;
import com.work.youtobe.Models.Video;
import com.work.youtobe.Repositorys.UserRepository;
import com.work.youtobe.Repositorys.VideoRepository;
import com.work.youtobe.Utils.VideoUrlUpLoad;

@Service
public class VideoService {

     @Autowired
     private VideoUrlUpLoad videoUrlUpLoad;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private VideoRepository videoRepository;

     public List<Video> getAllFiles() {
          return videoRepository.findByOrderByCreatedAtDesc();
     }

     public Video getFile(Long id) {
          return videoRepository.findById(id).orElseThrow(() -> {
               throw new NotFoundException("Not found Video with id = " + id);
          });
     }

     // Trả về đối tượng video sau khi đã lưu vào cơ sở dữ liệu
     public Video uploadFile(MultipartFile file, String description, Long userId) {
          videoUrlUpLoad.validateFile(file);
          try {
               String videoName = StringUtils.cleanPath(file.getOriginalFilename());

               // Kiểm tra kích thước tối đa của video
               long maxVideoSize = 100 * 1024 * 1024; // 100 MB
               if (file.getSize() > maxVideoSize) {
                    throw new IllegalArgumentException("Video size exceeds the maximum limit (100 MB)");
               }

               // Tìm người dùng trong cơ sở dữ liệu bằng ID
               User user = userRepository.findById(userId).orElse(null);

               // Tạo một đối tượng Video mới và đặt thông tin từ người dùng
               Video video = new Video();
               video.setTitle(videoName);
               video.setDescription(description);
               video.setLikes(0);
               video.setDisLikes(0);
               video.setType(file.getContentType());
               video.setData(file.getBytes());
               video.setUser(user); // Liên kết video với người dùng đã tải lên

               // Lưu video vào cơ sở dữ liệu
               video = videoRepository.save(video);

               return video;
          } catch (IOException e) {
               throw new UploadVideoException("Error while processing the uploaded file 1.", e);
          } catch (IllegalArgumentException e) {
               // Nếu kích thước video vượt quá giới hạn, ném ngoại lệ UploadVideoException
               throw new UploadVideoException(e.getMessage(), e);
          } catch (Exception e) {
               throw new UploadVideoException("Error while uploading video 2 : " +
                         e.getMessage(), e);
          }
     }

     public void deleteFile(Long id) {

          Video video = videoRepository.findById(id).orElseThrow(() -> {
               throw new NotFoundException("Not found image with id = " + id);
          });

          videoRepository.delete(video);
     }
}