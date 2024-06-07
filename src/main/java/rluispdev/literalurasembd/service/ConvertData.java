package rluispdev.literalurasembd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collections;
import java.util.List;

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










//  @Override
//    public <T> List<T> getList(String json, Class<T> tClass) {
//        CollectionType list = mapper.getTypeFactory()
//                .constructCollectionType(List.class, tClass);
//        try {
//            return mapper.readValue(json,list);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }