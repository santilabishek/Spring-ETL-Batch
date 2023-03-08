package spring.etl.batch.controller;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ETL_Batch_Controller {

//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	private Job job;
//
//	public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
//		File file = new File(multipartFile.getOriginalFilename());
//		FileOutputStream outputStream = new FileOutputStream(file);
//		outputStream.write(multipartFile.getBytes());
//		outputStream.close();
//		return file;
//	}
//
//	@PostMapping("/etl")
//	public String etl(@RequestParam("file") MultipartFile Mfile) throws Exception {
//
//		// Convert the MultipartFile to a File
//		File file = convertMultipartFileToFile(Mfile);
//
//		// Create a JobParameters object to pass to the job
//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("inputFile", "file:" + file.getAbsolutePath()).toJobParameters();
//
//		// Launch the job and wait for it to complete
//		jobLauncher.run(job, jobParameters).getExitStatus();
//
//		// Return a success message to the user
//		return "ETL job submitted successfully";
//	}

}
