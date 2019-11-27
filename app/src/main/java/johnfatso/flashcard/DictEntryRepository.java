package johnfatso.flashcard;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DictEntryRepository {
    private DictEntryDao dictEntryDao;
    private DBreader reader;

    protected final String Log_Tag = "TAG";

    public DictEntryRepository(Application application, DBreader reader) {
        DeutcheRoomDB db = DeutcheRoomDB.getDatabase(application);
        dictEntryDao = db.dictEntryDao();
        this.reader =reader;
    }

    public DictEntry getNthEntry(int pos){
        return dictEntryDao.getNthEntry(pos);
    }

    public void getDataSet(){
        new GetAllDataAsyncTask(dictEntryDao, reader).execute();
    }

    private static class GetAllDataAsyncTask extends AsyncTask<Void, Void, DictEntry[]>{

        private DictEntryDao dao;
        private DBreader reader;

        GetAllDataAsyncTask(DictEntryDao dao, DBreader reader){
            this.dao = dao;
            this.reader = reader;
            Log.v("TAG", "get all data async task created");
        }

        @Override
        protected DictEntry[] doInBackground(Void... voids) {
            Log.v("TAG", "get all data async task background started");
            return dao.getListOfWords();
        }

        @Override
        protected void onPostExecute(DictEntry[] dictEntries) {

            Log.v("TAG", "get all data async task completed");
            super.onPostExecute(dictEntries);
            reader.DBreaderResponse(dictEntries);
        }
    }


    /*
    Implementation of AsycTask for Insert function
     */

    public void insert(DictEntry dictEntry){
        new insertAsyncTask(dictEntryDao, reader).execute(dictEntry);
    }

    private static class insertAsyncTask extends AsyncTask<DictEntry, Void, Void>{
        private DictEntryDao asyncTaskDao;
        DBreader reader;

        insertAsyncTask(DictEntryDao dao, DBreader reader){
            asyncTaskDao = dao;
            this.reader = reader;
            Log.v("TAG", "data pusher async task created");
        }

        @Override
        protected Void doInBackground(final DictEntry... params){
            Log.v("TAG", "data pusher async task background started");
            asyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.v("TAG", "data pusher async task completed");
            super.onPostExecute(aVoid);
            reader.DBreaderResponse(aVoid);
        }
    }

    /*
    implementation of the AsyncTask subclass for extracting total number of entries in the DB
     */

    public void getCountOfTotalDBEntries(){
        Integer count_referrence;
        new getTotalCountAsyncTask(dictEntryDao, reader).execute();
    }

    private static class getTotalCountAsyncTask extends AsyncTask<Void, Void, Integer>{

        private DictEntryDao asyncTaskDao;
        DBreader reader;

        getTotalCountAsyncTask(DictEntryDao dao, DBreader reader){
            asyncTaskDao = dao;
            this.reader = reader;
            Log.v("TAG", "count reader async task created");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Log.v("TAG", "count reader async task background started");
            return asyncTaskDao.getTotalItemCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.v("TAG", "count reader async task completed");
            reader.DBreaderResponse(integer);
        }
    }
}
