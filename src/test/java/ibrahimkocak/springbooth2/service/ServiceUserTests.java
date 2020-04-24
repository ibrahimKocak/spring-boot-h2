package ibrahimkocak.springbooth2.service;

import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.repository.IRepositoryUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        assertThat(serviceUser.getAll().size()).isEqualTo(2);
    }

    @Test
    public void testGetId() {

        Optional<User> optionalUser = Optional.of(getUser());
        when(repositoryUser.findById(1L)).thenReturn(optionalUser);
        assertThat(serviceUser.getId(1L)).isEqualTo(optionalUser);
    }


    @Test
    public void testGetAccountType() {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(repositoryUser.findByAccountType(User.AccountType.Standard)).thenReturn(list);
        assertThat(serviceUser.getAccountType(User.AccountType.Standard)).isEqualTo(list);
        assertThat(serviceUser.getAccountType(User.AccountType.Standard)).size().isEqualTo(2);
    }

    @Test
    public void testGetName() {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(repositoryUser.findByName("ibrahim")).thenReturn(list);
        assertThat(serviceUser.getName("ibrahim")).isEqualTo(list);
        assertThat(serviceUser.getName("ibrahim")).size().isEqualTo(2);
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

        Optional<User> optionalUser = Optional.of(getUser());
        when(repositoryUser.findById(Mockito.anyLong())).thenReturn(optionalUser);
        assertThat(serviceUser.delete(Mockito.anyLong())).isEqualTo(optionalUser.get());
    }

    @Test
    public void testUpdate() {

        Optional<User> optionalUser = Optional.of(getUser());
        when(repositoryUser.findById(Mockito.anyLong())).thenReturn(optionalUser);
        optionalUser.get().setName("Ahmet");
        when(repositoryUser.save(optionalUser.get())).thenReturn(optionalUser.get());
        assertThat(serviceUser.update(Mockito.anyLong(), optionalUser.get())).isEqualTo(optionalUser.get());
    }

    private User getUser() {

        User user = new User();
        user.setName("ibrahim");
        user.setSurname("Koçak");
        user.setAccountType(User.AccountType.Standard);
        return user;
    }
}
