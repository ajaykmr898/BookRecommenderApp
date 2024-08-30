package bookrecommender;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una libreria personale di un utente.
 */
public class Library {
    private String name;
    private List<Book> books;

    // Costruttore
    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    // Getters e setters
    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
