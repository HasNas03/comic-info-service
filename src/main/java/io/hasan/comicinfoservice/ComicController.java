package io.hasan.comicinfoservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comics")
public class ComicController {

    // 1. create variable for Service bean that will handle logic
    private final ComicService comicService;

    // 2. constructor
    public ComicController(ComicService comicService) {
        this.comicService = comicService;}

    // 3. write API of HTTP methods:

    @RequestMapping("/{comicId}")
    public Comic getComicInfo(@PathVariable("comicId") String comicId){
        return comicService.getComic(comicId);
    }
}
