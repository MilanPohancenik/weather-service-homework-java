package weather.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityDoesNotExist extends RuntimeException {

	private static final long serialVersionUID = -417243266283304802L;

	private String city;
	
	public CityDoesNotExist(String city) {
		this.city = city;
	}
	
	@Override
	public String getMessage() {
		return String.format("The city [%1$s] was not found", city);
	}

}
