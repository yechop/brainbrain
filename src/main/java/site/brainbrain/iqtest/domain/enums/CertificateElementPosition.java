package site.brainbrain.iqtest.domain.enums;

import lombok.Getter;

@Getter
public enum CertificateElementPosition {

    TESTEE_NAME(0, -30),
    MAIN_IQ(30, 380),
    PERCENTILE(2520, 1765),
    SD24(2810, 1843),
    SD16(2810, 1904),
    SD15(2810, 1965),
    ISSUE_DATE(536, 1810),
    CERTIFICATION_NUMBER(536, 1974);

    private final int x;
    private final int y;

    CertificateElementPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
