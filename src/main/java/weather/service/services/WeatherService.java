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

/**
 * This service handles the business case of adding and retrieving temperatures for a city and also the list of cities.
 */
@Service
public class WeatherService {

	private TemperaturesRepository temperaturesRepository;

	public WeatherService(TemperaturesRepository temperaturesRepository) {
		this.temperaturesRepository = temperaturesRepository;
	}

	/**
	 * Returns the temperatures for a given city
	 *
	 * @throws CityDoesNotExist exception when the city could not be found in the list
	 * @throws ValuesNotFoundException if there are no temperature values for the given city
	 * @param city
	 * @return returns a map of temperatures for a particular date
	 */
	public Map<Date, Integer> getTemperaturesForCity(String city) {
		if (temperaturesRepository.findCity(city) == null) {
			throw new CityDoesNotExist(city);
		}
		return temperaturesRepository.getTemperaturesForCity(city).orElseThrow(() -> new ValuesNotFoundException(city));
	}

	/**
	 * Lists all the available cities
	 *
	 * @return
	 */
	public List<String> getCities() {
		return temperaturesRepository.getCities();
	}

	/**
	 * Adds a new city to the list.
	 *
	 * @throws CityAlreadyExistsException if the city is already in the list
	 * @param city
	 */
	public void addCity(String city) {
		if (temperaturesRepository.findCity(city) != null) {
			throw new CityAlreadyExistsException(city);
		} else {
			temperaturesRepository.addCity(city);
		}
	}

	/**
	 * Adds temperatures for a given city and dates
	 *
	 * @throws TemperatureAlreadyExists exception if a value already exists, with a list of first 10 conflicting temperatures
	 * @throws CityDoesNotExist exception when the city does not exist
	 * @param city
	 * @param temperatures
	 */
	public void addTemperatures(String city, Map<Date, Integer> temperatures) {
		// first do validation, if values already exist
		validateTemperatures(city, temperatures);
		
		// then append
		temperaturesRepository.addTemperatures(city, temperatures);
	}

	/**
	 * Validatas if the values that should be inserted already exist. Also validates if the city exists.
	 *
	 * @param city
	 * @param temperatures
	 */
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
