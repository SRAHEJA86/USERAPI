package com.herokuapp.api.controller;

import com.herokuapp.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserInfoResource.class)
class UserResourceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        this.restTemplate = new RestTemplate(requestFactory);
        this.mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void when_the_user_wants_to_fetch_list_of_London_users_and_users_around_London() {
        String url = "/London/users";
        try {
            mockMvc.perform(get(url))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonResponseForUsersInAndAroundLondon))
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
        public void when_the_user_sends_an_invalid_request() {
            String url = "/123/users";
            try {
                mockMvc.perform(get(url))
                        .andExpect(MockMvcResultMatchers.status().is(404))
                        .andDo(print())
                        .andReturn();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    @Test
    public void test_the_rest_client_by_mocking() {
        try {
            ResponseEntity<User[]> users = getResponseEntity();
            assertNotNull(users);
            assertEquals(users.getStatusCode(), HttpStatus.OK);
            mockServer.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void when_user_enters_city_name_to_fetch_user_details() {
     String url = "/city/London/users";
     try {
            mockMvc.perform(get(url))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonResponseForLondonUsers))
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void when_the_user_wants_to_fetch_list_of_all_users(){
        String url = "/users";
        try {
            mockMvc.perform(get(url))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private ResponseEntity<User[]> getResponseEntity() {
        String url = "https://bpdts-test-app.herokuapp.com/city/London/users";

        mockServer.expect(manyTimes(), requestTo(url)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        return restTemplate.getForEntity(url, User[].class);
    }

    String jsonResponse = "[{\"id\": 135, \"first_name\": \"Mechelle\", \"last_name\": \"Boam\", " +
            "\"email\": \"mboam3q@thetimes.co.uk\"," +
            " \"ip_address\": \"113.71.242.187\", \"latitude\": -6.5115909, " +
            "\"longitude\": 105.652983}, {\"id\": 396, \"first_name\": \"Terry\", \"last_name\":" +
            " \"Stowgill\", \"email\": \"tstowgillaz@webeden.co.uk\", \"ip_address\":" +
            " \"143.190.50.240\"," +
            " \"latitude\": -6.7098551, \"longitude\": 111.3479498}, {\"id\": 520, " +
            "\"first_name\": \"Andrew\"," +
            " \"last_name\": \"Seabrocke\", \"email\": \"aseabrockeef@indiegogo.com\"," +
            " \"ip_address\": \"28.146.197.176\", \"latitude\": \"27.69417\", \"longitude\": " +
            "\"109.73583\"}, " +
            "{\"id\": 658, \"first_name\": \"Stephen\", \"last_name\": \"Mapstone\", " +
            "\"email\": \"smapstonei9@bandcamp.com\", \"ip_address\": \"187.79.141.124\", " +
            "\"latitude\": -8.1844859, \"longitude\": 113.6680747}, {\"id\": 688, " +
            "\"first_name\": \"Tiffi\", \"last_name\": \"Colbertson\", \"email\": " +
            "\"tcolbertsonj3@vimeo.com\", \"ip_address\": \"141.49.93.0\", " +
            "\"latitude\": 37.13, \"longitude\": -84.08}, {\"id\": 794, " +
            "\"first_name\": \"Katee\", \"last_name\": \"Gopsall\", \"email\": " +
            "\"kgopsallm1@cam.ac.uk\", \"ip_address\": \"203.138.133.164\", " +
            "\"latitude\": 5.7204203, \"longitude\": 10.901604}]";

    String jsonResponseForLondonUsers = "{\"usersList\":[{\"id\":135,\"first_name\":\"Mechelle\",\"last_name\":\"Boam\"," +
            "\"email\":\"mboam3q@thetimes.co.uk\",\"ip_address\":\"113.71.242.187\",\"latitude\":-6.5115909," +
            "\"longitude\":105.652983},{\"id\":396,\"first_name\":\"Terry\",\"last_name\":\"Stowgill\"," +
            "\"email\":\"tstowgillaz@webeden.co.uk\",\"ip_address\":\"143.190.50.240\",\"latitude\":-6.7098551," +
            "\"longitude\":111.3479498},{\"id\":520,\"first_name\":\"Andrew\",\"last_name\":\"Seabrocke\"," +
            "\"email\":\"aseabrockeef@indiegogo.com\",\"ip_address\":\"28.146.197.176\",\"latitude\":27.69417," +
            "\"longitude\":109.73583},{\"id\":658,\"first_name\":\"Stephen\",\"last_name\":\"Mapstone\"," +
            "\"email\":\"smapstonei9@bandcamp.com\",\"ip_address\":\"187.79.141.124\",\"latitude\":-8.1844859," +
            "\"longitude\":113.6680747},{\"id\":688,\"first_name\":\"Tiffi\",\"last_name\":\"Colbertson\"," +
            "\"email\":\"tcolbertsonj3@vimeo.com\",\"ip_address\":\"141.49.93.0\",\"latitude\":37.13," +
            "\"longitude\":-84.08},{\"id\":794,\"first_name\":\"Katee\",\"last_name\":\"Gopsall\"," +
            "\"email\":\"kgopsallm1@cam.ac.uk\",\"ip_address\":\"203.138.133.164\",\"latitude\":5.7204203," +
            "\"longitude\":10.901604}]}";
    String jsonResponseForUsersInAndAroundLondon = "{\"usersList\":[{\"id\":266,\"first_name\":\"Ancell\"," +
        "\"last_name\":\"Garnsworthy\",\"email\":\"agarnsworthy7d@seattletimes.com\",\"ip_address\":\"67.4.69.137\"," +
            "\"latitude\":51.6553959,\"longitude\":0.0572553},{\"id\":322,\"first_name\":\"Hugo\",\"last_name\":" +
            "\"Lynd\",\"email\":\"hlynd8x@merriam-webster.com\",\"ip_address\":\"109.0.153.166\"," +
            "\"latitude\":51.6710832,\"longitude\":0.8078532},{\"id\":554,\"first_name\":\"Phyllys\"," +
            "\"last_name\":\"Hebbs\",\"email\":\"phebbsfd@umn.edu\",\"ip_address\":\"100.89.186.13\"," +
            "\"latitude\":51.5489435,\"longitude\":0.3860497},{\"id\":135,\"first_name\":\"Mechelle\"," +
            "\"last_name\":\"Boam\",\"email\":\"mboam3q@thetimes.co.uk\",\"ip_address\":\"113.71.242.187\"," +
            "\"latitude\":-6.5115909,\"longitude\":105.652983},{\"id\":396,\"first_name\":\"Terry\"," +
            "\"last_name\":\"Stowgill\",\"email\":\"tstowgillaz@webeden.co.uk\",\"ip_address\":\"143.190.50.240\"," +
            "\"latitude\":-6.7098551,\"longitude\":111.3479498},{\"id\":520,\"first_name\":\"Andrew\"," +
            "\"last_name\":\"Seabrocke\",\"email\":\"aseabrockeef@indiegogo.com\",\"ip_address\":\"28.146.197.176\"," +
            "\"latitude\":27.69417,\"longitude\":109.73583},{\"id\":658,\"first_name\":\"Stephen\"," +
            "\"last_name\":\"Mapstone\",\"email\":\"smapstonei9@bandcamp.com\",\"ip_address\":\"187.79.141.124\"," +
            "\"latitude\":-8.1844859,\"longitude\":113.6680747},{\"id\":688,\"first_name\":\"Tiffi\"," +
            "\"last_name\":\"Colbertson\",\"email\":\"tcolbertsonj3@vimeo.com\",\"ip_address\":\"141.49.93.0\"," +
            "\"latitude\":37.13,\"longitude\":-84.08},{\"id\":794,\"first_name\":\"Katee\"," +
            "\"last_name\":\"Gopsall\",\"email\":\"kgopsallm1@cam.ac.uk\",\"ip_address\":\"203.138.133.164\"," +
            "\"latitude\":5.7204203,\"longitude\":10.901604}]}";

}