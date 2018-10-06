package org.ni.recruitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class RecruitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitServiceApplication.class, args);
	}
}
