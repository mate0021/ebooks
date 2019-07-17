package rnd.mate00.ebooks.commands;

public class ReaderCommand {

    private int id;

    private String name;

    private String password;

    private String repeatedPassword;

    private String email;

    public ReaderCommand() {
    }

    public ReaderCommand(int id, String name, String password, String repeatedPassword, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ReaderCommand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

