package ra.spring_movie.services.impl;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.request.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.dto.response.TokenRespone;
import ra.spring_movie.entity.ConfirmEmail;
import ra.spring_movie.entity.RefreshToken;
import ra.spring_movie.entity.Role;
import ra.spring_movie.entity.User;
import ra.spring_movie.jwt.JwtTokenProvider;
import ra.spring_movie.model.UserCustomDetail;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.UserService;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final ConfirmEmailRepo confirmEmailRepo;
    private final UserRepo userRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final RoleRepo roleRepo;
    private final UserStatusRepo userStatusRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;
    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
    public void generateCode(User user){
        String randomCode= generateRandomString(6);
        ConfirmEmail confirmEmail= new ConfirmEmail();
        confirmEmail.setUser(user);
        confirmEmail.setExpiredTime(new Date(System.currentTimeMillis()+300000));
        confirmEmail.setConfirmCode(randomCode);
        confirmEmailRepo.save(confirmEmail);
    }
    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        Optional<Role> findbyRole= roleRepo.findByRoleName(registerRequest.getRole().getRoleName().name());
        User user= User
                .userBuilder()
                .Username(registerRequest.getUsername())
                .Password(passwordEncoder.encode(registerRequest.getPassword()))
                .Email(registerRequest.getEmail())
                .Name(registerRequest.getName())
                .PhoneNumber(registerRequest.getPhonenumber())
                .IsActive(true)
                .userStatus(userStatusRepo.findById(1).get())
                .role(findbyRole.get())
                .build();
        userRepo.save(user);
        generateCode(user);
        sendVerificationEmail(user);
        return MessageResponse.builder().message("Check your email to verify registration!!!").build();
    }
    @Override
    public TokenRespone login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        Optional<User> findbyuser = userRepo.findByUsername(loginRequest.getUsername());
        if(findbyuser.isPresent()){
            findbyuser.get().setUserStatus(userStatusRepo.findById(2).get());
            userRepo.save(findbyuser.get());
            String jwtToken= jwtTokenProvider.generateToken(new UserCustomDetail(findbyuser.get()));
            String jwtRefreshToken= jwtTokenProvider.generateRefreshToken(new UserCustomDetail(findbyuser.get()));

            RefreshToken refreshToken= new RefreshToken();
            refreshToken.setUser(findbyuser.get());
            refreshToken.setToken(jwtRefreshToken);
            refreshToken.setExpiredTime(jwtTokenProvider.extractExpiration(jwtRefreshToken));
            refreshTokenRepo.save(refreshToken);

            return TokenRespone
                    .builder()
                    .accessToken(jwtToken)
                    .refreshToken(jwtRefreshToken)
                    .role(findbyuser.get().getRole().getRoleName().name())
                    .build();
        }
        return null;
    }
    @Override
    public MessageResponse logout(String username) {
        Optional<User> user= userRepo.findByUsername(username);
        if(user.isEmpty()){
            return MessageResponse.builder().message("Logout Failed!!").build();
        }else {
            user.get().setUserStatus(userStatusRepo.findById(1).get());
            for(RefreshToken refreshToken: refreshTokenRepo.findAll()){
                if(refreshToken.getUser().getId()==user.get().getId()){
                    refreshTokenRepo.delete(refreshToken);
                }
            }
            userRepo.save(user.get());
            return MessageResponse.builder().message("Logout Account "+username+" Successfully!!").build();
        }
    }
    private void sendVerificationEmail(User user) {
        for(ConfirmEmail ce: confirmEmailRepo.findAll()){
            if(!ce.isIsConfirm() && ce.getUser().getId()== user.getId()){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Verification Code");
                message.setText("Your verification code is: " + ce.getConfirmCode());
                mailSender.send(message);
            }
        }

    }
    public MessageResponse confirmEmail(ConfirmCodeRequest codeRequest){
        Optional<ConfirmEmail> confirmEmail= confirmEmailRepo.findByConfirmCode(codeRequest.getCode());
        if(confirmEmail.isPresent()){
            // nếu code chua het han va chua dc confirm
            if(confirmEmail.get().getExpiredTime().after(new Date()) && !confirmEmail.get().isIsConfirm()
            ){
                confirmEmail.get().setIsConfirm(true);
                confirmEmailRepo.save(confirmEmail.get());
                return MessageResponse.builder().message("Confirm Successfully!!!").build();
            }
        }
        return MessageResponse.builder().message("Confirm Failed!!!").build();
    }
    @Override
    public MessageResponse changePass(changePassRequest changepass) {
        Optional<User> user= userRepo.findByUsername(changepass.getUsername());
        if (user.isEmpty()){
            return MessageResponse.builder().message("User does not exist!!").build();
        }
        else {
            if(user.get().getUserStatus().getId()==2 && passwordEncoder.matches(changepass.getOldPassword(),user.get().getPassword())
            ){
                if(changepass.getNewPassword().equals(changepass.getOldPassword())){
                    return MessageResponse.builder().message("new password is the same old password!!!").build();
                }else {
                    user.get().setPassword(passwordEncoder.encode(changepass.getNewPassword()));
                    user.get().setUserStatus(userStatusRepo.findById(1).get());
                    userRepo.save(user.get());
                    return MessageResponse.builder().message("Change Password Successfully. Please login again!!").build();
                }
            }
        }
        return MessageResponse.builder().message("Change Password Failed!!!").build();
    }

    @Override
    public MessageResponse forgotPassword( String username, String email){
        Optional<User> user= userRepo.findByUsername(username);
        if(user.isEmpty()){
            return MessageResponse.builder().message("User Does Not Exist!!").build();
        }else {
            if(user.get().getEmail().equals(email)){
                    generateCode(user.get());
                    sendVerificationEmail(user.get());
                return MessageResponse.builder().message(
                        "Confirm code is sent to "+email+". Please check your email!!"
                ).build();
            }else {
                return MessageResponse.builder().message("Email is not correct!!!").build();
            }
        }
    }
    @Override
    public MessageResponse createNewPassword(CreateNewPassRequest newPassRequest){
        Optional<User> user= userRepo.findByUsername(newPassRequest.getUsername());
        if(user.isPresent()){
            user.get().setPassword(passwordEncoder.encode(newPassRequest.getNewpass()));
            user.get().setUserStatus(userStatusRepo.findById(1).get());
            userRepo.save(user.get());
            return MessageResponse.builder().message("You have changed your password. Please login again!!").build();
        }else {
            return MessageResponse.builder().message("Create new password failed!!!").build();
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authentication= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authentication==null ||!authentication.startsWith("Bearer ")){
            return;
        }
        refreshToken= authentication.substring(7);
        username= jwtTokenProvider.extractUsername(refreshToken);

        if(username!=null){
            UserCustomDetail user= this.userRepo.findByUsername(username).orElseThrow();
            if(jwtTokenProvider.isTokenValid(refreshToken, user)){
                String accessToken= jwtTokenProvider.generateToken(user);
            }
        }
    }
}
