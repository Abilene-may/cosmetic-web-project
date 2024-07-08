package org.example.cosmeticwebpro.services;

import lombok.AllArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    Optional<User> user =
        Optional.ofNullable(
            userRepository
                .findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                    () -> new UsernameNotFoundException("User not exists by Username or Email")));

    Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("USER"));
    //        Set<GrantedAuthority> authorities = Set.of(new
    // SimpleGrantedAuthority(user.get().getRoleName()));
    return new org.springframework.security.core.userdetails.User(
        usernameOrEmail, user.get().getPassword(), authorities);
  }
}
