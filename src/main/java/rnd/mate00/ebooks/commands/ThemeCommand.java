package rnd.mate00.ebooks.commands;

/**
 * Created by mate00 on 11.03.18.
 */
public class ThemeCommand {

    private int id;

    private String theme;

    public ThemeCommand() {
    }

    public ThemeCommand(int id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "ThemeCommand{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                '}';
    }
}
