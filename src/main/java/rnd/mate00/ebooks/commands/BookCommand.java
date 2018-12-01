package rnd.mate00.ebooks.commands;

import rnd.mate00.ebooks.model.Theme;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Created by mate00 on 17.03.18.
 */
public class BookCommand {

    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @Min(1)
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
