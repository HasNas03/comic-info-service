package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ComicService {

    public static final String STATUS_LIBRARY = "LIBRARY";
    public static final String STATUS_WANTED = "WANTED";

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
        comic.setComicStatus(normalizeStatus(comic.getComicStatus()));
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
        if (comicUpdate.getComicStatus() != null) {
            comic.setComicStatus(normalizeStatus(comicUpdate.getComicStatus()));
        } else if (comic.getComicStatus() == null) {
            comic.setComicStatus(STATUS_LIBRARY);
        }
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
        normalizeStatus(comic.getComicStatus());
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return STATUS_LIBRARY;
        }

        String normalizedStatus = status.trim().toUpperCase();
        if (!STATUS_LIBRARY.equals(normalizedStatus) && !STATUS_WANTED.equals(normalizedStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comicStatus must be LIBRARY or WANTED");
        }
        return normalizedStatus;
    }
}
