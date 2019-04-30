package weather.service.controller.response;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewTemperatureResponse {

	private final String city;
	
	private final Map<Date, Integer> temperatures;

	private NewTemperatureResponse(String city, Map<Date, Integer> temperatures) {
		this.city = city;
		this.temperatures = Collections.unmodifiableMap(new LinkedHashMap<>(temperatures));
	}
	
	public String getCity() {
		return city;
	}

	public Map<Date, Integer> getTemperatures() {
		return temperatures;
	}

	public static final NewTemperatureResponse of(String city, Map<Date, Integer> temperatures) {
		return new NewTemperatureResponse(city, temperatures);
	}
}
