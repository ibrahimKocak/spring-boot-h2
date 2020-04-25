package ibrahimkocak.springbooth2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ibrahimkocak.springbooth2.model.User;
import ibrahimkocak.springbooth2.service.ServiceUser;
import org.apache.log4j.Logger;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerUser.class)
public class ControllerUserTests {

    private final Logger logger = Logger.getLogger(ControllerUserTests.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceUser serviceUser;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGelAll() throws Exception {

        User user0 = getUser();

        List<User> list = new ArrayList<>();
        list.add(user0);
        list.add(new User());

        when(serviceUser.getAll()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/getAll"))
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

        MvcResult result = mockMvc.perform(get("/get/1"))
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

        MvcResult result = mockMvc.perform(get("/getByName/ibrahim"))
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

        MvcResult result = mockMvc.perform(get("/getByAccountType/" + User.AccountType.Standard))
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

        MvcResult result = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toJson(objectMapper)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    public void testDeleteAll() throws Exception {

        mockMvc.perform(delete("/deleteAll"))
                //.andDo(print())
                .andExpect(status().isOk());

        assertThat(serviceUser.getAll().size()).isEqualTo(0);
    }

    @Test
    public void testDelete() throws Exception {

        User user = getUser();

        when(serviceUser.delete(Mockito.anyLong())).thenReturn(user);

        MvcResult result = mockMvc.perform(delete("/delete/1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    public void testUpdate() throws Exception {

        User userOld = new User();
        User userNew = getUser();
        Map<String, User> map = new HashMap<>();
        map.put("Old Value", userOld);
        map.put("New Value", userNew);

        when(serviceUser.getId(Mockito.anyLong())).thenReturn(Optional.of(userOld));
        when(serviceUser.update(Mockito.anyLong(), Mockito.any(User.class))).thenReturn(userNew);

        MvcResult result = mockMvc.perform(put("/put/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userNew.toJson(objectMapper)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(map));
    }

    private User getUser() {

        User user = new User();
        user.setName("ibrahim");
        user.setSurname("Kocak");
        return user;
    }

}