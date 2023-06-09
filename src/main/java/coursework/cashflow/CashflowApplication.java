package coursework.cashflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import springfox.boot.starter.autoconfigure.SpringfoxConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Overtone application.
 */
@SpringBootApplication
@EnableSwagger2
//@ImportResource("classpath:beans.xml")
@Import(SpringfoxConfigurationProperties.Swagger2Configuration.class)
public class CashflowApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CashflowApplication.class, args);
	}
}
