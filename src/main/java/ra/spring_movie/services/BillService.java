package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Bill;
import ra.spring_movie.model.BillCustom;

import java.util.Optional;

public interface BillService {
    MessageResponse createBill(Bill bill);
    double totalMoney(Optional<Bill> billCur);
    MessageResponse updateBill(int id, Bill bill);
    MessageResponse deleteBill(int id);
    BillCustom viewBill(int id);
}
