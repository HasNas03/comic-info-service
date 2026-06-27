package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/comics")
public class ComicController {

    // 1. create variable for Service bean that will handle logic
    private final ComicService comicService;
    private final ComicImageService comicImageService;

    // 2. constructor
    public ComicController(ComicService comicService, ComicImageService comicImageService) {
        this.comicService = comicService;
        this.comicImageService = comicImageService;
    }

    // 3. API for HTTP methods:

    // get list of all comics
    @GetMapping
    public List<Comic> getAllComics() {
        return comicService.getAllComics();}
    // get a specific comic by id
    @GetMapping("/{comicId}")
    public Comic getComic(@PathVariable UUID comicId) {
        return comicService.getComic(comicId);}
    // add a new comic
    @PostMapping
    public Comic addComic(@RequestBody Comic comic) {
        return comicService.addComic(comic);}
    // edit an existing comic
    @PutMapping("/{comicId}")
    public Comic updateComic(@PathVariable UUID comicId, @RequestBody Comic comic) {
        return comicService.updateComic(comicId, comic);}
    // delete an existing comic
    @DeleteMapping("/{comicId}")
    public void deleteComic(@PathVariable UUID comicId) {
        comicService.deleteComic(comicId);}

    // 4. API for HTTP methods for cover image:

    // add a cover image to a comic
    @PostMapping(value = "/{comicId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Comic addComicImage(@PathVariable UUID comicId, @RequestPart("image") MultipartFile image) {
        return comicImageService.saveImage(comicId, image);}
    // edit cover image of a comic
    @PutMapping(value = "/{comicId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Comic updateComicImage(@PathVariable UUID comicId, @RequestPart("image") MultipartFile image) {
        return comicImageService.saveImage(comicId, image);}

    // get the cover image of a comic
    @GetMapping(value = "/{comicId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getComicImage(@PathVariable UUID comicId) {
        return ResponseEntity
                .ok()
                // must specify via contentType that content being returned is not JSON but image
                .contentType(MediaType.IMAGE_JPEG)
                .body(comicImageService.getImage(comicId));}
    // delete the cover image of a comic
    @DeleteMapping("/{comicId}/image")
    public void deleteComicImage(@PathVariable UUID comicId) {
        comicImageService.deleteImage(comicId);}

    //notes:
    // consume/produce tell what format the request/response body is in, default is APPLICATION_JSON_VALUE
    // @RequestPart is just @RequestBody but for multipart form data (files)
    // MultipartFile is Spring's built-in type that represents an uploaded file
}



