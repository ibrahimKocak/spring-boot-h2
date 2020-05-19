package ibrahimkocak.springbooth2.service;

import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.repository.IRepositoryUser;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceUser {

    @Autowired
    private IRepositoryUser repositoryUser;

    public Iterable<User> getAll() {
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

        if (optionalUser.isPresent()) {
            repositoryUser.deleteById(id);
            return optionalUser.get();
        }
        return null;
    }

    public Map<String, User> update(Long id, User user) {

        Map<String, User> userMap = null;
        Optional<User> userDb = repositoryUser.findById(id);

        if (userDb.isPresent()) {

            userMap = new HashMap<>();
            userMap.put("Old_value", SerializationUtils.clone(userDb.get()));

            if (userDb.get().updateUser(user))
                userMap.put("New_value", repositoryUser.save(userDb.get()));
            else
                userMap.put("New_value", userMap.get("Old_value"));
        }
        return userMap;
    }

}
