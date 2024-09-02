package bookrecommender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe che gestisce l'applicazione di Book Recommender.
 */
public class BookRecommenderApp {
    private List<Book> bookRepository;
    private List<User> users;
    private User loggedInUser;
    private Scanner scanner;

    public BookRecommenderApp() {
        this.bookRepository = CsvManager.loadBooks("BooksDatasetClean.csv");  // Carica i libri dal file CSV
        this.users = CsvManager.loadUsers("users.csv");            // Carica gli utenti dal file CSV
        this.scanner = new Scanner(System.in);
    }

    /**
     * Avvia l'applicazione.
     */
    public void start() {
        while (true) {
            System.out.println("Benvenuto nel Book Recommender!");
            System.out.println("1. Cerca un libro per titolo/autore/anno");
            System.out.println("2. Visualizza informazioni libro");
            System.out.println("3. Registrarsi");
            System.out.println("4. Login");
            System.out.println("5. Uscire");
            System.out.print("Seleziona un'opzione: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    cercaLibro();
                    break;
                case 2:
                    visualizzaLibro();
                    break;
                case 3:
                    register();
                    break;
                case 4:
                    login();
                    break;
                case 5:
                    saveData();  // Salva i dati prima di uscire
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    /**
     * Registra un nuovo utente.
     */
    private void register() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        users.add(user);
        CsvManager.saveUsers(users, "users.csv");  // Salva i nuovi utenti nel file CSV

        System.out.println("Registrazione avvenuta con successo!");
    }

    /**
     * Effettua il login dell'utente.
     */
    private void login() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login effettuato con successo!");
                userMenu();
                return;
            }
        }
        System.out.println("Username o password errati. Riprova.");
    }

    /**
     * Menu delle funzionalità per l'utente loggato.
     */
    private void userMenu() {
        while (true) {
            System.out.println("1. Creare una nuova libreria");
            System.out.println("2. Aggiungere un libro alla libreria");
            System.out.println("3. Inserire valutazione di un libro");
            System.out.println("4. Inserire consigli su un libro");
            System.out.println("5. Logout");
            System.out.print("Seleziona un'opzione: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    createLibrary();
                    break;
                case 2:
                    addBookToLibrary();
                    break;
                case 3:
                    addRating();
                    break;
                case 4:
                    addRecommendation();
                    break;
                case 5:
                    saveData();  // Salva i dati prima di fare logout
                    loggedInUser = null;
                    System.out.println("Logout effettuato con successo.");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    /**
     * Crea una nuova libreria personale.
     */
    private void createLibrary() {
        System.out.print("Inserisci il nome della nuova libreria: ");
        String libraryName = scanner.nextLine();
        Library library = new Library(libraryName);
        loggedInUser.addLibrary(library);
        System.out.println("Libreria creata con successo!");
    }

    /**
     * Aggiunge un libro alla libreria personale dell'utente.
     */
    private void addBookToLibrary() {
        System.out.print("Inserisci il titolo del libro da aggiungere: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);

        if (book != null) {
            System.out.print("Inserisci il nome della libreria: ");
            String libraryName = scanner.nextLine();

            for (Library library : loggedInUser.getLibraries()) {
                if (library.getName().equalsIgnoreCase(libraryName)) {
                    library.addBook(book);
                    System.out.println("Libro aggiunto con successo!");
                    return;
                }
            }
            System.out.println("Libreria non trovata.");
        } else {
            System.out.println("Libro non trovato nel repository.");
        }
    }

    /**
     * Aggiunge una valutazione a un libro nella libreria dell'utente.
     */
    private void addRating() {
        System.out.print("Inserisci il titolo del libro da valutare: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);

        if (book != null) {
            System.out.print("Valuta lo stile (0-5): ");
            double styleRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Valuta il contenuto (0-5): ");
            double contentRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Valuta la gradevolezza (0-5): ");
            double enjoymentRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Valuta l'originalità (0-5): ");
            double originalityRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Valuta l'edizione (0-5): ");
            double editionRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Valutazione complessiva (0-5): ");
            double overallRating = Double.parseDouble(scanner.nextLine());
            System.out.print("Nota aggiuntiva (facoltativa): ");
            String note = scanner.nextLine();

            Rating rating = new Rating(loggedInUser.getUsername(), styleRating, contentRating, enjoymentRating,
                    originalityRating, editionRating, overallRating, note);
            book.getRatings().add(rating);

            System.out.println("Valutazione aggiunta con successo!");
        } else {
            System.out.println("Libro non trovato nel repository.");
        }
    }

    /**
     * Aggiunge un consiglio per un libro.
     */
    private void addRecommendation() {
        System.out.print("Inserisci il titolo del libro per cui vuoi aggiungere un consiglio: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);

        if (book != null) {
            System.out.print("Inserisci il titolo del libro consigliato: ");
            String recommendedTitle = scanner.nextLine();
            Recommendation recommendation = new Recommendation(recommendedTitle, 1);
            book.getRecommendations().add(recommendation);

            System.out.println("Consiglio aggiunto con successo!");
        } else {
            System.out.println("Libro non trovato nel repository.");
        }
    }

    /**
     * Cerca un libro per titolo.
     * 
     * @param title Titolo del libro.
     * @return L'oggetto Book se trovato, altrimenti null.
     */
    private Book findBookByTitle(String title) {
        for (Book book : bookRepository) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Salva i dati dei libri e degli utenti sui rispettivi file CSV.
     */
    private void saveData() {
        CsvManager.saveBooks(bookRepository, "books.csv");
        CsvManager.saveUsers(users, "users.csv");
    }

    /**
     * Implementazione del metodo di ricerca dei libri.
     */
    public void cercaLibro() {
        System.out.println("Seleziona il tipo di ricerca:");
        System.out.println("1: Ricerca per titolo");
        System.out.println("2: Ricerca per autore");
        System.out.println("3: Ricerca per autore e anno");

        int scelta = Integer.parseInt(scanner.nextLine());
        List<Book> risultati = new ArrayList<>();

        switch (scelta) {
            case 1:
                // Ricerca per titolo
                System.out.print("Inserisci il titolo del libro: ");
                String titoloRicerca = scanner.nextLine();
                for (Book book : bookRepository) {
                    if (book.getTitle().toLowerCase().contains(titoloRicerca.toLowerCase())) {
                        risultati.add(book);
                    }
                }
                break;

            case 2:
                // Ricerca per autore
                System.out.print("Inserisci il nome dell'autore: ");
                String autoreRicerca = scanner.nextLine();
                for (Book book : bookRepository) {
                    if (book.getAuthors().toLowerCase().contains(autoreRicerca.toLowerCase())) {
                        risultati.add(book);
                    }
                }
                break;

            case 3:
                // Ricerca per autore e anno
                System.out.print("Inserisci il nome dell'autore: ");
                String autoreAnnoRicerca = scanner.nextLine();
                System.out.print("Inserisci l'anno di pubblicazione: ");
                int annoRicerca = Integer.parseInt(scanner.nextLine());
                for (Book book : bookRepository) {
                    if (book.getAuthors().toLowerCase().contains(autoreAnnoRicerca.toLowerCase()) 
                        && book.getPublicationYear() == annoRicerca) {
                        risultati.add(book);
                    }
                }
                break;

            default:
                System.out.println("Opzione non valida.");
                return;
        }

        // Stampa i risultati della ricerca
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro trovato.");
        } else {
            System.out.println("Libri trovati:");
            for (int i = 0; i < risultati.size(); i++) {
                System.out.println((i + 1) + ": " + risultati.get(i)); // Assuming Book class has a toString method
            }
        }
    }

    /** 
     * Method to view a book's details
     */
    public void visualizzaLibro() {
        System.out.print("Inserisci il numero del libro che desideri visualizzare: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1; // Convert to zero-based index

        if (index < 0 || index >= bookRepository.size()) {
            System.out.println("Numero di libro non valido.");
            return;
        }

        Book book = bookRepository.get(index); // Get the selected book
        System.out.println("Dettagli del libro:");
        System.out.println(book); // Print basic book details

        // Print ratings if available
        List<Rating> ratings = book.getRatings(); // Assuming getRatings() returns a list of ratings
        if (ratings.isEmpty()) {
            System.out.println("Non ci sono valutazioni per questo libro.");
        } else {
            System.out.println("Valutazioni:");
            // Aggregate rating information
            double totalScore = 0;
            int count = ratings.size();
            for (Rating rating : ratings) {
                totalScore += rating.getOverallScore(); // Assuming Rating class has a method getOverallScore()
            }
            double averageScore = totalScore / count;
            System.out.printf("Media delle valutazioni: %.2f da %d utenti.%n", averageScore, count);
        }

        // Print recommendations if available
        List<Book> recommendations = book.getRecommendations(); // Assuming getRecommendations() returns a list of recommended books
        if (recommendations.isEmpty()) {
            System.out.println("Non ci sono consigli per questo libro.");
        } else {
            System.out.println("Libri consigliati:");
            for (Book recommendedBook : recommendations) {
                System.out.println(recommendedBook); // Print recommended book details
            }
        }
    }


    /**
     *  Metodo per cercare libri per titolo
     */ 
    public List<Book> cercaLibroTitolo(String titolo) {
        List<Book> risultati = new ArrayList<>();
        for (Book book : bookRepository) {
            if (book.getTitle().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(book);
            }
        }
        return risultati;
    }

    /**
     *  Metodo per cercare libri per autore
     */
    public List<Book> cercaLibroAutore(String autore) {
        List<Book> risultati = new ArrayList<>();
        for (Book book : bookRepository) {
            if (book.getAuthors().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(book);
            }
        }
        return risultati;
    }

    /**
     *  Metodo per cercare libri per autore e anno
     */ 
    public List<Book> cercaLibroAutoreAnno(String autore, String anno) {
        List<Book> risultati = new ArrayList<>();
        for (Book book : bookRepository) {
            if (book.getAuthors().toLowerCase().contains(autore.toLowerCase()) &&
                String.valueOf(book.getPublishYear()).equals(anno)) {
                risultati.add(book);
            }
        }
        return risultati;
    }


    /** 
     * Metodo per inserire un nuovo libro
     */
    public boolean inserisciLibro(Book libro) {
        try {
            CsvManager.appendBookToCsv("Libri.dati.csv", libro);
            bookRepository.add(libro);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Stampa nel catch in caso di errore
            return false;
        }
    }

    /**
     *  Metodo per modificare un dato specifico di un libro
    */
    public Book modificaDato(Book libro, int i, String dato) {
        switch (i) {
            case 0:
                libro.setTitle(dato);
                break;
            case 1:
                libro.setAuthors(dato);
                break;
            case 2:
                libro.setPublishYear(Integer.parseInt(dato));
                break;
            case 3:
                libro.setPublisher(dato);
                break;
            default:
                return null;
        }
        CsvManager.updateBookInCsv("Libri.dati.csv", libro);
        return libro;
    }

    /**
    * Metodo per modificare le categorie di un libro
    */
    public Book modificaGenere(Book libro, List<String> categorie) {
        String categorieString = String.join(", ", categorie);
        libro.setCategory(categorieString);
        CsvManager.updateBookInCsv("Libri.dati.csv", libro);
        return libro;
    }

    /**
     * Inserisce un nuovo libro nel repository.
     */
    public boolean eliminaLibro(Book libro) {
        boolean removed = bookRepository.remove(libro);
        if (removed) {
            CsvManager.removeBookFromCsv("Libri.dati.csv", libro);
            return true;
        } else {
            return false;
        }
    }

}
