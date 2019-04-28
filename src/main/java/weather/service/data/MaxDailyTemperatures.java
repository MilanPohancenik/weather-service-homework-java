package weather.service.data;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaxDailyTemperatures {

	@JsonProperty("cities")
	private Map<String, Map<Date, Integer>> citiesAndTemperatures;

	public Map<String, Map<Date, Integer>> getCitiesAndTemperatures() {
		return citiesAndTemperatures;
	}

	public void setCitiesAndTemperatures(Map<String, Map<Date, Integer>> citiesAndTemperatures) {
		this.citiesAndTemperatures = citiesAndTemperatures;
	}
	
}
