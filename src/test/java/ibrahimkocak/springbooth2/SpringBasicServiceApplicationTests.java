package ibrahimkocak.springbooth2;

import ibrahimkocak.springbooth2.controller.ControllerUser;
import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.service.ServiceUserTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringBasicServiceApplicationTests implements ApplicationRunner {

    @Autowired
    ControllerUser controllerUser;
    @Autowired
    ServiceUserTests serviceUserTests;

    @Test
    void contextLoads() {

        serviceUserTests.testGetAll();
        serviceUserTests.testGetId();
    }


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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        addUsers(5);
    }
}
