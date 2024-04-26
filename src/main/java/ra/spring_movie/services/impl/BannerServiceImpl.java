package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Banner;
import ra.spring_movie.repository.BannerRepo;
import ra.spring_movie.services.BannerService;

import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepo bannerRepo;
    @Override
    public MessageResponse insertBanner(Banner banner) {
        bannerRepo.save(banner);
        return MessageResponse.builder().message("Insert banner successfully!!!").build();
    }
    @Override
    public MessageResponse updateBanner(int id, Banner banner) {
        Optional<Banner> bannerCur= bannerRepo.findById(id);
        if(bannerCur.isEmpty()){
            return MessageResponse.builder().message("Banner does not exist!!!").build();
        }else {
            bannerCur.get().setTitle(banner.getTitle());
            bannerCur.get().setImageUrl(banner.getImageUrl());
            bannerRepo.save(bannerCur.get());
            return MessageResponse.builder().message("Update banner successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteBanner(int id) {
        Optional<Banner> bannerCur= bannerRepo.findById(id);
        if(bannerCur.isEmpty()){
            return MessageResponse.builder().message("Banner does not exist!!!").build();
        }else {
            bannerRepo.deleteById(id);
            return MessageResponse.builder().message("Delete banner successfully!!!").build();
        }
    }

    @Override
    public List<Banner> viewListBanner() {
        return bannerRepo.findAll();
    }
}
