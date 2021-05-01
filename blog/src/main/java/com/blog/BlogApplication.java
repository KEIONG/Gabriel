package com.blog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class BlogApplication {


//	static StringRedisTemplate redisTemplate;



	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);
//		ValueOperations<String, String> operations = redisTemplate.opsForValue();
//
//		String ans = operations.get("louxi");
//		System.out.println(ans);



	}


















}
