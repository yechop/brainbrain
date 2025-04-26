package site.brainbrain.iqtest.domain;

import java.awt.image.BufferedImage;

public record Certificate(BufferedImage certificateImage, String fileName, String certificateNumber) {
}
