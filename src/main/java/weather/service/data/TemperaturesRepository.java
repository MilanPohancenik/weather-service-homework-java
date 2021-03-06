package weather.service.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Repository
public class TemperaturesRepository {

	private Map<String, Map<Date, Integer>> data;

	private ObjectMapper objectMapper;

	public TemperaturesRepository(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public Optional<Map<Date, Integer>> getTemperaturesForCity(String city) {
		return Optional.ofNullable(data.get(city.toLowerCase()));
	}

	public List<String> getCities() {
		return new ArrayList<>(data.keySet());
	}

	public void addCity(String city) {
		data.put(city, null);
	}

	public String findCity(String city) {
		return data.keySet()
				.stream()
				.filter(existing -> existing.equalsIgnoreCase(city))
				.findFirst()
				.orElse(null);
	}

	public void addTemperatures(String city, Map<Date, Integer> temperatures) {
		Map<Date, Integer> existing = data.get(city.toLowerCase());
		if (existing == null) {
			data.put(city, new LinkedHashMap<>());
		}
		data.get(city.toLowerCase()).putAll(temperatures);
	}

	@PostConstruct
	public void init() throws IOException {
		InputStream inputStreamData = this.getClass().getResourceAsStream("max_daily_temperatures.json");
		MaxDailyTemperatures jsonData = objectMapper.readValue(inputStreamData, MaxDailyTemperatures.class);
		data = jsonData.getCitiesAndTemperatures();
	}
}
