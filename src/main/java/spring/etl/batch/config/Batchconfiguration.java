//package spring.etl.batch.config;
//
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//
//import jakarta.persistence.EntityManagerFactory;
//
//@Configuration
//@EnableBatchProcessing
//@SuppressWarnings({ "deprecation", "removal" })
//public class Batchconfiguration {
//
//	private final JobBuilderFactory jobBuilderFactory;
//	private final StepBuilderFactory stepBuilderFactory;
//	private final EntityManagerFactory entityManagerFactory;
//
//	public Batchconfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
//			EntityManagerFactory entityManagerFactory) {
//		this.jobBuilderFactory = jobBuilderFactory;
//		this.stepBuilderFactory = stepBuilderFactory;
//		this.entityManagerFactory = entityManagerFactory;
//	}
//
//	@Bean
//	public Job etlJob() {
//		return jobBuilderFactory.get("etlJob").incrementer(new RunIdIncrementer()).start(etlStep()).build();
//	}
//
//	@Bean
//	public Step etlStep() {
//		return stepBuilderFactory.get("etlStep").<Map<String, String>, Object>chunk(1000).reader(reader(null))
//				.processor(processor()).writer(writer()).build();
//	}
//
//	@Bean
//	@StepScope
//	public FlatFileItemReader<Map<String, String>> reader(@Value("#{jobParameters['file']}") Resource resource) {
//		FlatFileItemReader<Map<String, String>> reader = new FlatFileItemReader<>();
//		reader.setResource(resource);
//		reader.setLinesToSkip(1);
//		reader.setLineMapper(new DefaultLineMapper<>() {
//			{
//				setLineTokenizer(new DelimitedLineTokenizer() {
//					{
//						setDelimiter(",");
//						setNames(new String[] { "id", "name", "email", "phone" });
//					}
//				});
////				setFieldSetMapper(new MapFieldSetMapper());
//			}
//		});
//		return reader;
//	}
//
//	@Bean
//	public ItemProcessor<Map<String, String>, Object> processor() {
//		return map -> {
//			Class<?> entityClass = Class.forName(map.get("entity"));
//			Object entity = entityClass.getDeclaredConstructor().newInstance();
//			for (Field field : entityClass.getDeclaredFields()) {
//				String fieldName = field.getName();
//				String value = map.get(fieldName);
//				if (value != null) {
//					field.setAccessible(true);
//					if (field.getType() == Long.class) {
//						field.set(entity, Long.parseLong(value));
//					} else if (field.getType() == String.class) {
//						field.set(entity, value);
//					} else if (field.getType() == Date.class) {
//						field.set(entity, new SimpleDateFormat("yyyy-MM-dd").parse(value));
//					}
//				}
//			}
//			return entity;
//		};
//	}
//
//	@Bean
//	public JpaItemWriter<Object> writer() {
//		JpaItemWriter<Object> writer = new JpaItemWriter<>();
//		writer.setEntityManagerFactory(entityManagerFactory);
//		return writer;
//	}
//}
