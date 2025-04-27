package site.brainbrain.iqtest.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.brainbrain.iqtest.controller.dto.CreateResultRequest;
import site.brainbrain.iqtest.domain.Certificate;
import site.brainbrain.iqtest.domain.enums.IqScore;
import site.brainbrain.iqtest.service.CertificateService;
import site.brainbrain.iqtest.service.EmailService;
import site.brainbrain.iqtest.service.PaymentService;
import site.brainbrain.iqtest.service.ScoreService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ResultController {

    private final PaymentService paymentService;
    private final CertificateService certificateService;
    private final ScoreService scoreService;
    private final EmailService emailService;

    @PostMapping("/results")
    public ResponseEntity<Void> create(@RequestBody final CreateResultRequest request) {
        paymentService.pay(request.paymentRequest());
        final IqScore testeeIqScore = scoreService.calculate(request.answerSheet());
        final Certificate certificate = certificateService.generate(request.testeeRequest(), testeeIqScore);
        emailService.send(request.testeeRequest(), certificate);

        //임시 로그
        log.info("\n이메일 : " + request.testeeRequest().email()
                + "\n이름 : " + request.testeeRequest().name()
                + "\n답지 : " + request.answerSheet()
                + "\n날짜 + " + LocalDateTime
                .now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
                + "\n");
        return ResponseEntity.ok().build();
    }
}
