package site.brainbrain.iqtest.domain;

import java.io.IOException;
import java.io.InputStream;

public interface ImageLoader {

    InputStream load(final String path) throws IOException;
}
