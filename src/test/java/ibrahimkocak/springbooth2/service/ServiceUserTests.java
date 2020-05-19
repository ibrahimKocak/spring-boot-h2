package ibrahimkocak.springbooth2.service;

import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.repository.IRepositoryUser;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceUserTests {

    @Autowired
    private ServiceUser serviceUser;

    @MockBean
    private IRepositoryUser repositoryUser;

    @Test
    public void testGetAll() {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(repositoryUser.findAll()).thenReturn(list);
        assertThat(serviceUser.getAll()).isEqualTo(list);
    }

    @Test
    public void testGetId() {

        User user = getUser();
        when(repositoryUser.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        assertThat(serviceUser.getId(Mockito.anyLong())).isEqualTo(Optional.of(user));
    }


    @Test
    public void testGetAccountType() {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(repositoryUser.findByAccountType(User.AccountType.Standard)).thenReturn(list);
        assertThat(serviceUser.getAccountType(User.AccountType.Standard)).isEqualTo(list);
    }

    @Test
    public void testGetName() {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(repositoryUser.findByName("ibrahim")).thenReturn(list);
        assertThat(serviceUser.getName("ibrahim")).isEqualTo(list);
    }

    @Test
    public void testSave() {

        User user = getUser();
        when(repositoryUser.save(user)).thenReturn(user);
        assertThat(serviceUser.save(user)).isEqualTo(user);
    }

    @Test
    public void testDeleteAll() {

        repositoryUser.deleteAll();
        assertThat(serviceUser.getAll()).size().isEqualTo(0);
    }

    @Test
    public void testDelete() {

        User user = getUser();
        when(repositoryUser.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        assertThat(serviceUser.delete(Mockito.anyLong())).isEqualTo(user);
    }

    @Test
    public void testUpdate() {

        Map<String, User> userMap = new HashMap<>();

        userMap.put("Old_value", getUser());
        userMap.put("New_value", getUser());
        userMap.get("New_value").setName("Ahmet");

        when(repositoryUser.findById(Mockito.anyLong())).thenReturn(Optional.of(SerializationUtils.clone(userMap.get("Old_value"))));
        when(repositoryUser.save(Mockito.any(User.class))).thenReturn(userMap.get("New_value"));

        Map<String, User> userMapFromService = serviceUser.update(Mockito.anyLong(), userMap.get("New_value"));

        assertThat(userMap.get("Old_value").getName()).isEqualTo(userMapFromService.get("Old_value").getName());
        assertThat(userMap.get("New_value").getName()).isEqualTo(userMapFromService.get("New_value").getName());
    }

    private User getUser() {

        User user = new User();
        user.setName("ibrahim");
        user.setSurname("Koçak");
        user.setAccountType(User.AccountType.Standard);
        return user;
    }
}
