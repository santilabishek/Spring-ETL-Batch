package spring.etl.batch.config;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@SuppressWarnings("deprecation")
	@Bean
	public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
		return new JobBuilderFactory(jobRepository);
	}

	@Bean
	@SuppressWarnings({ "removal", "deprecation" })
	public StepBuilderFactory askda(JobRepository jobRepository) {
		return new StepBuilderFactory(jobRepository);
	}

}
