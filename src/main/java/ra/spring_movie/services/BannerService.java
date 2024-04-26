package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Banner;

import java.util.List;

public interface BannerService {
    MessageResponse insertBanner(Banner banner);
    MessageResponse updateBanner(int id, Banner banner);
    MessageResponse deleteBanner(int id);
    List<Banner> viewListBanner();
}
