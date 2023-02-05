package com.assignment.sn;

import com.assignment.sn.executor.PrivateInvestigator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@SpringBootApplication
public class PrivateInvestigatorApplication implements CommandLineRunner {

	@Autowired
	private PrivateInvestigator investigator;

	@Value("${investigator.input.file.path:src/main/resources/data/input.txt}")
	private String inputFilePath;

	@Value("${investigator.output.file.path:src/main/resources/data/output.txt}")
	private String outputFilePath;

	public static void main(String[] args) {
		SpringApplication.run(PrivateInvestigatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Input File Path: {}", inputFilePath);
		Path path = Paths.get(inputFilePath);
		List<String> sentences = Files.readAllLines(path);
		investigator.initiateInvestigation(sentences);
		investigator.writeResults(outputFilePath);
		log.info("Output File Path: " + outputFilePath);
		log.info("Investigation Completed");
	}
}
