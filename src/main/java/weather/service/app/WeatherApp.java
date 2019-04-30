package weather.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"weather.service"})
public class WeatherApp {

    public static void main(String[] args) {
    	SpringApplication.run(WeatherApp.class, args);
    }
    
}
