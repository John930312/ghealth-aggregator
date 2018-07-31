package com.todaysoft.ghealth.utils;

import com.todaysoft.ghealth.model.email.Email;


import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;

import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmailUtil {
    public static void sendEmail(Email data, String toEmail, String name, String path, String packagename) throws MessagingException, UnsupportedEncodingException {
        String EMAILTITLE = name + packagename + "报告";
        String EMAILCONTENT = "尊敬的客户您好：   "+"\n"
               + "  您好，您的基因检测报告已经生成，请下载附件查看您的报告，开始探索您的基因密码。"+"\n"+"  在阅读报告的过程中如有任何疑问，可直接拨打我们的客服热线:400-087-3360。"
                +"\n"+"  (此邮件由系统自动发送，请勿回复)"+"\n"+"\n"+"  华生基因，祝您健康、平安！";
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", data.getServerHost());
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", data.getServerPort().toString());
        props.setProperty("mail.smtp.socketFactory.port", data.getServerPort().toString());
        props.setProperty("mail.smtp.timeout", "80000");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(data.getUsername(), data.getPassword());
            }});
        Message msg = new MimeMessage(session);
        // 设置发件人和收件人
        msg.setFrom(new InternetAddress(data.getUsername()));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(EMAILTITLE); // 标题
        msg.setText(EMAILCONTENT);// 内容
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        // 设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText(EMAILCONTENT);
        multipart.addBodyPart(contentPart);
        // 添加附件
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        FileDataSource fdatasource = new FileDataSource(path);
        messageBodyPart2.setDataHandler(new DataHandler(fdatasource));
        messageBodyPart2.setFileName(fdatasource.getName());
        multipart.addBodyPart(messageBodyPart2);
        msg.setContent(multipart);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
