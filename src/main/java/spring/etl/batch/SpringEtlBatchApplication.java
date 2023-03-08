package spring.etl.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEtlBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEtlBatchApplication.class, args);
		System.out.println("Spring boot ETL Batch application has started sucessfully...");
	}

}
