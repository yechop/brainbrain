package site.brainbrain.iqtest.domain;

public record Certificate(byte[] pdfBytes, String fileName, String certificateNumber) {
}
