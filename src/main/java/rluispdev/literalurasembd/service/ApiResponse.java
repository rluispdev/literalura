package rluispdev.literalurasembd.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import rluispdev.literalurasembd.model.BookData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private List<BookData> results;

    public List<BookData> getResults() {
        return results;
    }
    public void setResults(List<BookData> results) {
        this.results = results;
    }
}
