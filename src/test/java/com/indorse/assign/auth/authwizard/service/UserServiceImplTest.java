package com.indorse.assign.auth.authwizard.service;

import com.indorse.assign.auth.authwizard.dto.UserDto;
import com.indorse.assign.auth.authwizard.model.Users;
import com.indorse.assign.auth.authwizard.repository.UserRepository;
import com.indorse.assign.auth.authwizard.service.UserServiceImpl;
import com.indorse.assign.auth.authwizard.service.api.EmailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private static final String EMAIL = "test@test.com";
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String CNF_TOKEN = "CNF";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<Users> usersArgumentCaptor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(bCryptPasswordEncoder);
        verifyNoMoreInteractions(emailService);
    }

    @Test
    public void test_findByEmail(){
        Users user = new Users();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        when(userRepository.findByEmail(EMAIL)).thenReturn(user);
        Users resultUser = userService.findByEmail(EMAIL);
        verify(userRepository).findByEmail(EMAIL);
        assertEquals(resultUser.getFirstName(), FIRST_NAME);
        assertEquals(resultUser.getLastName(), LAST_NAME);
    }

    @Test
    public void test_findByEmailConfirmationToken(){
        Users user = new Users();
        user.setFirstName(FIRST_NAME);
        user.setConfirmationToken(CNF_TOKEN);
        user.setEmail(EMAIL);
        when(userRepository.findByConfirmationToken(CNF_TOKEN)).thenReturn(user);
        Users resultUser = userService.findByConfirmationToken(CNF_TOKEN);
        verify(userRepository).findByConfirmationToken(CNF_TOKEN);
        assertEquals(resultUser.getFirstName(), FIRST_NAME);
        assertEquals(resultUser.getConfirmationToken(), CNF_TOKEN);
    }

    @Test
    public void test_saveAndSendConfirmationMail(){
        Users users = new Users();
        users.setFirstName(FIRST_NAME);
        users.setEmail(EMAIL);
        UserDto user = new UserDto();
        user.setFirstName(FIRST_NAME);
        user.setEmail(EMAIL);
        when(userRepository.save(any())).thenReturn(users);
        when(emailService.sendUserRegistrationEmail(any())).thenReturn(true);
        userService.saveAndSendConfirmationMail(user);
        verify(userRepository).save(usersArgumentCaptor.capture());
        assertEquals(usersArgumentCaptor.getAllValues().get(0).getEmail(), EMAIL);
        verify(emailService).sendUserRegistrationEmail(usersArgumentCaptor.capture());
        assertEquals(usersArgumentCaptor.getAllValues().get(1).getFirstName(), FIRST_NAME);
        assertFalse(usersArgumentCaptor.getAllValues().get(1).isEnabled());
    }



}