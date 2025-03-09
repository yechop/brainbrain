package site.brainbrain.iqtest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IqtestApplicationTests {

	@Test
	void contextLoads() {
		assertThat(true).isFalse();
	}

}
