package com.edeja.edejaEducation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executor;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@ImportResource({"classpath*:userPlayBeans.xml"})
public class EdejaEducationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EdejaEducationApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {


    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
