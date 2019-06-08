package com.indorse.assign.auth.authwizard.repository;

import com.indorse.assign.auth.authwizard.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    /**
     * Find user by email address
     * @param email
     * @return
     */
    Users findByEmail(String email);

    /**
     * Find user by confirmation token
     * @param confirmationToken
     * @return
     */
    Users findByConfirmationToken(String confirmationToken);
}
