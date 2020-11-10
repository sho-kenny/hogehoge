package orderManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import orderManagement.Service.SwaggerConfig;
import orderManagement.Service.WebConfig;

/**
 * 実行するためのソース(DDDとは特に関係なし)
 * @author sho
 */
@SpringBootApplication
@Import({ SwaggerConfig.class, WebConfig.class })
public class Program {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Program.class, args);
	}
}
