package bookrecommender;

/**
 * Classe che rappresenta una valutazione di un libro.
 */
public class Rating {
    private String username;
    private double styleRating;
    private double contentRating;
    private double enjoymentRating;
    private double originalityRating;
    private double editionRating;
    private double overallRating;
    private String note;

    // Costruttore
    public Rating(String username, double styleRating, double contentRating, double enjoymentRating,
                  double originalityRating, double editionRating, double overallRating, String note) {
        this.username = username;
        this.styleRating = styleRating;
        this.contentRating = contentRating;
        this.enjoymentRating = enjoymentRating;
        this.originalityRating = originalityRating;
        this.editionRating = editionRating;
        this.overallRating = overallRating;
        this.note = note;
    }

    // Getters e setters
    public String getUsername() {
        return username;
    }

    public double getStyleRating() {
        return styleRating;
    }

    public double getContentRating() {
        return contentRating;
    }

    public double getEnjoymentRating() {
        return enjoymentRating;
    }

    public double getOriginalityRating() {
        return originalityRating;
    }

    public double getEditionRating() {
        return editionRating;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public String getNote() {
        return note;
    }
}
