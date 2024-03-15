package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> getAllUsers();

    User findUserByName(String firstName);

    void updateUser(User person);

    void removeUser(Long id);

    User findOneById(Long id);


    void create(User user);

    Optional<User> isUserExist(String emil);
}
