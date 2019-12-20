package johnfatso.flashcard;

import java.sql.Date;

public class DictDefinition {
    String wort;
    String plural;
    String translation;
    long enteredDate;

    public DictDefinition(String wort, String plural, String translation, long enteredDate) {
        this.wort = wort;
        this.plural = plural;
        this.translation = translation;
        this.enteredDate = enteredDate;
    }
}
