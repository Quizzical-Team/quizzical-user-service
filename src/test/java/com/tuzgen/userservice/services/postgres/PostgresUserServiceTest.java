package com.tuzgen.userservice.services.postgres;

import com.tuzgen.userservice.entities.User;
import com.tuzgen.userservice.repositories.UserRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.verify;

@DataJpaTest
class PostgresUserServiceTest {

    @Mock
    private UserRepository userRepository;
    private AutoCloseable autoCloseable;
    private PostgresUserService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PostgresUserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @Disabled
    void itShouldCheckIfUserExistsByUsername() {
        underTest.getUsersPaginated(0, 0);
        verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void getUsersPaginated() {
//        underTest.getUsersPaginated();
    }

    @Test
    void canGetAllUsers() {

        underTest.getAllUsers();

        verify(userRepository).findAll();
    }

    @Test
    void logInWithUsernameAndPassword() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void addUsers() {
    }
}