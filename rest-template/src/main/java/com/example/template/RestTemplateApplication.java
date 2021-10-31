package com.example.template;

import com.example.template.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RestTemplateApplication {

	public static void main(String[] args) throws URISyntaxException {

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> responseEntity = template.getForEntity(
				"http://91.241.64.178:7081/api/users", String.class);
		List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));

		System.out.println(cookies);

		User user = new User();
		user.setId((long) 3);
		user.setName("James");
		user.setLastName("Brown");
		user.setAge((byte) 10);

		RequestEntity requestEntity = RequestEntity
				.method(HttpMethod.POST,"http://91.241.64.178:7081/api/users")
				.headers(headers)
				.body(user);


		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity entity = restTemplate.exchange(requestEntity, String.class);

		System.out.println(entity.getBody());

		user.setName("Thomas");
		user.setLastName("Shelby");

		requestEntity = RequestEntity
				.method(HttpMethod.PUT,"http://91.241.64.178:7081/api/users")
				.headers(headers)
				.body(user);

		entity = restTemplate.exchange(requestEntity, String.class);

		System.out.println(entity.getBody());

		requestEntity = RequestEntity
				.method(HttpMethod.DELETE, "http://91.241.64.178:7081/api/users/3")
				.headers(headers)
				.body(user);

		entity = restTemplate.exchange(requestEntity, String.class);

		System.out.println(entity.getBody());

	}

}

//5ebfebe7cb975dfcf9