package site.brainbrain.iqtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.brainbrain.iqtest.controller.dto.CreateResultRequest;
import site.brainbrain.iqtest.domain.Certificate;
import site.brainbrain.iqtest.domain.enums.IQScore;
import site.brainbrain.iqtest.service.CertificateService;
import site.brainbrain.iqtest.service.EmailService;
import site.brainbrain.iqtest.service.PaymentService;
import site.brainbrain.iqtest.service.ScoreService;

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
        final IQScore testeeIQScore = scoreService.calculate(request.answerSheet());
        final Certificate certificate = certificateService.generate(request.testeeRequest(), testeeIQScore);
        emailService.send(request.testeeRequest(), certificate);
        return ResponseEntity.ok().build();
    }
}
