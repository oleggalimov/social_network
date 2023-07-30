package org.olegalimov.examples.social.network.core.service;

import lombok.AllArgsConstructor;
import org.olegalimov.examples.social.network.core.dto.SocialNetworkUserDetails;
import org.olegalimov.examples.social.network.core.entity.User;
import org.olegalimov.examples.social.network.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SocialNetworkUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);

        return new SocialNetworkUserDetails(user);
    }
}
