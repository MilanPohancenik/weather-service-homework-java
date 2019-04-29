package weather.service.controller.request;

import java.util.Date;
import java.util.Map;

public class NewTemperatureRequest {

	private Map<Date, Integer> temperatures;

	public Map<Date, Integer> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(Map<Date, Integer> temperatures) {
		this.temperatures = temperatures;
	}
	
}
