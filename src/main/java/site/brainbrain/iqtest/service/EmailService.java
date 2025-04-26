package site.brainbrain.iqtest.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.brainbrain.iqtest.controller.dto.TesteeRequest;
import site.brainbrain.iqtest.domain.Certificate;
import site.brainbrain.iqtest.exception.MailException;
import site.brainbrain.iqtest.util.PdfConverter;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(final TesteeRequest testeeRequest, final Certificate certificate) {
        try {
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(testeeRequest.email());
            helper.setSubject("BrainBrain IQ 테스트 결과");
            helper.setText("첨부된 인증서 및 보고서를 확인해주세요.");

            final String fileName = certificate.fileName();
            final ByteArrayResource pdfResource = PdfConverter.imageToPdfResource(certificate.certificateImage());

            helper.addAttachment(fileName, pdfResource, MediaType.APPLICATION_PDF_VALUE);

            mailSender.send(message);
            System.out.println("메일 전송 완료");
        } catch (final Exception e) {
            throw new MailException("메일 전송에 실패했습니다.");
        }
    }
}
