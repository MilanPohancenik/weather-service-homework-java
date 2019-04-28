package weather.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ValuesNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3617365273390044084L;

	private String city;

	public ValuesNotFoundException(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}
	
	@Override
	public String getMessage() {
		return String.format("No values found for city: '%1$s'", city);
	}

}
