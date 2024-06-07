package rluispdev.literalurasembd.service;

import rluispdev.literalurasembd.model.BookData;

import java.util.List;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}







//    <T> List<T> getList(String json, Class<T> tClass);