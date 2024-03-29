package johnfatso.flashcard;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DictEntryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(DictEntry... entry);

    @Query("DELETE FROM dict_table")
    void deleteAll();

    @Query("DELETE FROM dict_table WHERE `german_word`= :word")
    void deleteEntry(String word);

    @Query("SELECT * FROM dict_table ORDER BY german_word ASC")
    DictEntry[] getListOfWords();

    @Query("SELECT COUNT(*) FROM dict_table")
    int getTotalItemCount();

}
