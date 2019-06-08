package com.indorse.assign.auth.authwizard.service.api;

import com.indorse.assign.auth.authwizard.dto.UserDto;
import com.indorse.assign.auth.authwizard.dto.UserLoginDto;
import com.indorse.assign.auth.authwizard.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Find user by email address
     * @param email
     * @return
     */
    Users findByEmail(String email);

    /**
     * Find user by email confirmation token
     * @param confirmationToken
     * @return
     */
    Users findByConfirmationToken(String confirmationToken);

    /**
     * Save user to the database
     * @param userLoginDto
     */
    String getUserStatus(UserLoginDto userLoginDto);

    /**
     * Save User and send confirmation email
     * @param userDto
     */
    void saveAndSendConfirmationMail(UserDto userDto);

    /**
     * Save or update user's password using confirmation token
     * @param token
     * @param password
     */
    void saveUserPassword(String token, String password);
}

