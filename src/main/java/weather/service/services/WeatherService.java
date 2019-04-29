package weather.service.services;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import weather.service.data.TemperaturesRepository;
import weather.service.exception.CityAlreadyExistsException;
import weather.service.exception.CityDoesNotExist;
import weather.service.exception.TemperatureAlreadyExists;
import weather.service.exception.ValuesNotFoundException;

@Service
public class WeatherService {

	private TemperaturesRepository temperaturesRepository;

	public WeatherService(TemperaturesRepository temperaturesRepository) {
		this.temperaturesRepository = temperaturesRepository;
	}

	public Map<Date, Integer> getTemperaturesForCity(String city) {
		if (temperaturesRepository.findCity(city) == null) {
			throw new CityDoesNotExist(city);
		}
		return temperaturesRepository.getTemperaturesForCity(city).orElseThrow(() -> new ValuesNotFoundException(city));
	}

	public List<String> getCities() {
		return temperaturesRepository.getCities();
	}

	public void addCity(String city) {
		if (temperaturesRepository.findCity(city) != null) {
			throw new CityAlreadyExistsException(city);
		} else {
			temperaturesRepository.addCity(city);
		}
	}
	
	public void addTemperatures(String city, Map<Date, Integer> temperatures) {
		// first do validation, if values already exist
		validateTemperatures(city, temperatures);
		
		// then append
		temperaturesRepository.addTemperatures(city, temperatures);
	}
	
	private void validateTemperatures(String city, Map<Date, Integer> temperatures) {
		try {
			Map<Date, Integer> existing = getTemperaturesForCity(city);
			Map<Date, Integer> conflicing = new LinkedHashMap<>();
			for(Date existingDate : existing.keySet()) {
				if (temperatures.containsKey(existingDate)) {
					if (conflicing.size() <= 10) {
						conflicing.put(existingDate, existing.get(existingDate));
					} else {
						break;
					}
				}
			}
			if (conflicing.size() > 0) {
				throw new TemperatureAlreadyExists(city, conflicing);
			}
		} catch (ValuesNotFoundException e) {
			// does not even have values, so everything is ok
			return;
		}
	}
}
