package io.hasan.comicinfoservice.models;
import java.util.UUID;

public class Comic {

    // attributes
    private UUID id;
    private String comicTitle;
    private String comicStartYear;
    private String comicDesc;

    // constructor
    public Comic(UUID id, String comicTitle, String comicStartYear, String comicDesc) {
        this.id = id;
        this.comicTitle = comicTitle;
        this.comicStartYear = comicStartYear;
        this.comicDesc = comicDesc;
    }

    // getters and setters
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public String getComicTitle() {return comicTitle;}
    public void setComicTitle(String comicTitle) {this.comicTitle = comicTitle;}
    public String getComicStartYear() {return comicStartYear;}
    public void setComicStartYear(String comicStartYear) {this.comicStartYear = comicStartYear;}
    public String getComicDesc() {return comicDesc;}
    public void setComicDesc(String comicDesc) {this.comicDesc = comicDesc;}
}
