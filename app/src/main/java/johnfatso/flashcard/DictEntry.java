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
    @ColumnInfo(name = "index")
    private int index;

    @NonNull
    @ColumnInfo(name = "german_word")
    private String deutcheWort;

    @NonNull
    @ColumnInfo(name = "german_word_type")
    private String deutcheWortType;

    @NonNull
    @ColumnInfo(name = "english_translation")
    private String englischeTranslation;

    @NonNull
    @ColumnInfo(name = "entered_date")
    private long enteredDate;

    public DictEntry(@NonNull int index, @NonNull String deutcheWort, @NonNull String deutcheWortType, @NonNull String englischeTranslation, @NonNull long enteredDate) {
        this.index = index;
        this.deutcheWort = deutcheWort;
        this.deutcheWortType = deutcheWortType;
        this.englischeTranslation = englischeTranslation;
        this.enteredDate = enteredDate;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDeutcheWort(@NonNull String deutcheWort) {
        this.deutcheWort = deutcheWort;
    }

    public void setDeutcheWortType(@NonNull String deutcheWortType) {
        this.deutcheWortType = deutcheWortType;
    }

    public void setEnglischeTranslation(@NonNull String englischeTranslation) {
        this.englischeTranslation = englischeTranslation;
    }

    public void setEnteredDate(long enteredDate) {
        this.enteredDate = enteredDate;
    }

    public int getIndex() {
        return index;
    }

    public String getDeutcheWort() {
        return deutcheWort;
    }

    public String getDeutcheWortType() {
        return deutcheWortType;
    }

    public String getEnglischeTranslation() {
        return englischeTranslation;
    }

    public long getEnteredDate(){
        return enteredDate;
    }
}
