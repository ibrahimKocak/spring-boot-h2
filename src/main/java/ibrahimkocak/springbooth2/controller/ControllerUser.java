package ibrahimkocak.springbooth2.controller;


import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.service.ServiceUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ControllerUser {

    private final Logger logger = Logger.getLogger(ControllerUser.class);

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAll() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(serviceUser.getAll());
        } catch (Exception e) {
            logger.error("Error on function 'getAll'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Long id) {

        try {
            Optional<User> optionalUser = serviceUser.getId(id);
            if (optionalUser.isPresent())
                return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/usersByName/{name}")
    public ResponseEntity<List<User>> getByName(@PathVariable("name") String name) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(serviceUser.getName(name));
        } catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/usersByAccountType/{accountType}")
    public ResponseEntity<List<User>> getByAccountType(@PathVariable("accountType") User.AccountType accountType) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(serviceUser.getAccountType(accountType));
        } catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {

        try {
            user.setCreationTime(new Date());
            user.setLastUpdateTime(user.getCreationTime());
            if (user.getAccountType() == null)
                user.setAccountType(User.AccountType.Standard);
            else if (!user.getAccountType().equals(User.AccountType.Premium))
                user.setAccountType(User.AccountType.Standard);

            return ResponseEntity.status(HttpStatus.OK).body(serviceUser.save(user));

        } catch (Exception e) {
            logger.error("Error on function 'save'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAll() {

        try {
            serviceUser.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.error("Error on function 'deleteAll'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, User>> delete(@PathVariable("id") Long id) {

        try {
            if (serviceUser.getId(id).isPresent()) {

                User userDeleted = serviceUser.delete(id);
                if (userDeleted != null)
                    return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("Deleted Value", userDeleted));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error on function 'delete'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, User>> update(@PathVariable("id") Long id, @RequestBody User user) {

        try {
            Map<String, User> userMap = serviceUser.update(id, user);

            if (userMap == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            else if (userMap.get("Old_value").equals(userMap.get("New_value")))
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

            else
                return ResponseEntity.status(HttpStatus.OK).body(userMap);

        } catch (Exception e) {
            logger.error("Error on function 'put'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}