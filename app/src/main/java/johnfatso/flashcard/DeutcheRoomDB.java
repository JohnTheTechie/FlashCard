package johnfatso.flashcard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
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

        Log.v("DB", "getting DB");

        if (INSTANCE == null){
            synchronized (DeutcheRoomDB.class){
                if (INSTANCE == null){
                    INSTANCE = buildDB(context);
                }
            }
        }
        return INSTANCE;
    }

    private static DeutcheRoomDB buildDB(Context context){
        Log.v("DB", "building DB");
        DeutcheRoomDB db = Room.databaseBuilder(context.getApplicationContext(), DeutcheRoomDB.class, "deutche_db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.v("DB", "creating DB for first time");
                        XmlPullParser xml = context.getResources().getXml(R.xml.dictionary);
                        new DeutcheRoomDB.insertAsync(getDatabase(context).dictEntryDao(), xml).execute();
                    }
                })
                .build();
        Log.v("DB", "built DB");
        return db;
    }


    private static class insertAsync extends AsyncTask<Void, Void, Void>{
        DictEntryDao dao;
        XmlPullParser xml;

        public insertAsync(DictEntryDao dao, XmlPullParser xml) {
            this.dao = dao;
            this.xml = xml;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.v("DB", "building the DB with xml");
            try {
                dao.insert(toArray(DictionaryParser.parse(xml)));
            }catch (Exception e){
                Log.v("DB","exception occurred in reading xml :"+e.toString());
            }
            return null;
        }

        DictEntry[] toArray(ArrayList<DictEntry> list){
            DictEntry[] array = new DictEntry[list.size()];
            for(int i=0;i<list.size();i++){
                array[i]=list.get(i);
            }
            return array;
        }
    }
}
