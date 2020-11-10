package com.techdenovo.app.jwtspringbootapp.config;

import com.techdenovo.app.jwtspringbootapp.model.DAOUser;
import com.techdenovo.app.jwtspringbootapp.model.UserDto;
import com.techdenovo.app.jwtspringbootapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        DAOUser user = userRepository.findByUsername(username);
        if(user!=null){
            roles= Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(),user.getPassword(),roles);
        }

       throw new UsernameNotFoundException("User not found for "+username);
    }
    public DAOUser save(UserDto userDto){
        DAOUser newUser =  new DAOUser();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        return userRepository.save(newUser);
    }
}
