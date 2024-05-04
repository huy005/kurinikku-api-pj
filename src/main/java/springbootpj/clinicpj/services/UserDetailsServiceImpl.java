package springbootpj.clinicpj.services;

import springbootpj.clinicpj.dtos.MyUserDetails;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.getUserByEmail(email);
        User theUser = null;
        if (userResult.isPresent()) {
            theUser = userResult.get();
        } else {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(theUser);
    }
}
