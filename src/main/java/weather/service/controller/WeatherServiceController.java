package weather.service.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weather.service.controller.response.WeatherResponse;
import weather.service.services.WeatherService;

@RestController
@RequestMapping("/api/weather/cities")
public class WeatherServiceController {

	private WeatherService weatherService;
	
	public WeatherServiceController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping(value = "{city}/temperatures", produces = "application/json")
	public WeatherResponse getTemperaturesForCity(@PathVariable("city") String city) {
		Map<Date, Integer> temperatures = weatherService.getTemperaturesForCity(city);
		return WeatherResponse.of(city, temperatures);
	}
	
	@GetMapping
	public List<String> getCities() {
		return weatherService.getCities();
	}
	
}
