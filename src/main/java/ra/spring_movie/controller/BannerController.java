package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Banner;
import ra.spring_movie.services.impl.BannerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banner/")
public class BannerController {
    @Autowired
    private BannerServiceImpl bannerService;
    @PostMapping("insertbanner")
    public ResponseEntity<MessageResponse> insertBanner(@RequestBody Banner banner){
        return new ResponseEntity<>(bannerService.insertBanner(banner), HttpStatus.CREATED);
    }
    @PutMapping("updatebanner/{id}")
    public ResponseEntity<MessageResponse> updateBanner(@PathVariable int id, @RequestBody Banner banner){
        return new ResponseEntity<>(bannerService.updateBanner(id,banner), HttpStatus.OK);
    }
    @DeleteMapping("deletebanner/{id}")
    public ResponseEntity<MessageResponse> deleteBanner(@PathVariable int id){
        return new ResponseEntity<>(bannerService.deleteBanner(id), HttpStatus.OK);
    }
    @GetMapping("viewlistbanner")
    public List<Banner> viewBanner(){
        return bannerService.viewListBanner();
    }
}
