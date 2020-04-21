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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ControllerUser {

    private Logger logger = Logger.getLogger(ControllerUser.class);

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {

        try {
            return ResponseEntity.ok(serviceUser.getAll());
        }catch (Exception e) {
            logger.error("Error on function 'getAll'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@PathVariable("id") String id) {

        try {
            System.out.println(serviceUser.getId(id));
            return ResponseEntity.ok(serviceUser.getId(id));
        }catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<List<User>> getByName(@PathVariable("name") String name) {

        try {
            return ResponseEntity.ok(serviceUser.getName(name));
        }catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/getByAccountType/{accountType}")
    public ResponseEntity<List<User>> getByAccountType(@PathVariable("accountType") User.AccountType accountType) {

        try {
            return ResponseEntity.ok(serviceUser.getAccountType(accountType));
        }catch (Exception e) {
            logger.error("Error on function 'get'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {

        try {
            user.setCreationTime(new  Date());
            user.setLastUpdateTime(new  Date());
            if(user.getAccountType() == null)
                user.setAccountType(User.AccountType.Standard);
            else if(!user.getAccountType().equals(User.AccountType.Premium))
                user.setAccountType(User.AccountType.Standard);
            return ResponseEntity.ok(serviceUser.save(user));
        }catch (Exception e) {
            logger.error("Error on function 'save'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAll() {

        try {
            return ResponseEntity.ok(serviceUser.deleteAll());
        }catch (Exception e) {
            logger.error("Error on function 'delete'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") String id) {

        try {
            return ResponseEntity.ok(serviceUser.delete(id));
        }catch (Exception e) {
            logger.error("Error on function 'delete'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    private boolean isUpdatable(Object newOne, Object oldOne) {

        return newOne != null && !newOne.equals(oldOne);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<HashMap<String,User>> update(@PathVariable("id") String id, @RequestBody User user) {

        try {
            HashMap<String, User> map = new HashMap<>();
            User userOld = serviceUser.getId(id).clone();
            map.put("Old Value",userOld);
            map.put("New Value",serviceUser.update(id,user));
            return ResponseEntity.ok(map);
        }catch (Exception e) {
            logger.error("Error on function 'put'");
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
