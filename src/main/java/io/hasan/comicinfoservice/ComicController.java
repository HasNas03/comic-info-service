package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/comics")
public class ComicController {

    // 1. create variable for Service bean that will handle logic
    private final ComicService comicService;
    // webclient test
    private WebClient webClient;

    // 2. constructor
    public ComicController(ComicService comicService, WebClient webClient) {
        this.comicService = comicService;
        // webclient test
        this.webClient = webClient;}

    // 3. write API of HTTP methods:

    @RequestMapping(method= RequestMethod.GET, value="/{comicId}")
    public Comic getComicInfo(@PathVariable("comicId") String comicId){
        return comicService.getComic(comicId);
    }

    // webclient test
    @RequestMapping(method= RequestMethod.GET, value="/webclienttest")
    public Rating getWebClientResponse(){
        Rating webRating = webClient.get()
                                    .uri("http://localhost:8083/ratings/bbanner")
                                    .retrieve()
                                    .bodyToMono(Rating.class)
                                    .block();
        return webRating;
    }


}



