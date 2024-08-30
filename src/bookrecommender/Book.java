package bookrecommender;

import java.util.List;

/**
 * Classe che rappresenta un libro nel repository.
 */
public class Book {
    private String title;
    private String author;
    private int publicationYear;
    private String publisher;
    private List<String> categories;
    private List<Rating> ratings;
    private List<Recommendation> recommendations;

    // Costruttore
    public Book(String title, String author, int publicationYear, String publisher, List<String> categories) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.categories = categories;
    }

    // Getters e setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "Titolo: " + title + "\n" +
                "Autore: " + author + "\n" +
                "Anno di Pubblicazione: " + publicationYear + "\n" +
                (publisher != null ? "Editore: " + publisher + "\n" : "") +
                (categories != null && !categories.isEmpty() ? "Categorie: " + String.join(", ", categories) + "\n" : "");
    }
}
