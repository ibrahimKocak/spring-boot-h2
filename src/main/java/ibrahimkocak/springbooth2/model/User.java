package ibrahimkocak.springbooth2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Cloneable {

    public enum AccountType{
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

    public void setUser(User user) {

        boolean isUpdated = false;

        if(user.name != null && !user.name.equals(name)){
            setName(user.name);
            isUpdated = true;
        }
        if(user.surname != null && !user.surname.equals(surname)){
            setSurname(user.surname);
            isUpdated = true;
        }
        if(user.accountType != null && !user.accountType.equals(accountType)){
            setAccountType(user.accountType);
            isUpdated = true;
        }
        if(user.birthday != null && !user.birthday.equals(birthday)){
            setBirthday(user.birthday);
            isUpdated = true;
        }
        if(isUpdated)
            setLastUpdateTime(new Date());

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

        if(birthday != null)
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

    @Override
    public String toString() {
        return String.format(
                "\nUser[id=%s, name='%s', surname='%s', birthday='%tc' , creationDate='%tc' , accountType='%s']\n",
                id, surname, name, birthday, creationTime, accountType);
    }
}
