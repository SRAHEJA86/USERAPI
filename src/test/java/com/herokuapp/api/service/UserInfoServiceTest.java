package com.herokuapp.api.service;

import com.herokuapp.api.controller.UserInfoResource;
import com.herokuapp.api.model.User;
import com.herokuapp.api.model.UsersInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserInfoServiceTest {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserInfoResource usersInfoResource;

    @Test
    public void testGetUsersAroundLondon() {
        UsersInfo usersInfo = usersInfoResource.getUsers();
        List<User> usersAroundLondon = userInfoService.getUsersAroundLondon(usersInfo);
        assertEquals(3, usersAroundLondon.size());
    }

    @Test
    public void testCalculateDistanceBetweenCoordinates(){
        double latitudeOfLondon = 51.5074;
        double longitudeOfLondon = -0.12778;
        double latitudeOfReading = 51.4543;
        double longitudeOfReading = 0.9781;
        double distance = userInfoService.
                calculateDistanceBetweenCoordinates(latitudeOfLondon,longitudeOfLondon,
                        latitudeOfReading,longitudeOfReading);
        assertTrue(distance < 50.0);
    }

}