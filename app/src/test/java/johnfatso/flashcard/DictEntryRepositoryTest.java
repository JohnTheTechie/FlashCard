package johnfatso.flashcard;

import android.app.Application;
import android.content.Context;

import org.junit.Test;

import java.sql.Date;


import static org.junit.Assert.*;

public class DictEntryRepositoryTest {

    @Test
    public void getCountOfTotalDBEntries() {
    }

    @Test
    public void getNthEntry() {
    }

    @Test
    public void getEntryList() {
    }

    @Test
    public void insert() {
        DictEntryRepository repo = new DictEntryRepository(new Application());
        repo.insert(new DictEntry(1,"test1", "verb", "teste", System.currentTimeMillis()));
        repo.insert(new DictEntry(2,"test2", "noun", "testew", System.currentTimeMillis()));
        repo.insert(new DictEntry(3,"test3", "noun", "testee", System.currentTimeMillis()));
        int count=repo.getCountOfTotalDBEntries();
        assertEquals(count, 3);
    }
}