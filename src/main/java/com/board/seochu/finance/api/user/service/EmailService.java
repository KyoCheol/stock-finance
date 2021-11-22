package com.board.seochu.finance.api.user.service;

import com.board.seochu.finance.api.user.dto.EmailDto;
import com.board.seochu.finance.exception.EmailSendException;
import com.board.seochu.finance.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private final EmailDto emailDto;

    public void sendEmailMessage(String email) {
        try {
            String code = createCode();
            redisUtil.setDataExpire(code, email, emailDto.getValidTime());
            MimeMessage message = createMessage(email, code);
            javaMailSender.send(message);
        } catch(Exception e) {
            throw new EmailSendException("메일코드 보내기 오류");
        }

        // 보낼 이메일 설정
//        message.addRecipients(MimeMessage.RecipientType.TO, email);
//        // 이메일 제목
//        message.setSubject("[인증 코드]" + code);
//        message.setText(setContext(code), "utf-8", "html");

    }

    // 타임리프 설정하는 코드
    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        return templateEngine.process("mail", context); // mail.html
    }

    private MimeMessage createMessage(String email, String code) throws Exception {
        log.info("STEP2 > > > >");
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        log.info("STEP3 > > > >");
        message.setSubject("[회원가입] 인증코드 :  " + code);
        message.setText(setContext(code), "utf-8", "html");
        log.info("STEP4 > > > >");
        message.setFrom(new InternetAddress(emailDto.getLink(), emailDto.getName()));
        return message;
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }
}
