package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import io.hasan.comicinfoservice.models.Rating;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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
    @RequestMapping(method= RequestMethod.GET, value="/webClient/{userId}")
    public List<Rating> getWebClientResponse(@PathVariable("userId") String userId){
        List<Rating> webRating = webClient.get()
                                    .uri("http://localhost:8083/ratings/"+userId)
                                    .retrieve()
                                    .bodyToFlux(new ParameterizedTypeReference<Rating>() {})
                                    .collectList()
                                    // .bodyToMono() // for single items
                                    .block();
        return webRating;
    }


}



