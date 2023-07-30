package org.olegalimov.examples.social.network.core.dto;

import lombok.Data;
import org.olegalimov.examples.social.network.core.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SocialNetworkUserDetails implements UserDetails {

    private String username;
    private String password;

    //this part is ignored
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    Collection<? extends GrantedAuthority> authorities = List.of();

    public SocialNetworkUserDetails(User user) {
        this.username = user.getUserId();
        this.password = user.getPassword();
    }
}
