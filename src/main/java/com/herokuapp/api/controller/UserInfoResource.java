package com.herokuapp.api.controller;

import com.herokuapp.api.model.UsersInfo;
import com.herokuapp.api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/")
public class UserInfoResource {

    @Autowired
    UserInfoService userInfoService;


    /**
     * Returns the list of all users who are either living in London or whose current coordinates
     * are within 50 miles of London
     * @return userInfo object
     */
    @RequestMapping("/London/users")
    public UsersInfo getUsersInAndAroundLondon() throws Exception {
        return userInfoService.getUsersInAndAroundLondon();
    }

    /**
     *Returns the list of users in a city
     * @param city city name
     * @return list of all users
     */
    @RequestMapping("/city/{city}/users")
    public UsersInfo getUsersBasedOnCity(@PathVariable String city){
        return userInfoService.getUsersBasedOnCity(city);
    }

    /**
     * Returns the list of all users
     * @return all users
     */
    @RequestMapping("/users")
    public UsersInfo getUsers(){
         return userInfoService.getUsers();
    }


}
