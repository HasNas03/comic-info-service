package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ComicService {

    // 1. create temporary comic database
    // future variable for Repository bean that will handle db CRUD "private final ComicRepository comicRepository;"
    private final Map<UUID, Comic> comicsDB = new LinkedHashMap<>();

    // 2. constructor
    public ComicService(){}

    // 3. Declare service methods

    // get list of all comics
    public List<Comic> getAllComics() {
        return new ArrayList<>(comicsDB.values());}

    // get a specific comic by id
    public Comic getComic(UUID id) {
        Comic comic = comicsDB.get(id);
        if (comic == null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic not found");}
        return comic;}

    // add a new comic
    public Comic addComic(Comic comic) {
        UUID id = UUID.randomUUID();
        comic.setId(id);
        comicsDB.put(id, comic);
        return comic;
    }

    // edit an existing comic
    public Comic updateComic(UUID id, Comic comicUpdate) {
        Comic comic = getComic(id);
        comic.setComicTitle(comicUpdate.getComicTitle());
        comic.setComicStartYear(comicUpdate.getComicStartYear());
        comic.setComicDesc(comicUpdate.getComicDesc());
        return comic;
    }

    // delete an existing comic
    public void deleteComic(UUID id) {
        getComic(id);
        comicsDB.remove(id);
    }
//    // temp data of comics
//    List<Comic> comics = Arrays.asList(
//            new Comic("1", "Civil War", "Superheroes divide into two factions over government registration"),
//            new Comic("2", "Annihilation", "A cosmic threat led by Annihilus from the Negative Zone devastates the Marvel Universe"),
//            new Comic("3", "Secret Invasion", "The shapeshifting Skrulls infiltrate Earth"),
//            new Comic("4", "Dark Reign", "Green Goblin takes control of national security"),
//            new Comic("5", "Siege", "Osborn's regime falls when he attacks Asgard"),
//            new Comic("6", "AVX", "The Avengers and X-Men clash over what to do with the returning Phoenix Force"),
//            new Comic("7", "Secret Wars", "The multiverse collapses and Doctor Doom creates Battleworld from its remnants")
//    );

}
