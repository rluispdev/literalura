package rluispdev.literalurasembd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{
private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tClass) {
        try{
            return mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
         System.err.println("Erro ao analizar o JSON: " + e.getMessage());
        }
        return null;
    }
}

