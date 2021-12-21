package com.manosoft.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.manosoft.app")
public class FileSplitterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSplitterAppApplication.class, args);
	}

}
