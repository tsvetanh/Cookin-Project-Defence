package com.exam.service;

import com.exam.model.service.UserServiceModel;

public interface UserService {
    void register(UserServiceModel userServiceModel, boolean admin);
    boolean login(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String name);

    UserServiceModel findById(Long id);

    UserServiceModel findByUsernameAndPassword(String username, String password);

}
