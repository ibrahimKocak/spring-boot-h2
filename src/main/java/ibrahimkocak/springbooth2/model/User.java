package ibrahimkocak.springbooth2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private AccountType accountType;
    private Date birthday;
    private Date creationTime;
    private Date lastUpdateTime;

    public boolean updateUser(User user) {

        boolean isUpdated = false;

        if (user.name != null && !user.name.equals(name)) {
            name = user.name;
            isUpdated = true;
        }
        if (user.surname != null && !user.surname.equals(surname)) {
            surname = user.surname;
            isUpdated = true;
        }
        if (user.accountType != null && !user.accountType.equals(accountType)) {
            accountType = user.accountType;
            isUpdated = true;
        }
        if (user.birthday != null && !user.birthday.equals(birthday)) {
            birthday = user.birthday;
            isUpdated = true;
        }
        if (isUpdated)
            setLastUpdateTime(new Date());

        return isUpdated;
    }

    /*
        public User cloneIt() {

            User user = new User();
            user.id = this.id;
            user.name = this.name;
            user.surname = this.surname;
            user.accountType = this.accountType;
            user.birthday = this.birthday;
            user.creationTime = this.creationTime;
            user.lastUpdateTime = this.lastUpdateTime;
            return user;
        }
    */
    public enum AccountType {
        Standard,
        Premium
    }
}
