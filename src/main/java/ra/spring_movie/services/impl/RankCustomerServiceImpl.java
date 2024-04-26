package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.RankCustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class RankCustomerServiceImpl implements RankCustomerService {
    @Autowired private RankCustomerRepo rankCustomerRepo;
    @Autowired private PromotionRepo promotionRepo;
    @Autowired private UserRepo userRepo;
    @Autowired private RefreshTokenRepo refreshTokenRepo;
    @Autowired private ConfirmEmailRepo confirmEmailRepo;
    @Autowired private BillRepo billRepo;
    @Autowired private BillFoodRepo billFoodRepo;
    @Autowired private BillTicketRepo billTicketRepo;
    @Override
    public MessageResponse insertRank(RankCustomer rankCustomer) {
        rankCustomer.setIsActive(true);
        rankCustomerRepo.save(rankCustomer);
        return MessageResponse.builder().message("Insert Successfully!!!").build();
    }

    @Override
    public MessageResponse updateRank(int id, RankCustomer rankCustomer) {
        Optional<RankCustomer> rankCustomerCur= rankCustomerRepo.findById(id);
        if(rankCustomerCur.isEmpty()){
            return MessageResponse.builder().message("Rank does not exist!!!").build();
        }else {
            rankCustomerCur.get().setName(rankCustomer.getName());
            rankCustomerCur.get().setDescription(rankCustomer.getDescription());
            rankCustomerCur.get().setPoint(rankCustomer.getPoint());
            rankCustomerRepo.save(rankCustomerCur.get());
            return MessageResponse.builder().message("Update Successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteRank(int id) {
        Optional<RankCustomer> rankCustomerCur= rankCustomerRepo.findById(id);
        if(rankCustomerCur.isEmpty()){
            return MessageResponse.builder().message("Rank does not exist!!!").build();
        }else {
            for(User user : userRepo.findAll()){
                if(user.getRankCustomer().getId()==id && user.isIsActive()){
                    for(RefreshToken refreshToken: refreshTokenRepo.findAll()){
                        if(refreshToken.getUser().getId()==user.getId()){
                            refreshTokenRepo.delete(refreshToken);
                        }
                    }
                    for(ConfirmEmail confirmEmail: confirmEmailRepo.findAll()){
                        if(confirmEmail.getUser().getId()== user.getId()){
                            confirmEmailRepo.delete(confirmEmail);
                        }
                    }
                    for(Bill bill: billRepo.findAll()){
                        if(bill.getUser().getId()== user.getId() && bill.isIsActive()){
                            for(BillFood billFood: billFoodRepo.findAll()){
                                if(billFood.getBill().getId()==bill.getId()){
                                    billFoodRepo.delete(billFood);
                                }
                            }
                            for(BillTicket billTicket: billTicketRepo.findAll()){
                                if(billTicket.getBill().getId()==bill.getId()){
                                    billTicketRepo.delete(billTicket);
                                }
                            }
                            bill.setIsActive(false);
                            billRepo.save(bill);
                        }
                    }
                    user.setIsActive(false);
                    userRepo.save(user);
                }
            }
            for(Promotion promotion: promotionRepo.findAll()){
                if(promotion.getRankCustomer().getId()==id && promotion.isIsActive()){
                    List<Bill> findAll = billRepo.findAll();
                    for (int i = 0; i < findAll.size(); i++) {
                        Bill bill = findAll.get(i);
                        if (bill.getUser().getId() == promotion.getId() && bill.isIsActive()) {
                            for (BillFood billFood : billFoodRepo.findAll()) {
                                if (billFood.getBill().getId() == bill.getId()) {
                                    billFoodRepo.delete(billFood);
                                }
                            }
                            for (BillTicket billTicket : billTicketRepo.findAll()) {
                                if (billTicket.getBill().getId() == bill.getId()) {
                                    billTicketRepo.delete(billTicket);
                                }
                            }
                            bill.setIsActive(false);
                            billRepo.save(bill);
                        }
                    }
                    promotion.setIsActive(false);
                    promotionRepo.save(promotion);
                }
            }
            rankCustomerCur.get().setIsActive(false);
            rankCustomerRepo.save(rankCustomerCur.get());
            return MessageResponse.builder().message("Delete Successfully!!!").build();
        }

    }
    @Override
    public List<RankCustomer> viewRank() {
        return rankCustomerRepo.findAll();
    }
}
