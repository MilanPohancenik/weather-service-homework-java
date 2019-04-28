package weather.service.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import weather.service.data.TemperaturesRepository;
import weather.service.exception.ValuesNotFoundException;

@Service
public class WeatherService {

	private TemperaturesRepository temperaturesRepository;
	
	public WeatherService(TemperaturesRepository temperaturesRepository) {
		this.temperaturesRepository = temperaturesRepository;
	}

	public Map<Date, Integer> getTemperaturesForCity(String city) {
		return temperaturesRepository.getTemperaturesForCity(city)
				.orElseThrow(() -> new ValuesNotFoundException(city));
	}

	public List<String> getCities() {
		return temperaturesRepository.getCities();
	}
}
