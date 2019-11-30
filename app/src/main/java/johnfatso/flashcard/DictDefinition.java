package johnfatso.flashcard;

import java.sql.Date;

public class DictDefinition {
    String wort;
    String type;
    String translation;
    long enteredDate;

    public DictDefinition(String wort, String type, String translation, long enteredDate) {
        this.wort = wort;
        this.type = type;
        this.translation = translation;
        this.enteredDate = enteredDate;
    }
}
