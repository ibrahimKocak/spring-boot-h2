package ibrahimkocak.springbooth2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.service.ServiceUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerUser.class)
public class ControllerUserTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceUser serviceUser;

    @Test
    public void testGelAll() throws Exception {

        User user0 = getUser();

        List<User> list = new ArrayList<>();
        list.add(user0);
        list.add(new User());

        when(serviceUser.getAll()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/users"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(list));
    }

    @Test
    public void testGet() throws Exception {

        User user = getUser();

        when(serviceUser.getId(Mockito.anyLong())).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(get("/users/1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    public void testGetByName() throws Exception {

        User user0 = getUser();

        List<User> list = new ArrayList<>();
        list.add(user0);
        list.add(new User());

        when(serviceUser.getName(Mockito.anyString())).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/usersByName/ibrahim"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(list));
    }

    @Test
    public void testGetByAccountType() throws Exception {

        User user0 = getUser();

        List<User> list = new ArrayList<>();
        list.add(user0);
        list.add(new User());

        when(serviceUser.getAccountType(User.AccountType.Standard)).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/usersByAccountType/" + User.AccountType.Standard))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(list));
    }

    @Test
    public void testSave() throws Exception {

        User user = getUser();

        when(serviceUser.save(Mockito.any(User.class))).thenReturn(user);

        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    public void testDeleteAll() throws Exception {

        List<User> list = new ArrayList<>();
        list.add(new User());

        doAnswer((i) -> {

            list.clear();
            return true;

        }).when(serviceUser).deleteAll();

        mockMvc.perform(delete("/users"))
                //.andDo(print())
                .andExpect(status().isOk());

        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void testDelete() throws Exception {

        User userDeleted = getUser();
        Map<String, User> map = Collections.singletonMap("Deleted Value", getUser());

        when(serviceUser.getId(Mockito.anyLong())).thenReturn(Optional.of(userDeleted));
        when(serviceUser.delete(Mockito.anyLong())).thenReturn(userDeleted);

        MvcResult result = mockMvc.perform(delete("/users/1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(map));
    }

    @Test
    public void testUpdate() throws Exception {

        User userOld = new User();
        User userNew = getUser();
        Map<String, User> map = new HashMap<>();
        map.put("Old_value", userOld);
        map.put("New_value", userNew);

        when(serviceUser.getId(Mockito.anyLong())).thenReturn(Optional.of(userOld));
        when(serviceUser.update(Mockito.anyLong(), Mockito.any(User.class))).thenReturn(map);

        MvcResult result = mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userNew)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(map));
    }

    //Wrong Request Tests
    @Test
    public void testBadRequest() throws Exception {

        List<User> list = new ArrayList<>();
        list.add(getUser());
        list.add(getUser());

        when(serviceUser.getAll()).thenReturn(list);

        mockMvc.perform(get("/userss"))
                //.andDo(print())
                .andExpect(status().isNotFound());
    }

    private User getUser() {

        User user = new User();
        user.setName("ibrahim");
        user.setSurname("Kocak");
        return user;
    }

}