package spring.etl.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class Batchconfiguration {

	@Bean
	public Tasklet myTasklet() {
		return new MyTasklet();
	}

	@Bean
	public Job job(JobRepository jobRepository, @Qualifier("myStep") Step step) {
		return new JobBuilder("myJob", jobRepository).start(step).build();
	}

	@Bean
	public Step myStep(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager transactionManager) {
		return new StepBuilder("myStep", jobRepository).tasklet(tasklet, transactionManager).build();
	}

}
