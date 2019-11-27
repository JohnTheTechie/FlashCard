package johnfatso.flashcard;

import java.sql.Date;

public class DictDefinition {
    int index;
    String wort;
    String type;
    String translation;
    long enteredDate;

    public DictDefinition(int index, String wort, String type, String translation, long enteredDate) {
        this.index = index;
        this.wort = wort;
        this.type = type;
        this.translation = translation;
        this.enteredDate = enteredDate;
    }
}
