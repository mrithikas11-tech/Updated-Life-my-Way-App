package com.canigetafiver.lifemyway.service;

import com.canigetafiver.lifemyway.api.User;

public class UserService {
    public User getUser() {
        return new User("Test User");
    }
}