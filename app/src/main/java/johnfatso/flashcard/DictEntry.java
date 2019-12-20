package johnfatso.flashcard;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "dict_table")
public class DictEntry {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "german_word")
    private String deutcheWort;

    @NonNull
    @ColumnInfo(name = "plural")
    private String plural;

    @NonNull
    @ColumnInfo(name = "english_translation")
    private String englischeTranslation;

    @NonNull
    @ColumnInfo(name = "entered_date")
    private long enteredDate;

    public DictEntry(@NonNull String deutcheWort, @NonNull String plural, @NonNull String englischeTranslation, @NonNull long enteredDate) {
        this.deutcheWort = deutcheWort;
        this.plural = plural;
        this.englischeTranslation = englischeTranslation;
        this.enteredDate = enteredDate;
    }

    public void setDeutcheWort(@NonNull String deutcheWort) {
        this.deutcheWort = deutcheWort;
    }

    public void setPlural(@NonNull String plural) {
        this.plural = plural;
    }

    public void setEnglischeTranslation(@NonNull String englischeTranslation) {
        this.englischeTranslation = englischeTranslation;
    }

    public void setEnteredDate(long enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getDeutcheWort() {
        return deutcheWort;
    }

    public String getPlural() {
        return plural;
    }

    public String getEnglischeTranslation() {
        return englischeTranslation;
    }

    public long getEnteredDate(){
        return enteredDate;
    }
}
