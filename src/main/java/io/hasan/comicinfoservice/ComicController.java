package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comics")
public class ComicController {

    // 1. create variable for Service bean that will handle logic
    private final ComicService comicService;

    // 2. constructor
    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    // 3. write API for HTTP methods:

    // get list of all comics
    @GetMapping
    public List<Comic> getAllComics() {
        return comicService.getAllComics();}

    // get a specific comic by id
    @GetMapping("/{id}")
    public Comic getComic(@PathVariable UUID id) {
        return comicService.getComic(id);}

    // add a new comic
    @PostMapping
    public Comic addComic(@RequestBody Comic comic) {
        return comicService.addComic(comic);}

    // edit an existing comic
    @PutMapping("/{id}")
    public Comic updateComic(@PathVariable UUID id, @RequestBody Comic comic) {
        return comicService.updateComic(id, comic);}

    // delete an existing comic
    @DeleteMapping("/{id}")
    public void deleteComic(@PathVariable UUID id) {
        comicService.deleteComic(id);}

}



