package rluispdev.literalurasembd.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(@JsonAlias("title") String bookName,
                       @JsonAlias("authors")List<Author> author,
                       @JsonAlias("languages") List<String> languages,
                       @JsonAlias("download_count") Integer download){

};


