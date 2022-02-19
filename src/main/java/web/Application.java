package web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import web.model.User;

@Component
public class Application {

	static final String URL = "http://91.241.64.178:7081/api/users";
	static final String URL_ID = "http://91.241.64.178:7081/api/users/3";
	static RestTemplate restTemplate = new RestTemplate();
	static String result;
	static HttpHeaders httpHeaders = new HttpHeaders();


	public static void main(String[] args) {
		String cookie = getAllUsers();
		result += saveUser(cookie);
		result += updateUser(cookie);
		result += deleteUser(cookie);
		System.out.println(result);
	}

	public static String getAllUsers() {
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
		return response.getHeaders().getFirst("Set-Cookie");
	}
	public static String saveUser(String cookie) {
		User user = new User(3L, "James", "Brown", (byte) 25);
		httpHeaders.set(HttpHeaders.COOKIE, cookie);
		HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
		return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
	}

	public static String updateUser(String cookie) {
		User updUser = new User(3L, "Thomas", "Shelby", (byte) 27);
		httpHeaders.set(HttpHeaders.COOKIE, cookie);
		HttpEntity<User> entity = new HttpEntity<>(updUser, httpHeaders);
		return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
	}

	public static String deleteUser(String cookie) {
		httpHeaders.set(HttpHeaders.COOKIE, cookie);
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		return restTemplate.exchange(URL_ID, HttpMethod.DELETE, entity, String.class).getBody();
	}
}