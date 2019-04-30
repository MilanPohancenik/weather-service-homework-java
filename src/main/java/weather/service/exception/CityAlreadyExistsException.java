package weather.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class CityAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -8365933507793270981L;

	private String city;
	
	public CityAlreadyExistsException(String city) {
		this.city = city;
	}

	@Override
	public String getMessage() {
		return String.format("The city [%1$s] already exists!", city);
	}
	
}
