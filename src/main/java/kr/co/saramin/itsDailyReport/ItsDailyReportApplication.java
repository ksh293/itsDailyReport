package kr.co.saramin.itsDailyReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

@SpringBootApplication
@EnableScheduling
public class ItsDailyReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItsDailyReportApplication.class, args);
	}
	
	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorService() {
		
		ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
		bean.setPoolSize(2);
		
		return bean;
	}
}
