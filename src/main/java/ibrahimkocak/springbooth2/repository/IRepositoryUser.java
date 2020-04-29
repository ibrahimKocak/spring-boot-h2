package ibrahimkocak.springbooth2.repository;

import ibrahimkocak.springbooth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositoryUser extends CrudRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findByAccountType(User.AccountType accountType);
}
