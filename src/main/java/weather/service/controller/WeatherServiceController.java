package weather.service.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import weather.service.controller.request.NewCityRequest;
import weather.service.controller.request.NewTemperatureRequest;
import weather.service.controller.response.NewCityResponse;
import weather.service.controller.response.NewTemperatureResponse;
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
	
	@PutMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public NewCityResponse addCity(@RequestBody NewCityRequest request) {
		weatherService.addCity(request.getCity());
		return NewCityResponse.of(request.getCity());
	}
	
	@PutMapping(value = "{city}/temperatures", 
			consumes = "application/json", 
			produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public NewTemperatureResponse addTemperatureForCity(@PathVariable("city") String city, @RequestBody NewTemperatureRequest request) {
		weatherService.addTemperatures(city, request.getTemperatures());
		return NewTemperatureResponse.of(city, request.getTemperatures());
	}
	
}
