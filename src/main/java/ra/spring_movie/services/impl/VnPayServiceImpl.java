package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Bill;
import ra.spring_movie.model.BankCustom;
import ra.spring_movie.repository.BillRepo;
import ra.spring_movie.repository.BillStatusRepo;
import ra.spring_movie.services.VNPayService;

import java.util.Optional;

@Service
public class VnPayServiceImpl implements VNPayService {
    @Autowired
    private BillRepo billRepo;
    @Autowired
    private BillStatusRepo billStatusRepo;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public MessageResponse payment(BankCustom bankCustom, int billId) {
        Optional<Bill> bill= billRepo.findById(billId);
        if(bill.isEmpty()){
            return MessageResponse.builder().message("Bill invalid!!!").build();
        }else {
            if(bankCustom.getPayMoney()== bill.get().getTotalMoney()){
                bill.get().setBillStatus(billStatusRepo.findByName("Đã thanh toán").get());
                billRepo.save(bill.get());
                sendEmail(billId,bankCustom);
                return MessageResponse.builder().message("Your Payment is Successfully. It's sent to your Email:"+bill.get().getUser().getEmail()).build();
            }else {
                return MessageResponse.builder().message("You must pay "+ bill.get().getTotalMoney()+" VND").build();
            }
        }

    }
    @Override
    public void sendEmail(int billId,BankCustom bankCustom ) {
        Optional<Bill> bill= billRepo.findById(billId);
        if(bill.isPresent()){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(bill.get().getUser().getEmail());
            message.setSubject("Hóa đơn xem phim");
            message.setText("Thông Tin Chi Tiết:\n" +
                    "Địa chỉ email: " + bill.get().getUser().getEmail() + "\n\n" +
                    "Ngân Hàng:" + bankCustom.getBankName() + "\n" +
                    "Tên Chủ Tài Khoản: " + bankCustom.getOwnCardName() + "\n" +
                    "Số Tiền: " + bankCustom.getPayMoney() + "\n\n" +
                    "Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi. Nếu có bất kỳ thắc mắc hoặc yêu cầu hỗ trợ nào, vui lòng liên hệ với chúng tôi qua email hoặc số điện thoại được cung cấp bên dưới.\n" +
                    "Trân trọng,\n" +
                    "[Beta Cineplex]");
            javaMailSender.send(message);
        }
    }
}
