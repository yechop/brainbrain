package site.brainbrain.iqtest.domain;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ClasspathImageLoader implements ImageLoader {

    @Override
    public InputStream load(final String path) throws IOException {
        return new ClassPathResource(path).getInputStream();
    }
}
