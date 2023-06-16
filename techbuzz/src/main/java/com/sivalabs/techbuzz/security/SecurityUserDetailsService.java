package com.sivalabs.techbuzz.security;

import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import java.util.Optional;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<SecurityUser> securityUser =
                userRepository.findByEmail(username).map(SecurityUser::new);
        if (securityUser.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
        if (!securityUser.orElseThrow().isEnabled()) {
            throw new DisabledException("Account verification is pending");
        }
        return securityUser.orElseThrow();
    }
}
