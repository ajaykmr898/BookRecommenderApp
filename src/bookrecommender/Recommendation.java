package bookrecommender;

/**
 * Classe che rappresenta un consiglio di un libro da parte degli utenti.
 */
public class Recommendation {
    private String bookTitle;
    private int userCount;  // Numero di utenti che hanno consigliato il libro

    // Costruttore
    public Recommendation(String bookTitle, int userCount) {
        this.bookTitle = bookTitle;
        this.userCount = userCount;
    }

    // Getters e setters
    public String getBookTitle() {
        return bookTitle;
    }

    public int getUserCount() {
        return userCount;
    }
}
