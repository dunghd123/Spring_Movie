package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.model.BillCustom;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.BillService;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepo billRepo;
    @Autowired
    private BillFoodRepo billFoodRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillStatusRepo billStatusRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PromotionRepo promotionRepo;
    private static String generateTradingCode(int userid){
        char[] digits = "0123456789".toCharArray();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("BBT_").append(userid);
        for (int i = 0; i < 6; i++) {
            char digit = digits[random.nextInt(digits.length)];
            sb.append(digit);
        }
        return sb.toString();
    }
    private static int billCount = 0;
    public static String generateBillName() {
        billCount++;
        String formattedCount = String.format("#%06d", billCount);
        return "B" + formattedCount;
    }
    @Override
    public MessageResponse createBill(Bill bill) {
        Optional<User> user= userRepo.findById(bill.getUser().getId());
        if(user.isEmpty()){
            return MessageResponse.builder().message("Customer does not exist!!!").build();
        }else {
            Optional<Promotion> promotion= promotionRepo.findById(bill.getPromotion().getId());
            if(promotion.isEmpty()){
                return MessageResponse.builder().message("Promotion invalid!!!").build();
            }else {
                bill.setCreateTime(new Date(System.currentTimeMillis()));
                bill.setUpdateTime(new Date(System.currentTimeMillis()));
                bill.setIsActive(true);
                bill.setUser(user.get());
                bill.setBillStatus(billStatusRepo.findByName("Chờ thanh toán").get());
                bill.setTradingCode(generateTradingCode(user.get().getId()));
                bill.setName(generateBillName());
                bill.setPromotion(promotion.get());
                billRepo.save(bill);
                Optional<Bill> billCur= billRepo.findByName(bill.getName());
                if(billCur.isPresent()){
                    if(!bill.getBillTickets().isEmpty()){
                        boolean checkbillticket=true;
                        boolean checkbillfood=true;
                        for(BillTicket billTicket: bill.getBillTickets()){
                            Optional<Ticket> ticket = ticketRepo.findById(billTicket.getTicket().getId());
                            if(ticket.isPresent() && ticket.get().isIsActive()){
                                billTicket.setBill(billCur.get());
                                billTicket.setTicket(ticket.get());
                                billTicketRepo.save(billTicket);
                            }else {
                                checkbillticket=false;
                                break;
                            }
                        }if(!bill.getBillFoods().isEmpty()){
                            for(BillFood billFood: bill.getBillFoods()){
                                Optional<Food> food= foodRepo.findByNameOfFood(billFood.getFood().getNameOfFood());
                                if(food.isPresent()&& food.get().isIsActive()){
                                    billFood.setBill(billCur.get());
                                    billFood.setFood(food.get());
                                    billFoodRepo.save(billFood);
                                }else {
                                    checkbillfood=false;
                                    break;
                                }
                            }
                        }
                        if(checkbillticket && checkbillfood){
                            if(promotion.get().getPercent()!=0){
                                billCur.get().setTotalMoney(totalMoney(billCur)*(promotion.get().getPercent()/100.0));
                                billRepo.save(billCur.get());
                            }else {
                                billCur.get().setTotalMoney(totalMoney(billCur));
                                billRepo.save(billCur.get());
                            }
                        }
                    }else {
                        billRepo.delete(billCur.get());
                        return MessageResponse.builder().message("Billfood or billticket has some problems!!!").build();
                    }
                    return MessageResponse.builder().message("Creat bill successfully!!!").build();
                }else {
                    return MessageResponse.builder().message("Something went wrong!!!").build();
                }
            }
        }
    }
    @Override
    public double totalMoney(Optional<Bill> billCur) {
        double totalmoney=0;
        for(BillFood billFood: billFoodRepo.findAll()){
            if(billFood.getBill().getId()==billCur.get().getId()){
                Optional<Food> food= foodRepo.findById(billFood.getFood().getId());
                if(food.isPresent()){
                    totalmoney+=billFood.getQuantity()*food.get().getPrice();
                }
            }
        }
        for(BillTicket billTicket: billTicketRepo.findAll()){
            if(billTicket.getBill().getId()== billCur.get().getId()){
                Optional<Ticket> ticket= ticketRepo.findById(billTicket.getTicket().getId());
                if(ticket.isPresent()){
                    totalmoney+=billTicket.getQuantity()*ticket.get().getPriceTicket();
                }
            }
        }
        return totalmoney;
    }
    @Override
    public MessageResponse updateBill(int id, Bill bill) {
        return null;
    }
    @Override
    public MessageResponse deleteBill(int id) {
        Optional<Bill> bill= billRepo.findById(id);
        if(bill.isEmpty()){
            return MessageResponse.builder().message("Bill does not exist!!!").build();
        }else {
            for(BillFood billFood: billFoodRepo.findAll()){
                if(billFood.getBill().getId()==id){
                    billFoodRepo.delete(billFood);
                }
            }
            for(BillTicket billTicket: billTicketRepo.findAll()){
                if(billTicket.getBill().getId()==id){
                    billTicketRepo.delete(billTicket);
                }
            }
            bill.get().setIsActive(false);
            billRepo.save(bill.get());
            return MessageResponse.builder().message("Delete bill successfully!!!").build();
        }
    }
    @Override
    public BillCustom viewBill(int id) {
        Optional<Bill> bill = billRepo.findById(id);
        if(bill.isEmpty()){
            return null;
        }else {
            Optional<User> user= userRepo.findById(bill.get().getUser().getId());
            return user.map(value -> new BillCustom(bill.get().getName(), bill.get().getTotalMoney()
                    , bill.get().getTradingCode(), bill.get().getCreateTime(), value.getName())).orElse(null);
        }
    }
}
