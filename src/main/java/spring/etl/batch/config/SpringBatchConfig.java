package spring.etl.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import spring.etl.batch.model.Sample;
import spring.etl.batch.repository.SampleRepository;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("removal")
@ComponentScan(basePackages = "spring.etl.batch")
public class SpringBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private SampleRepository samplerepo;

	public SpringBatchConfig() {
		super();
	}

	public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	public FlatFileItemReader<Sample> reader() {

		FlatFileItemReader<Sample> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/sample.csv"));
		reader.setName("csvReader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}

	private LineMapper<Sample> lineMapper() {
		DefaultLineMapper<Sample> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
		delimiter.setDelimiter(",");
		delimiter.setStrict(false);
		delimiter.setNames("id", "firstname", "lastname");

		BeanWrapperFieldSetMapper<Sample> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Sample.class);

		lineMapper.setLineTokenizer(delimiter);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public Processor processor() {
		return new Processor();
	}

	@Bean
	public RepositoryItemWriter<Sample> writter() {
		RepositoryItemWriter<Sample> writer = new RepositoryItemWriter<>();
		writer.setRepository(samplerepo);
		writer.setMethodName("save");
		return writer;
	}

	@SuppressWarnings("deprecation")
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("csv-step").<Sample, Sample>chunk(100).reader(reader()).processor(processor())
				.writer(writter()).build();
	}

	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("myJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

}
