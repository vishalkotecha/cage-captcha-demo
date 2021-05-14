package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CaptchaRepository extends MongoRepository<Captcha, String>{

	Captcha findByToken(String token);

}
