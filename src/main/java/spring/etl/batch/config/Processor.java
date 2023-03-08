package spring.etl.batch.config;

import org.springframework.batch.item.ItemProcessor;

import spring.etl.batch.model.Sample;

public class Processor implements ItemProcessor<Sample, Sample> {

	@Override
	public Sample process(Sample item) throws Exception {
		return item;
	}

}
