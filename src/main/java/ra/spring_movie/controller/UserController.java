package ra.spring_movie.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.request.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.dto.response.TokenRespone;

import ra.spring_movie.services.impl.UserServiceImpl;




@RestController
@RequestMapping("/authentication/")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("login")
    public ResponseEntity<TokenRespone> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<MessageResponse> register(
            @RequestBody RegisterRequest registerRequest)  {
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping("confirm")
    public ResponseEntity<MessageResponse> confirm(@RequestBody ConfirmCodeRequest codeRequest) {
        return new ResponseEntity<>(userService.confirmEmail(codeRequest), HttpStatus.OK);
    }
    @PutMapping("logout")
    public ResponseEntity<MessageResponse> logout(@RequestParam String username){
        return new ResponseEntity<>(userService.logout(username),HttpStatus.OK);
    }

    @PutMapping("changepassword")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody changePassRequest changpass){
        return new ResponseEntity<>(userService.changePass(changpass), HttpStatus.OK);
    }
    @PostMapping("forgotPass")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestParam String email, @RequestParam String username){
        return new ResponseEntity<>(userService.forgotPassword(username,email), HttpStatus.OK);
    }
    @PutMapping("createnewpassword")
    public ResponseEntity<MessageResponse> createNewPassword(@RequestBody CreateNewPassRequest createNewPassRequest){
        return new ResponseEntity<>(userService.createNewPassword(createNewPassRequest), HttpStatus.OK);
    }
    @PostMapping("refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        userService.refreshToken(request,response) ;
    }
}
