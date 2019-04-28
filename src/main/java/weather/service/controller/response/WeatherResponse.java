package weather.service.controller.response;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherResponse {
	
	private final String city;
	
	private final Map<Date, Integer> temperatures;

	private WeatherResponse(String city, Map<Date, Integer> temperatures) {
		this.city = city;
		this.temperatures = new LinkedHashMap<>();
		this.temperatures.putAll(temperatures);
 	}

	public String getCity() {
		return city;
	}

	public Map<Date, Integer> getTemperatures() {
		return temperatures;
	}

	public static WeatherResponse of(String city, Map<Date, Integer> temperatures) {
		return new WeatherResponse(city, temperatures);
	}
}
