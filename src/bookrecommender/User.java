package bookrecommender;

import java.util.List;

/**
 * Classe che rappresenta un utente registrato.
 */
public class User {
    private String username;
    private String password;
    private List<Library> libraries;

    // Costruttore
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters e setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public void addLibrary(Library library) {
        this.libraries.add(library);
    }
}
