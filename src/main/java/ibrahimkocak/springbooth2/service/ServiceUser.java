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

        Optional<User> optionalUser = repositoryUser.findById(id);

        if(optionalUser.isPresent()) {
            repositoryUser.deleteById(id);
            return optionalUser.get();
        }
        return null;
    }

    public User update(Long id, User user) {

        Optional<User> optionalUser = repositoryUser.findById(id);

        if(optionalUser.isPresent()) {
            if(optionalUser.get().setUser(user))
                optionalUser = Optional.of(repositoryUser.save(optionalUser.get()));

            return optionalUser.get();
        }
        return null;
    }

}
