package site.brainbrain.iqtest.util;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CertificateNumberGenerator {

    public static String generate() {
        return UUID.randomUUID()
                .toString()
                .substring(15)
                .replace("-", "");
    }
}
