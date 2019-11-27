package johnfatso.flashcard;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DictEntry.class}, version = 1)
public abstract class DeutcheRoomDB extends RoomDatabase {
    public abstract DictEntryDao dictEntryDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile DeutcheRoomDB INSTANCE;

    static DeutcheRoomDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DeutcheRoomDB.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeutcheRoomDB.class, "deutche_db").build();
                }
            }
        }
        return INSTANCE;
    }
}
