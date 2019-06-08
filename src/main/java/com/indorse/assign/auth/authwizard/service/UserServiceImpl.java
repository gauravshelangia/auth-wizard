package com.indorse.assign.auth.authwizard.service;

import com.indorse.assign.auth.authwizard.dto.UserDto;
import com.indorse.assign.auth.authwizard.dto.UserLoginDto;
import com.indorse.assign.auth.authwizard.model.Users;
import com.indorse.assign.auth.authwizard.repository.UserRepository;
import com.indorse.assign.auth.authwizard.service.api.EmailService;
import com.indorse.assign.auth.authwizard.service.api.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;


    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public String getUserStatus(UserLoginDto userLoginDto) {
        String userStatus = null;
        Users user = userRepository.findByEmail(userLoginDto.getEmail());
        if (ObjectUtils.isEmpty(user)){
            userStatus = "User Doesn't exist with email = " + userLoginDto.getEmail();
        }else {
            if (!user.isEnabled()){
                userStatus = "Account not verified can't login";
            } else {
                if (bCryptPasswordEncoder.encode(userLoginDto.getPassword()).equals(user.getPassword())){
                    userStatus = "Success. Logged In success fully!!!";
                }
            }
        }

        return userStatus;
    }

    @Override
    public void saveAndSendConfirmationMail(UserDto userDto) {
        Users users = new Users();
        BeanUtils.copyProperties(userDto, users);
        users.setEnabled(false);
        users.setConfirmationToken(UUID.randomUUID().toString());
        users = userRepository.save(users);
        emailService.sendUserRegistrationEmail(users);

    }

    @Override
    public void saveUserPassword(String token, String password) {
        Users user = userRepository.findByConfirmationToken(token);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
