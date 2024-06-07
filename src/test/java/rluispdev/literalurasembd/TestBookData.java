package rluispdev.literalurasembd;

import com.fasterxml.jackson.databind.ObjectMapper;
import rluispdev.literalurasembd.model.BookData;

public class TestBookData {
    public static void main(String[] args) {
        String json = """
        {
            "title": "Example Book",
            "languages": "English",
            "download_count": 1234
        }
        """;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BookData bookData = objectMapper.readValue(json, BookData.class);
            System.out.println("bookName: " + bookData.bookName());
            System.out.println("language: " + bookData.language());
            System.out.println("download: " + bookData.download());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

