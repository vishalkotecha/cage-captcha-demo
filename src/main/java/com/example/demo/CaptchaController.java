package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.cage.Cage;
import com.github.cage.GCage;

@RestController(value = "/api/v1/captcha")
public class CaptchaController {

	@Autowired
	private CaptchaRepository captchaRepository;
	@Autowired
	private AmazonS3ClientService amazonS3ClientService;

	@GetMapping("/generate")
	public ResponseEntity<String> generateCaptcha() throws IOException {

		Cage cage = new GCage();
		String token = cage.getTokenGenerator().next();

		Captcha captcha = captchaRepository.save(new Captcha(token));

		String path = "captcha.jpg";

		File file = new File(path);
		OutputStream os = new FileOutputStream(file);
		if (!file.exists()) {
			file.createNewFile();
		}

		try {
			cage.draw(token, os);
		} finally {
			os.close();
		}

		amazonS3ClientService.uploadFileToS3Bucket(file, true);

		if (file.delete()) {
			System.out.println("file deleted");
		}

		// deleteFilesOlderThan(captchaImageUploadDirectory,
		// CAPTCHA_IMAGE_LIFE_MILLISECONDS, CAPTCHA_IMAGE_EXTENSION);

		return new ResponseEntity<>(captcha.getId(), HttpStatus.OK);
	}

	@GetMapping("/validate")
	public ResponseEntity<String> valiate(@RequestParam String token) throws IOException {

		Captcha captcha = captchaRepository.findByToken(token);
		if (captcha != null)
			return "success";
		return null;
	}

}
