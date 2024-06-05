package rluispdev.literalurasembd.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(@JsonAlias("title") String bookName,
                       @JsonAlias("authors") String author,
                       @JsonAlias("languages") String language,
                       @JsonAlias("download_count") Integer download){

}


