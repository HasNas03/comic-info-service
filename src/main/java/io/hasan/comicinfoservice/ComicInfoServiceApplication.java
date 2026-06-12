package io.hasan.comicinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ComicInfoServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ComicInfoServiceApplication.class, args);
    }
}
