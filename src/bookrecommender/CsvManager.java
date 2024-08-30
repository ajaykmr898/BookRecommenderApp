package bookrecommender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione dei file CSV (lettura e scrittura).
 */
public class CsvManager {

    /**
     * Metodo per caricare i libri da un file CSV.
     * 
     * @param filePath Percorso del file CSV.
     * @return Lista di libri caricati dal file.
     */
    public static List<Book> loadBooks(String filePath) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
              while ((line = br.readLine()) != null) {
                String[] values = line.split("\t"); // Tab-separated values
                String title = values[0];
                String authors = values[1];
                String description = values[2];
                String category = values[3];
                String publisher = values[4];
                double price = Double.parseDouble(values[5]);
                String publishMonth = values[6];
                int publishYear = Integer.parseInt(values[7]);

                Book book = new Book(title, authors, description, category, publisher, price, publishMonth, publishYear);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Metodo per caricare gli utenti registrati da un file CSV.
     * 
     * @param filePath Percorso del file CSV.
     * @return Lista di utenti registrati caricati dal file.
     */
    public static List<User> loadUsers(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                users.add(new User(username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Metodo per salvare i libri in un file CSV.
     * 
     * @param books Lista di libri da salvare.
     * @param filePath Percorso del file CSV.
     */
    public static void saveBooks(List<Book> books, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPublicationYear() +
                        (book.getPublisher() != null ? "," + book.getPublisher() : "") +
                        (book.getCategories() != null ? "," + String.join(";", book.getCategories()) : ""));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per salvare gli utenti registrati in un file CSV.
     * 
     * @param users Lista di utenti da salvare.
     * @param filePath Percorso del file CSV.
     */
    public static void saveUsers(List<User> users, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(user.getUsername() + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
