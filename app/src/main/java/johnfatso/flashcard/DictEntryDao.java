package johnfatso.flashcard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DictEntryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(DictEntry entry);

    @Query("DELETE FROM dict_table")
    void deleteAll();

    @Query("SELECT * FROM dict_table ORDER BY german_word ASC")
    DictEntry[] getListOfWords();

    @Query("SELECT COUNT(*) FROM dict_table")
    int getTotalItemCount();

    @Query("SELECT * FROM dict_table WHERE `index`= :positionIndex")
    DictEntry getNthEntry(int positionIndex);
}
