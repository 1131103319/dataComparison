package com.example.omcserver.utils;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p> @Title SftpUtils
 * <p> @Description SFTP工具类
 *
 * @author ACGkaka
 * @date 2024/1/31 17:41
 */
@Slf4j
@Component
public class SftpUtils {

    @Value("${sftp.username}")
    private String userName;
    @Value("${sftp.password}")
    private String passwd;
    @Value("${sftp.host}")
    private String host;
    @Value("${sftp.port}")
    private int port;
    @Value("${sftp.protocol}")
    private String protocol;

    /**
     * 创建SFTP连接
     */
    public ChannelSftp createSftp() throws JSchException {
        JSch jsch = new JSch();
        log.info("Try to connect sftp[" + userName + "@" + host + "]");

        Session session = createSession(jsch, host, userName, port);
        session.setPassword(passwd);
        session.setConfig("StrictHostKeyChecking", "no");
        // 默认情况下，JSch库本身并没有会话超时时间。
        // 为了避免长时间无活动连接占用资源或因网络问题导致连接挂起而不被释放，通常建议设置会话超时，（单位：毫秒）
        session.setTimeout(30000);
        session.connect();

        log.info("Session connected to {}.", host);

        Channel channel = session.openChannel(protocol);
        channel.connect();

        log.info("Channel created to {}.", host);

        return (ChannelSftp) channel;
    }

    /**
     * 创建 Session
     */
    public Session createSession(JSch jsch, String host, String username, Integer port) throws JSchException {
        Session session = null;

        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }

        if (session == null) {
            throw new RuntimeException(host + "session is null");
        }

        return session;
    }

    /**
     * 关闭连接
     */
    public void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.error("sftp 连接已关闭");
                }
                if (sftp.getSession() != null) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            log.error("sftp 断开连接失败，原因：{}", e.getMessage(), e);
        }
    }
}
