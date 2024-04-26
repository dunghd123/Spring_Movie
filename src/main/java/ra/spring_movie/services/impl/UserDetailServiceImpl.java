package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.spring_movie.entity.User;
import ra.spring_movie.model.UserCustomDetail;
import ra.spring_movie.repository.UserRepo;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findbyUser = userRepo.findByUsername(username);
        return UserCustomDetail.builder().user(findbyUser.get()).build();
    }
}
