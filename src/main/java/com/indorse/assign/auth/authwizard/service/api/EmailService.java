package com.indorse.assign.auth.authwizard.service.api;

import com.indorse.assign.auth.authwizard.model.Users;

public interface EmailService {

    /**
     * Send registration mail with confirmation token link
     * @param user
     * @return
     */
    boolean sendUserRegistrationEmail(Users user);
}
