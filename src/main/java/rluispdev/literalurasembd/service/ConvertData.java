package rluispdev.literalurasembd.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rluispdev.literalurasembd.model.BookData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConvertData implements IConvertData{
    private ObjectMapper mapper = new ObjectMapper();
    private List<BookData> results;

    @Override
    public <T> T getData(String json, Class<T> tClass) {
        try{
            return mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao analizar o JSON: " + e.getMessage());
        }
        return null;
    }

    public List<BookData> getResults() {
        return results;
    }
}
