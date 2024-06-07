package rluispdev.literalura.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}







//    <T> List<T> getList(String json, Class<T> tClass);