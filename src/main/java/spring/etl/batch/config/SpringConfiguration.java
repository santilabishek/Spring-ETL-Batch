//package spring.etl.batch.config;
//
//import java.util.Arrays;
//
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//
//import jakarta.persistence.Column;
//import spring.etl.batch.model.Sample;
//
//@Configuration
//public class SpringConfiguration {
//
////	public static String filePath = "";
//
//	@Bean
//	public FlatFileItemReader<?> reader(Resource inputFile) {
//		FlatFileItemReader<Sample> reader = new FlatFileItemReader<>();
//		reader.setResource(inputFile);
//		reader.setLinesToSkip(1);
//		reader.setLineMapper(new DefaultLineMapper<>() {
//			{
//				setLineTokenizer(new DelimitedLineTokenizer() {
//					{
//						setDelimiter(DELIMITER_COMMA);
//						setNames(getFieldNames());
//					}
//				});
//				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
//					{
//						setTargetType(Sample.class);
//					}
//				});
//			}
//		});
//		return reader;
//	}
//
//	private String[] getFieldNames() {
//		return Arrays.stream(Sample.class.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Column.class))
//				.map(field -> field.getAnnotation(Column.class).name()).toArray(String[]::new);
//	}
//
//}
