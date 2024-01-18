package com.sowermate.user.services.impl;

import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.services.RoleTypeService;
import com.sowermate.tenantService.services.TenantService;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import com.sowermate.user.repositories.UserAuthRepository;
import com.sowermate.user.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <h1>UserAuthServiceImpl class</h1>
 * Provides the blueprint for UserAuth-related operations which include.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-11-20
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private RoleTypeService roleTypeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAuth registerUser(String username, String phone,String firstName,String lastName, String tenantUuid,String roleUuid, Boolean isActive) {
        Long tenantId = this.tenantService.getTenantId(tenantUuid);
        Long roleId = this.roleTypeService.getRoleTypeId(roleUuid);
        UserAuth userAuth = new UserAuth();
        userAuth.setTenantId(tenantId);
        userAuth.setRoleId(roleId);
        userAuth.setFirstName(firstName);
        userAuth.setLastName(lastName);
        userAuth.setUsername(username);
        userAuth.setPhone(phone);
        userAuth.setIsEnabled(false);
        userAuth.setIsEmailVerified(false);
        userAuth.setIsPhoneVerified(false);
        userAuth.setIsActive(isActive);
        userAuth.setFailedAttempt(1L);
        userAuth.setIsAccountNonExpired(true);
        userAuth.setIsAccountNonLocked(true);
        userAuth.setIsCredentialsNonExpired(true);
        return this.userAuthRepository.save(userAuth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAuth findByUsername(String username) {
        return this.userAuthRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getUserId(String uuid) {
        return this.userAuthRepository.findIdByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("User", "uuid", uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAuth getUserAuth(String uuid) {
        UserAuth userAuth = this.userAuthRepository.findUserAuthByUuid(uuid);
        if (userAuth == null)
            throw new ResourceNotFoundException("User", "uuid", uuid);
        return userAuth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserUuid(String email) {
        return this.userAuthRepository.findUuidByUsername(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public Optional<UserAuth> findByUserId(Long userId) {
        return this.userAuthRepository.findUserAuthById(userId);
    }

}
