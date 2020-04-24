package ibrahimkocak.springbooth2.service;

import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.repository.IRepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser {

    @Autowired
    private IRepositoryUser repositoryUser;

    public List<User> getAll() {
        return repositoryUser.findAll();
    }

    public Optional<User> getId(Long id) {
        return repositoryUser.findById(id);
    }

    public List<User> getAccountType(User.AccountType accountType) {
        return repositoryUser.findByAccountType(accountType);
    }

    public List<User> getName(String name) {
        return repositoryUser.findByName(name);
    }

    public User save(User user) {
        return repositoryUser.save(user);
    }

    public void deleteAll() {
        repositoryUser.deleteAll();
    }

    public User delete(Long id) {
        User user = repositoryUser.findById(id).get();
        repositoryUser.deleteById(id);
        return user;
    }

    public User update(Long id, User user) {
        User userDb = repositoryUser.findById(id).get();
        userDb.setUser(user);
        return repositoryUser.save(userDb);
    }

}
