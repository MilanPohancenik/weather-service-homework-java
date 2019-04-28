package weather.service.controller.response;

public class NewCityResponse {

	private final String city;

	private NewCityResponse(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return city;
	}

	public static NewCityResponse of(String city) {
		return new NewCityResponse(city);
	}
}
