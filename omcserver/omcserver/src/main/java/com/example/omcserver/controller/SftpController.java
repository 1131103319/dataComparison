//package com.example.omcserver.controller;
//
//import com.example.omcserver.service.impl.SftpServicelmpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@Slf4j
//@RestController
//@RequestMapping("/demo")
//public class SftpController {
//    @Autowired
//    SftpServicelmpl sftpServicelmpl;
//    @PostMapping("/upload")
//    public Result<Object> upload(@RequestParam String sftpPath, @RequestParam MultipartFile file) {
//        sftpServicelmpl.upload(sftpPath, file);
//        return Result.succeed();
//    }
//    /**
//     * 删除文件
//     */
//    @GetMapping("/delete")
//    public Result<Object> delete(@RequestParam String sftpPath) {
//        demoService.delete(sftpPath);
//        return Result.succeed();
//    }
//}
