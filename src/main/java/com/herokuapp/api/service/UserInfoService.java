package com.herokuapp.api.service;

import com.herokuapp.api.model.User;
import com.herokuapp.api.model.UsersInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.sort;

@ConfigurationProperties(prefix="endpoint")
public class UserInfoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UsersInfo usersInfo;

    private final double latitudeOfLondon = 51.5074;
    private final double longitudeOfLondon = -0.1278;


    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    /**
     *Fetches list of all users around 50 miles of London.
     * @param usersInfo users info object
     * @return list of users around London
     */
    public List<User> getUsersAroundLondon (UsersInfo usersInfo) {
        List<User> usersList = usersInfo.getUsersList();
        List<User> userListAroundLondon;
        userListAroundLondon = usersList.stream()
                .filter(user -> {
                    double distance = calculateDistanceBetweenCoordinates(user.getLatitude(), user.getLongitude(),latitudeOfLondon,
                           longitudeOfLondon);
                    return distance <= 50.0;
                }).collect(Collectors.toList());

        return userListAroundLondon;
    }

    /**
     * Calculates the distance between coordinates of London and user's location.
     * @param latitudeOfLondon latitude of London
     * @param longitudeOfLondon longitude of London
     * @param lat2 Latitude of user's location
     * @param lon2 longitude of user's location
     * @return distance
     */
    public double calculateDistanceBetweenCoordinates(double latitudeOfLondon, double longitudeOfLondon,
                                                      double lat2, double lon2) {
        if ((latitudeOfLondon == lat2) && (longitudeOfLondon == lon2)) {
            return 0;
        }
        else {
            double theta = longitudeOfLondon - lon2;
            double distance = Math.sin(Math.toRadians(latitudeOfLondon)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(latitudeOfLondon)) * Math.cos(Math.toRadians(lat2)) *
                      Math.cos(Math.toRadians(theta));
            distance = Math.acos(distance);
            distance = Math.toDegrees(distance);
            distance = distance * 60 * 1.1515;
            return (distance);
        }
    }

    /**
     *
     */
    private UsersInfo getUsersInfo(User[] user) {
        usersInfo.setUsersList(Arrays.asList(user));
        return usersInfo;
    }

    /**
     *Returns the list of users in a city
     * @param city city name
     * @return list of all users
     */
    public UsersInfo getUsersBasedOnCity(String city) {
        ResponseEntity<User[]> responseEntity = restTemplate.
                getForEntity(url + "/city/" + city + "/users", User[].class);
        User[] user = responseEntity.getStatusCode() == HttpStatus.OK ? responseEntity.getBody() : null;
        return getUsersInfo(user);
    }

    /**
     * Returns the list of all users
     * @return all users
     */
    public UsersInfo getUsers() {
            ResponseEntity<User[]> responseEntity = restTemplate.
                    getForEntity(url + "/users", User[].class);
            User[] user = responseEntity.getStatusCode() == HttpStatus.OK ? responseEntity.getBody() : null;
            return getUsersInfo(user);
    }

    /**
     * Returns the list of all users who are either living in London or whose current coordinates
     * are within 50 miles of London
     * @return userInfo object
     */
    public UsersInfo getUsersInAndAroundLondon() throws Exception{
        ResponseEntity<User[]> responseEntity = restTemplate.
                getForEntity(url +"/users", User[].class);
        User[] user = responseEntity.getStatusCode() == HttpStatus.OK ? responseEntity.getBody() : null;
        List<User> usersAroundLondon = getUsersAroundLondon(getUsersInfo(user));
        usersAroundLondon.addAll(getUsersBasedOnCity("London").getUsersList());
        getSortedList(usersAroundLondon);
        usersInfo.setUsersList(usersAroundLondon);
        return usersInfo;
    }

    /**
     * Performs the sorting of the list.
     * @param usersAroundLondon List of all users staying in and around London
     */
    private void getSortedList(List<User> usersAroundLondon) {
        sort(usersAroundLondon, Comparator.comparingInt(User::getId));
    }
}
