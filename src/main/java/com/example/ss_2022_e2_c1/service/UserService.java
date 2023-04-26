package com.example.ss_2022_e2_c1.service;
import com.example.ss_2022_e2_c1.entities.SecurityUser;
import com.example.ss_2022_e2_c1.exception.MessageFactory;
import com.example.ss_2022_e2_c1.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public record UserService(UserRepository userRepository) implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MessageFactory.ERROR1, username)));
    }
}
