package io.hasan.comicinfoservice.models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// makes the class a database entity, so Hibernate knows to
// create a table for this class and store objects of it in the database
@Entity
public class Comic {

    // attributes
    @Id // comicsId is primary key for table
    @GeneratedValue // generate this ID automatically when a new entity is saved (no need for us to generate UUID now)
    private UUID comicId;
    private String comicTitle;
    private String comicIssue;
    private String comicStartYear;
    private String comicDesc;

    // constructor
    public Comic() {}
    public Comic(String comicTitle, String comicIssue, String comicStartYear, String comicDesc) {
        this.comicTitle = comicTitle;
        this.comicIssue = comicIssue;
        this.comicStartYear = comicStartYear;
        this.comicDesc = comicDesc;
    }

    // getters and setters
    public UUID getComicId() {return comicId;}
    public void setComicId(UUID id) {this.comicId = id;}
    public String getComicTitle() {return comicTitle;}
    public void setComicTitle(String comicTitle) {this.comicTitle = comicTitle;}
    public String getComicIssue() {return comicIssue;}
    public void setComicIssue(String comicIssue) {this.comicIssue = comicIssue;}
    public String getComicStartYear() {return comicStartYear;}
    public void setComicStartYear(String comicStartYear) {this.comicStartYear = comicStartYear;}
    public String getComicDesc() {return comicDesc;}
    public void setComicDesc(String comicDesc) {this.comicDesc = comicDesc;}
}
