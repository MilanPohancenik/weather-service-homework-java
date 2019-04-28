package weather.service.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		data.put(city, new LinkedHashMap<>());
	}

  	@PostConstruct
	public void onInit() throws JsonParseException, JsonMappingException, IOException {
		InputStream inputStreamData = this.getClass().getResourceAsStream("max_daily_temperatures.json");
		MaxDailyTemperatures jsonData = objectMapper.readValue(inputStreamData, MaxDailyTemperatures.class);
		data = jsonData.getCitiesAndTemperatures();
	}
}
