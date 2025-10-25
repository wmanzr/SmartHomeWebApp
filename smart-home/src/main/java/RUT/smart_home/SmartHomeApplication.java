package RUT.smart_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication(scanBasePackages = {"RUT.smart_home", "RUT.smart_home_contract"})
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class SmartHomeApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
	}
}