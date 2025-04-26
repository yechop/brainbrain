package site.brainbrain.iqtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(final PaymentException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("결제에 실패했습니다.");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }

    @ExceptionHandler(IqScoreException.class)
    public ResponseEntity<ErrorResponse> handleIQScoreException(final IqScoreException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("정답 개수와 일치하는 아이큐를 찾지 못했습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CertificateException.class)
    public ResponseEntity<ErrorResponse> certificateException(final CertificateException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse("인증서 생성에 실패했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> handleMailException(final MailException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("메일 전송에 실패했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("서버 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
