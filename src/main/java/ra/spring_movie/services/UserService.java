package ra.spring_movie.services;

import jakarta.mail.MessagingException;
import ra.spring_movie.dto.request.CreateNewPassRequest;
import ra.spring_movie.dto.request.LoginRequest;
import ra.spring_movie.dto.request.RegisterRequest;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.dto.response.TokenRespone;
import ra.spring_movie.dto.request.changePassRequest;

import java.io.UnsupportedEncodingException;

public interface UserService {
    TokenRespone login(LoginRequest loginRequest);
    MessageResponse register(RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException;
    public MessageResponse logout(String username);

    MessageResponse changePass(changePassRequest changepass);
    public MessageResponse forgotPassword(String username, String email);
    public MessageResponse createNewPassword(CreateNewPassRequest newPassRequest);
}
