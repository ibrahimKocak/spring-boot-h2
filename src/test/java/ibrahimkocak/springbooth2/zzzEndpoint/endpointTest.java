package ibrahimkocak.springbooth2.zzzEndpoint;

import ibrahimkocak.springbooth2.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class endpointTest {

    private static RestTemplate restTemplate;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
    }

    //Send a request to zzzEndpoint, try when service is up
    @Test
    void retrieveFromEndpoint() {

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8080/users", User[].class);
        List<User> userList = Arrays.asList(Objects.requireNonNull(response.getBody()));

        userList.forEach(user -> user.setName("ahem"));
        userList.forEach(System.out::println);
    }
}
