package org.skywise.skywise.services;

import org.skywise.skywise.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
}
