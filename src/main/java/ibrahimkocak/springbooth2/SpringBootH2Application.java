package ibrahimkocak.springbooth2;

import ibrahimkocak.springbooth2.controller.ControllerUser;
import ibrahimkocak.springbooth2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class SpringBootH2Application implements ApplicationRunner {

    @Autowired
    ControllerUser controllerUser;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootH2Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        addUsers(5);
    }

    //For add some data to Db
    void addUsers(int count) {

        for (int i = 0; i < count; i++) {

            User user = new User();
            user.setName("ibrahim");
            user.setSurname("Koçak");
            user.setAccountType(User.AccountType.Standard);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.MARCH);
            cal.set(Calendar.DAY_OF_MONTH, 13);
            cal.set(Calendar.YEAR, 1993);

            user.setBirthday(cal.getTime());
            user.setCreationTime(new Date());

            controllerUser.save(user);
        }
    }
}
