package getWeather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		String city = "Glasgow,uk";

		return args-> {
//			for (int i=0;i<10;i++) {
			while (true) {
			  getWeatherForLocation(restTemplate, "Glasgow,uk");
			  getWeatherForLocation(restTemplate, "Cardiff,uk");
			  getWeatherForLocation(restTemplate, "Denver,us");
			  getWeatherForLocation(restTemplate, "80016,us");
			  java.lang.Thread.sleep(60000);
			}
		};
	}

	private void getWeatherForLocation(RestTemplate restTemplate, String locale) {
		String appId = "1a7b8e8562ea3e7ca5242c92a75b7f80";
		String apiRequest = "http://api.openweathermap.org/data/2.5/weather?q=" + locale + "&APPID=" + appId;
		long preTime = System.currentTimeMillis();
		String weather = restTemplate.getForObject(
					apiRequest, String.class);
		long elapsedTime = System.currentTimeMillis() - preTime;
		log.info("Weather for " + locale + ": " + weather + " - responseTime=" + elapsedTime);
	}
}
