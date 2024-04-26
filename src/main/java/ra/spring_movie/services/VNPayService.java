package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.User;
import ra.spring_movie.model.BankCustom;

public interface VNPayService {

    MessageResponse payment(BankCustom bankCustom,int billId);
    void sendEmail(int billid, BankCustom bankCustom);
}
