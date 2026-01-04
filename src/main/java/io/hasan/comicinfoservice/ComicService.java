package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ComicService {

    // 1. create future variable for Repository bean that will handle db CRUD
    // private final ComicRepository comicRepository;

    // 2. constructor
    public ComicService(){}

    // temp data of comics
    List<Comic> comics = Arrays.asList(
            new Comic("1", "Civil War", "Superheroes divide into two factions over government registration"),
            new Comic("2", "Annihilation", "A cosmic threat led by Annihilus from the Negative Zone devastates the Marvel Universe"),
            new Comic("3", "Secret Invasion", "The shapeshifting Skrulls infiltrate Earth"),
            new Comic("4", "Dark Reign", "Green Goblin takes control of national security"),
            new Comic("5", "Siege", "Osborn's regime falls when he attacks Asgard"),
            new Comic("6", "AVX", "The Avengers and X-Men clash over what to do with the returning Phoenix Force"),
            new Comic("7", "Secret Wars", "The multiverse collapses and Doctor Doom creates Battleworld from its remnants")
    );

    public Comic getComic(String comicId){
        Comic comic = comics
                      .stream() // convert into stream
                      .filter(c -> c.getComicId().equals(comicId))
                      .findFirst().orElse(null);
        return comic;
    }
}
