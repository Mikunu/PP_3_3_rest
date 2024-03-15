package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByName(String firstName) {
        Optional<User> user = userRepository.findByFirstNameWithRoles(firstName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User: " + firstName + " not found");
        }
        return user.get();
    }


    @Override
    public void updateUser(User updatedPerson) {

        User existingPerson = userRepository.findById(updatedPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + updatedPerson.getId()));

        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        existingPerson.setRoles(updatedPerson.getRoles());
        userRepository.save(existingPerson);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public User findOneById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with ID: " + id + " not found");
        }
        return user.get();
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> isUserExist(String name) {
        return userRepository.findByFirstNameWithRoles(name);
    }
}
