package rnd.mate00.ebooks.commands;

import rnd.mate00.ebooks.model.Theme;

/**
 * Created by mate00 on 17.03.18.
 */
public class BookCommand {

    private int id;

    private String title;

    private String author;

    private int locations;

    private Theme theme;

    private ThemeCommand themeCommand;

    public BookCommand() {
    }

    public BookCommand(int id, String title, String author, int locations, Theme theme) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.locations = locations;
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLocations() {
        return locations;
    }

    public void setLocations(int locations) {
        this.locations = locations;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ThemeCommand getThemeCommand() {
        return themeCommand;
    }

    public void setThemeCommand(ThemeCommand themeCommand) {
        this.themeCommand = themeCommand;
    }

    @Override
    public String toString() {
        return "BookCommand{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", locations=" + locations +
                ", theme=" + theme +
                '}';
    }
}
