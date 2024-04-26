package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.PromotionService;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired private PromotionRepo promotionRepo;
    @Autowired private BillRepo billRepo;
    @Autowired private BillFoodRepo billFoodRepo;
    @Autowired private BillTicketRepo billTicketRepo;
    @Autowired private RankCustomerRepo rankCustomerRepo;

    private static String createName(int percent, String rankName){
        return "Discount_"+rankName+"_"+percent+"%";
    }
    @Override
    public MessageResponse insertPro(Promotion promotion) {
        Optional<RankCustomer> rankCustomer= rankCustomerRepo.findByName(promotion.getRankCustomer().getName());
        if(rankCustomer.isEmpty()){
            return MessageResponse.builder().message("RankCustomer invalid!!!").build();
        }else {
            if(promotion.getEndTime().before(promotion.getStartTime())){
                return MessageResponse.builder().message("EndTime must be after StartTime!!!").build();
            }else {
                promotion.setIsActive(true);
                promotion.setRankCustomer(rankCustomer.get());
                promotion.setName(createName(promotion.getPercent(), rankCustomer.get().getName()));
                promotionRepo.save(promotion);
                return MessageResponse.builder().message("Insert Successfully!!!").build();
            }
        }
    }
    @Override
    public MessageResponse updatePro(int id, Promotion promotion) {
        Optional<Promotion> promotionCur= promotionRepo.findById(id);
        if(promotionCur.isEmpty()){
            return MessageResponse.builder().message("Promotion does not exist!!!").build();
        }else {
            Optional<RankCustomer> rankCustomer= rankCustomerRepo.findByName(promotion.getRankCustomer().getName());
            if(rankCustomer.isEmpty()){
                return MessageResponse.builder().message("RankCustomer invalid!!!").build();
            }else {
                if(promotion.getEndTime().before(promotion.getStartTime())){
                    return MessageResponse.builder().message("EndTime must be after StartTime!!!").build();
                }else {
                    promotionCur.get().setPercent(promotion.getPercent());
                    promotionCur.get().setQuantity(promotion.getQuantity());
                    promotionCur.get().setType(promotion.getType());
                    promotionCur.get().setStartTime(promotion.getStartTime());
                    promotionCur.get().setEndTime(promotion.getEndTime());
                    promotionCur.get().setDescription(promotion.getDescription());
                    promotionCur.get().setName(createName(promotion.getPercent(),rankCustomer.get().getName()));
                    promotionCur.get().setRankCustomer(rankCustomer.get());
                    promotionRepo.save(promotionCur.get());
                    return MessageResponse.builder().message("Update Successfully!!!").build();
                }
            }
        }
    }
    @Override
    public MessageResponse deletePro(int id) {
        Optional<Promotion> promotionCur= promotionRepo.findById(id);
        if(promotionCur.isEmpty()){
            return MessageResponse.builder().message("Promotion does not exist!!!").build();
        }else{
            for(Bill bill : billRepo.findAll()){
                if(bill.getPromotion().getId()==id && bill.isIsActive()){
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
            promotionCur.get().setIsActive(false);
            promotionRepo.save(promotionCur.get());
            return MessageResponse.builder().message("Delete Successfully!!!").build();
        }
    }

    @Override
    public List<Promotion> viewPro() {
        return promotionRepo.findAll();
    }
}
