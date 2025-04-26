package site.brainbrain.iqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IqtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IqtestApplication.class, args);
    }
}
