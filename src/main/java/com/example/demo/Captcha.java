package com.example.demo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("captcha")
@Getter
@Setter
@NoArgsConstructor
public class Captcha {

	private String id;
	private String token;
	private Date generatedOn;
	
	public Captcha(String token) {
		this.token = token;
	}
	
	
}
