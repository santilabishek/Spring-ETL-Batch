package spring.etl.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import spring.etl.batch.model.Sample;
import spring.etl.batch.repository.SampleRepository;

@Configuration
@EnableBatchProcessing
public class ExistingConfig {

	@Autowired
	private SampleRepository samplerepo;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public ItemProcessor<Sample, Sample> processor() {
		return item -> {
			Sample outputItem = new Sample();
			outputItem.setFirstName(item.getFirstName());
			outputItem.setEmail(item.getEmail().toUpperCase());
			return outputItem;
		};
	}

	@Bean
	public TaskExecutor taskExecutor() {
		System.out.println("Task Executer..");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(30);
		return executor;
	}

	@Bean
	public FlatFileItemReader<Sample> reader() {
		System.out.println("Hi Reader...");
		FlatFileItemReader<Sample> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/sample.csv"));
		reader.setName("csvReader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}

	private LineMapper<Sample> lineMapper() {
		System.out.println("Line Mapper..");
		DefaultLineMapper<Sample> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
		delimiter.setDelimiter(",");
		delimiter.setStrict(false);
		delimiter.setNames(new String[] { "id", "firstname", "lastname" });
		BeanWrapperFieldSetMapper<Sample> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Sample.class);
		lineMapper.setLineTokenizer(delimiter);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public RepositoryItemWriter<Sample> writter() {
		System.out.println("HI Writter");
		RepositoryItemWriter<Sample> writer = new RepositoryItemWriter<>();
		writer.setRepository(samplerepo);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step1() {
		return new StepBuilder("csv-step", jobRepository).<Sample, Sample>chunk(10, transactionManager).reader(reader())
				.processor(processor()).writer(writter()).taskExecutor(taskExecutor()).build();
	}

	@Bean
	public Job runJob() {
		return new JobBuilder("outputItem", jobRepository).start(step1()).build();
	}
}
