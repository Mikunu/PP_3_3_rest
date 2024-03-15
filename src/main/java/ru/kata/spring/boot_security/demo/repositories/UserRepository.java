package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT p FROM User p LEFT JOIN FETCH p.roles WHERE p.id = :id")
    Optional<User> findByUserIdWithRoles(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM User p LEFT JOIN FETCH p.roles WHERE p.firstName = :firstName")
    Optional<User> findByFirstNameWithRoles(@Param("firstName") String firstName);

    @Query("SELECT DISTINCT p FROM User p LEFT JOIN FETCH p.roles")
    List<User> findAllWithRoles();
}
