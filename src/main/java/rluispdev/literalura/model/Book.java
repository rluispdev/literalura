package rluispdev.literalura.model;

import java.util.List;

public class Book {

    private  String bookName;
    private  String language;
    private  Integer download;
    private List<Author> authors;

    public Book(BookData bookData){
        this.bookName = bookData.bookName();
        this.language = bookData.languages() != null && !bookData.languages().isEmpty() ? bookData.languages().get(0) : "N/A";
        this.download = bookData.download();
        this.authors = bookData.author();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = Integer.valueOf(download);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
       return
             "Nome do livro: " + bookName + "Autor(a): " + authors + ", Linguagem: " + language + ", Quantidade de downloads: " + download;
    }
}
