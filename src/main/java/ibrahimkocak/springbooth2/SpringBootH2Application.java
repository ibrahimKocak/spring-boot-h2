package ibrahimkocak.springbooth2;

import ibrahimkocak.springbooth2.controller.ControllerUser;
import ibrahimkocak.springbooth2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class SpringBootH2Application implements ApplicationRunner {

    @Autowired
    ControllerUser controllerUser;
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootH2Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        addUsers(5);
        retrieveFromEndpoint();
    }

    //For add some data to Db
    void addUsers(int count) {

        for (int i = 0; i < count; i++) {

            User user = new User();
            user.setName("ibrahim");
            user.setSurname("Koçak");
            user.setAccountType(User.AccountType.Standard);
            user.setBirthday(new Date());
            user.setCreationTime(new Date());

            controllerUser.save(user);
        }
    }

    //Send a request to endpoint
    @Bean
    public RestTemplate newRestTemplate() {
        return new RestTemplate();
    }

    void retrieveFromEndpoint() {

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8080/users", User[].class);
        List<User> userList = Arrays.asList(Objects.requireNonNull(response.getBody()));

        userList.forEach(user -> user.setName("ahem"));
        userList.forEach(System.out::println);
    }
}
