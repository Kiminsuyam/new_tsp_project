package com.tsp.new_tsp_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@ComponentScans({
		@ComponentScan(basePackages = "com.tsp")
})
@SpringBootApplication(scanBasePackages = "com")
public class NewTspProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewTspProjectApplication.class, args);
	}

}
