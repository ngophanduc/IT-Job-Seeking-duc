package com.jobseeking.jobseekingbe;

import com.jobseeking.jobseekingbe.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JobSeekingBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobSeekingBeApplication.class, args);
	}
}
