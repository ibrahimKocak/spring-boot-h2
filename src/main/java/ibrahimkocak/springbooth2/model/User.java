package ibrahimkocak.springbooth2.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    public enum AccountType {
        Standard,
        Premium
    }

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

    public User() {

        //setStatus(this.getClass().getSimpleName());
        //System.out.println(this.getClass().getSimpleName());
    }

    public boolean setUser(User user) {

        boolean isUpdated = false;

        if (user.name != null && !user.name.equals(name)) {
            setName(user.name);
            isUpdated = true;
        }
        if (user.surname != null && !user.surname.equals(surname)) {
            setSurname(user.surname);
            isUpdated = true;
        }
        if (user.accountType != null && !user.accountType.equals(accountType)) {
            setAccountType(user.accountType);
            isUpdated = true;
        }
        if (user.birthday != null && !user.birthday.equals(birthday)) {
            setBirthday(user.birthday);
            isUpdated = true;
        }
        if (isUpdated)
            setLastUpdateTime(new Date());

        return isUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {

        if (birthday != null)
            this.birthday = (Date) birthday.clone();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public User clone() {

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

    /*
    @Override
    public User clone(){

        User clone = null;
        try {
            clone = (User) super.clone();
            clone.birthday = (Date) this.getBirthday().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }
*/
    public static User copy(User user) {

        User user1 = new User();
        user1.setUser(user);

        return user1;
    }

    public String toJson(ObjectMapper mapper) throws JsonProcessingException {
        return  mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return String.format(
                "\nUser[id=%s, name='%s', surname='%s', birthday='%tc' , creationDate='%tc' , accountType='%s']\n",
                id, surname, name, birthday, creationTime, accountType);
    }
}
