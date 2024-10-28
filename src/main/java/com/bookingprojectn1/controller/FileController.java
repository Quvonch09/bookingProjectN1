package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.res.ResFile;
import com.bookingprojectn1.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService videoFileService;

    public FileController(FileService videoFileService) {
        this.videoFileService = videoFileService;
    }

    @PostMapping(value = "/upload",consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> uploadVideo(@RequestParam("file") MultipartFile file)
    {
        return ResponseEntity.ok(videoFileService.saveFile(file));
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id)
    {
        ResFile resFile = videoFileService.loadFileAsResource(id);
        return ResponseEntity.ok()
                .headers(resFile.getHeaders())
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resFile.getFillName())
                .body(resFile.getResource());
    }


    @PutMapping(value = "/update/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> updateFile(@PathVariable Long id, @RequestParam("file") MultipartFile file)
    {
        return ResponseEntity.ok(videoFileService.updateFile(id, file));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFile(@PathVariable Long id)
    {
        return ResponseEntity.ok(videoFileService.deleteFile(id));
    }
}