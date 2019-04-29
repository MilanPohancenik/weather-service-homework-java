package weather.service.exception;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class TemperatureAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 4491630953038114412L;

	private String city;

	private String conflictsString;
	
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public TemperatureAlreadyExists(String city, Map<Date, Integer> conflicts) {
		this.city = city;
		this.conflictsString = convertToString(conflicts);
	}

	@Override
	public String getMessage() {
		return String.format("Following temperatures for city [%1$s] already exist! [%2$s]", city, conflictsString);
	}

	private String convertToString(Map<Date, Integer> conflicts) {
		return conflicts.entrySet().stream()
				.map(entry -> dateFormat.format(entry.getKey()) + ":" + entry.getValue())
				.collect(Collectors.joining(","));
	}
}
