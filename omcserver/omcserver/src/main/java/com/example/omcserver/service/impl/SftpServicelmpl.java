//package com.example.omcserver.service.impl;
//
//import com.example.omcserver.service.SftpService;
//import com.example.omcserver.utils.SftpUtils;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.SftpATTRS;
//import com.jcraft.jsch.SftpException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.*;
//
//@Slf4j
//@Service
//public class SftpServicelmpl implements SftpService {
//    @Resource
//    private SftpUtils sftpUtils;
//
//    @Override
//    public void upload(String sftpPath, File file) {
//        // 上传文件
//        ChannelSftp sftp = null;
//        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
//            // 开启sftp连接
//            sftp = sftpUtils.createSftp();
//            // 进入sftp文件目录
//            sftp.cd(sftpPath);
//            log.info("修改目录为：{}", sftpPath);
//
//            // 上传文件
//            sftp.put(in, file.getName()+".tmp");
//            sftp.rename(sftp.pwd()+ "/" +file.getName()+".tmp",sftp.pwd()+ "/" +file.getName());
//            log.info("上传文件成功，目标目录：{}", sftpPath);
//        } catch (SftpException | JSchException | IOException e) {
//            log.error("上传文件失败，原因：{}", e.getMessage(), e);
//            throw new RuntimeException("上传文件失败");
//        } finally {
//            // 关闭sftp
//            sftpUtils.disconnect(sftp);
//        }
//    }
//
//    @Override
//    public void delete(String sftpPath) {
//        // 删除文件
//        ChannelSftp sftp = null;
//        try {
//            // 开启sftp连接
//            sftp = sftpUtils.createSftp();
//
//            // 判断sftp文件存在
//            boolean isExist = isFileExist(sftpPath, sftp);
//            if (isExist) {
//                // 删除文件
//                SftpATTRS sftpATTRS = sftp.lstat(sftpPath);
//                if (sftpATTRS.isDir()) {
//                    sftp.rmdir(sftpPath);
//                } else {
//                    sftp.rm(sftpPath);
//                }
//                log.info("sftp文件删除成功，目标文件：{}.", sftpPath);
//            } else {
//                log.error("sftp文件删除失败，sftp文件不存在：" + sftpPath);
//                throw new RuntimeException("sftp文件删除失败，sftp文件不存在：" + sftpPath);
//            }
//        } catch (SftpException | JSchException e) {
//            log.error("sftp文件删除失败，原因：{}", e.getMessage(), e);
//            throw new RuntimeException("sftp文件删除失败");
//        } finally {
//            // 关闭sftp
//            sftpUtils.disconnect(sftp);
//        }
//    }
//
//    private boolean isFileExist(String sftpPath, ChannelSftp sftp) {
//        try {
//            // 获取文件信息
//            SftpATTRS sftpATTRS = sftp.lstat(sftpPath);
//            return sftpATTRS != null;
//        } catch (Exception e) {
//            log.error("判断文件是否存在失败，原因：{}", e.getMessage(), e);
//            return false;
//        }
//    }
//}
