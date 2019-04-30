package weather.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll
import weather.service.app.WeatherApp
import weather.service.exception.CityDoesNotExist
import weather.service.exception.ValuesNotFoundException

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = WeatherApp, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles('test')
@AutoConfigureMockMvc
class WeatherServiceControllerIntegrationTest extends Specification {

    @Autowired
    MockMvc mockMvc

    /**
     * Use-case no.1 : getting data for cities
     */
    @Unroll
    def 'Get data for a city [#city]'() {
        expect:
        mockMvc.perform(get("/api/weather/cities/$city/temperatures"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent))

        where:
        expectedContent                                                                                                          | city
        """{"city":"ostrava","temperatures":{"19-09-2017":20,"20-09-2017":15,"21-09-2017":17,"22-09-2017":25,"23-09-2017":5}}""" | 'ostrava'
        """{"city":"brno","temperatures":{"19-09-2017":18,"20-09-2017":11,"21-09-2017":9,"22-09-2017":6,"23-09-2017":9}}"""      | 'brno'
        """{"city":"praha","temperatures":{"19-09-2017":5,"20-09-2017":15,"21-09-2017":17,"22-09-2017":8,"23-09-2017":10}}"""    | 'praha'
    }

    /**
     * Use-case no. 2 : city not found
     */
    def 'Getting data for not existing city'() {
        when:
        def exception = mockMvc.perform(get('/api/weather/cities/abcd/temperatures'))
                .andExpect(status().isNotFound())
                .andReturn()
                .resolvedException

        then:
        exception.message == 'The city [abcd] was not found'
        exception.class == CityDoesNotExist
    }

    /**
     * Use-case no. 3 :  add a new city, but no temperatures for the city
     */
    def 'Create a new city, but no temperatures then perform requests'() {
        expect:
        "Create city $city"
        mockMvc.perform(put("/api/weather/cities")
                .contentType('application/json')
                .content("""{"city":"$city"}"""))
                .andExpect(status().isCreated())
                .andExpect(content().string("""{"city":"$city"}"""))

        and:
        "The cities endpoint contains $city"
        def content = mockMvc.perform(get('/api/weather/cities'))
                .andExpect(status().isOk())
                .andReturn()
                .response.contentAsString
        content.contains("\"$city\"")

        and:
        "There are no temperature values for the city $city"
        def exception = mockMvc.perform(get("/api/weather/cities/$city/temperatures"))
                .andExpect(status().isNotFound())
                .andReturn()
                .resolvedException

        exception.message == "No values found for city [$city]"
        exception.class == ValuesNotFoundException

        where:
        city = 'madrid'
    }

    /**
     * Use-case no. 4 : add a new city and temperatures, then test the response
     */
    def 'Create a new city and add temperatures'() {
        expect:
        "Create city $city"
        mockMvc.perform(put("/api/weather/cities")
                .contentType('application/json')
                .content("""{"city":"$city"}"""))
                .andExpect(status().isCreated())

        and:
        "Add temperatures for $city"
        mockMvc.perform(put("/api/weather/cities/$city/temperatures")
                .contentType('application/json')
                .content("""{"temperatures": {"20-02-2020": 1, "19-02-2020": 2, "18-02-2020": 5}}"""))
                .andExpect(status().isCreated())
                .andExpect(content().string("""{"city":"$city","temperatures":{"20-02-2020":1,"19-02-2020":2,"18-02-2020":5}}"""))

        where:
        city = 'barcelona'
    }
}