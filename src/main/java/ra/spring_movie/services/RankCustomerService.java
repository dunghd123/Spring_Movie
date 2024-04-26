package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.RankCustomer;


import java.util.List;

public interface RankCustomerService {
    MessageResponse insertRank(RankCustomer rankCustomer);
    MessageResponse updateRank(int id,RankCustomer rankCustomer);
    MessageResponse deleteRank(int id);
    List<RankCustomer> viewRank();
}
