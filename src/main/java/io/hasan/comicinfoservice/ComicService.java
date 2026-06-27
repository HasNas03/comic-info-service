package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ComicService {

    // 1. create Repository bean that will handle db CRUD "private final ComicRepository comicRepository;"
    private final ComicRepository comicRepository;
    // call comciImageService so delete operation has reference
    private final ComicImageService comicImageService;

    // 2. constructor
    public ComicService(ComicRepository comicRepository, ComicImageService comicImageService) {
        this.comicRepository = comicRepository;
        this.comicImageService = comicImageService;
    }

    // 3. Declare service methods

    // get list of all comics
    public List<Comic> getAllComics() {
        return comicRepository.findAll();}

    // get a specific comic by id
    public Comic getComic(UUID id) {
        return comicRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic not found"));}

    // add a new comic
    public Comic addComic(Comic comic) {
        validateComic(comic);
        // set any previous id to null
        comic.setComicId(null);
        // save comic and Hibernate makes id
        return comicRepository.save(comic);
    }

    // edit an existing comic
    public Comic updateComic(UUID id, Comic comicUpdate) {
        validateComic(comicUpdate);
        Comic comic = getComic(id);
        comic.setComicTitle(comicUpdate.getComicTitle());
        comic.setComicIssue(comicUpdate.getComicIssue());
        comic.setComicStartYear(comicUpdate.getComicStartYear());
        comic.setComicDesc(comicUpdate.getComicDesc());
        return comicRepository.save(comic);
    }

    // delete an existing comic
    public void deleteComic(UUID id) {
        comicImageService.deleteImage(id);
        comicRepository.delete(getComic(id));
    }

    // validation helper
    private void validateComic(Comic comic) {
        if (comic.getComicTitle() == null || comic.getComicTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
    }
}
