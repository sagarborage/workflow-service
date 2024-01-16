package com.sowermate.security.services;

import com.sowermate.security.projections.UserAuthSuccessDetailsProjection;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    UserAuth registerUser(String username, String password, UserRole role);
    UserAuth findByUsername(String username);
    /*UserAuthSuccessDetailsProjection findUserAuthSuccessDetails(String username);

*/
}
