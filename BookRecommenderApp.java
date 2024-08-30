package bookrecommender;

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
        this.bookRepository = CsvManager.loadBooks("books.csv");  // Carica i libri dal file CSV
        this.users = CsvManager.loadUsers("users.csv");            // Carica gli utenti dal file CSV
        this.scanner = new Scanner(System.in);
    }

    /**
     * Avvia l'applicazione.
     */
    public void start() {
        while (true) {
            System.out.println("Benvenuto nel Book Recommender!");
            System.out.println("1. Consultare il repository dei libri");
            System.out.println("2. Registrarsi");
            System.out.println("3. Login");
            System.out.println("4. Uscire");
            System.out.print("Seleziona un'opzione: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    login();
                    break;
                case 4:
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
    private void searchBook() {
        System.out.println("Cerca un libro:");
        System.out.println("1. Per titolo");
        System.out.println("2. Per autore");
        System.out.println("3. Per autore e anno");
        System.out.print("Seleziona un'opzione: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1:
                System.out.print("Inserisci il titolo da cercare: ");
                String title = scanner.nextLine();
                for (Book book : bookRepository) {
                    if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                        System.out.println(book);
                    }
                }
                break;
            case 2:
                System.out.print("Inserisci l'autore da cercare: ");
                String author = scanner.nextLine();
                for (Book book : bookRepository) {
                    if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                        System.out.println(book);
                    }
                }
                break;
            case 3:
                System.out.print("Inserisci l'autore: ");
                String authorSearch = scanner.nextLine();
                System.out.print("Inserisci l'anno: ");
                int yearSearch = Integer.parseInt(scanner.nextLine());
                for (Book book : bookRepository) {
                    if (book.getAuthor().toLowerCase().contains(authorSearch.toLowerCase()) && book.getPublicationYear() == yearSearch) {
                        System.out.println(book);
                    }
                }
                break;
            default:
                System.out.println("Opzione non valida.");
        }
    }
}
