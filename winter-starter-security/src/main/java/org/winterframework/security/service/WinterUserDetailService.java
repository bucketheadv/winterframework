package org.winterframework.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.winterframework.security.properties.WinterSecurityProperties;

import java.util.Objects;

/**
 * @author sven
 * Created on 2023/6/11 6:49 PM
 */
@Service
@ConditionalOnProperty(prefix = "winter.security.user", name = "name")
public class WinterUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WinterSecurityProperties winterSecurityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (winterSecurityProperties.getUser() != null) {
            if (Objects.equals(username, winterSecurityProperties.getUser().getName())) {
                return new User(username,
                        passwordEncoder.encode(winterSecurityProperties.getUser().getPassword()),
                        AuthorityUtils.createAuthorityList("ADMIN"));
            }
        }
        return null;
    }
}
