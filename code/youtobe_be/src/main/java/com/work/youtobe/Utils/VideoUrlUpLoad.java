package com.work.youtobe.Utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.work.youtobe.Exceptions.BadRequestException;

@Component
public class VideoUrlUpLoad {
     // Validate file
     public void validateFile(MultipartFile file) {
          // Kiểm tra tên file
          String fileName = file.getOriginalFilename();
          if (fileName == null || fileName.isEmpty()) {
               throw new BadRequestException("tên file không được để trống");
          }

          // kiem tra dinh dang file
          String fileExtension = getFileExtension(fileName);
          if (!checkFileExtension(fileExtension)) {
               throw new BadRequestException("file không đúng định dạng");
          }

          double fileSize = (double) file.getSize() / (1024 * 1024); // Chuyển đổi byte thành megabyte
          if (fileSize > 100) { // Kiểm tra kích thước tệp có vượt quá 100MB hay không
               throw new BadRequestException("File không được vượt quá 100MB");
          }

     }

     // Lấy extension của file (ví dụ png, jpg, ...)
     public String getFileExtension(String fileName) {
          int lastIndexOf = fileName.lastIndexOf(".");
          return fileName.substring(lastIndexOf + 1);
     }

     // Kiểm tra extension của file có được phép hay không
     public boolean checkFileExtension(String fileExtension) {
          List<String> extensions = new ArrayList<>(List.of("png", "jpg", "jpeg", "pdf", "mp4"));
          return extensions.contains(fileExtension.toLowerCase());
     }
}
