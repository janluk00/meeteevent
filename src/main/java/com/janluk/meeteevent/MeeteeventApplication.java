package com.janluk.meeteevent;

import com.janluk.meeteevent.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class MeeteeventApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeeteeventApplication.class, args);
	}

}
