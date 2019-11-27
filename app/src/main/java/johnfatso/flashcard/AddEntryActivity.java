package johnfatso.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AddEntryActivity extends AppCompatActivity implements DBreader{

    //Views for adding the entries
    TextView specimen, translation;
    Spinner wordTypeSpinner;

    //Dict entry repo for referring the DB
    DictEntryRepository repository;

    //DB access flags
    int total_count_from_db;

    //Status values to send back to the Main activity
    public static final int ACTIVITY_RESULT_ENTRY_INSERTED = 0x01;
    public static final int ACTIVITY_BACK_PRESSED = 0x02;
    public static final String LOG_TAG_AddEntryActivity = "TAG";

    //status values for tracing the DB reference status
    private final int STATUS_PROCESS_WAITING_FOR_COUNT_READING = 0x01;
    private final int STATUS_PROCESS_WAITING_FOR_PUSHING_ENTRY_TO_DB = 0x02;
    private final int STATUS_PROCESS_POST_PUSH_COUNT_READ = 0x03;
    private final int STATUS_IDLE = 0xff;

    //status flag for DB reference
    private int STATUS_PROCESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        //initialize RoomDBRepository
        repository = new DictEntryRepository(getApplication(), this);

        //DB access flags initialization
        STATUS_PROCESS = STATUS_IDLE;
        total_count_from_db = -99;

        //view definitions
        specimen = findViewById(R.id.foreign_word_textbox);
        translation = findViewById(R.id.translation_textbox);
        wordTypeSpinner = findViewById(R.id.spinner_word_type);

        /**
         * set onClickListener for add button
         */
        Button add_button = findViewById(R.id.button_add_entry);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STATUS_PROCESS = STATUS_PROCESS_WAITING_FOR_COUNT_READING;
                Log.v(LOG_TAG_AddEntryActivity, "onClick triggered | count requested | process status :"+STATUS_PROCESS);
                repository.getCountOfTotalDBEntries();
            }
        });

        specimen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    specimen.setText("");
                }
                else {
                    if(specimen.getText().toString().length() == 0){
                        specimen.setText(R.string.specimen_default);
                    }
                }
            }
        });

        translation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    translation.setText("");
                }
                else {
                    Log.v(LOG_TAG_AddEntryActivity, "FocusLost : current text |"+translation.getText().toString()+"|");
                    if(translation.getText().toString().length() == 0){
                        translation.setText(R.string.translation_default);
                    }
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_type_spinner, getResources().getStringArray(R.array.word_type_spinner));
        wordTypeSpinner.setAdapter(adapter);

        Log.v(LOG_TAG_AddEntryActivity, "Add entry activity created");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.putExtra("message", ACTIVITY_BACK_PRESSED);

        setResult(RESULT_CANCELED);
        finish();
        Log.v("TAG", "Activity completed since back pressed");
    }

    public void addEntryToTheDB(){

        Log.v(LOG_TAG_AddEntryActivity, "entry push function addEntryToTheDB entered");

        //read data from form
        String foreign_Word_from_the_text_box = specimen.getText().toString();
        String translation_from_the_text_box = (String) translation.getText().toString();
        String type_from_spinner = (String) wordTypeSpinner.getSelectedItem().toString();
        long currentTime = System.currentTimeMillis();

        DictEntry entry = new DictEntry(total_count_from_db+1, foreign_Word_from_the_text_box, type_from_spinner, translation_from_the_text_box, currentTime);
        Log.v(LOG_TAG_AddEntryActivity, "entries :"+total_count_from_db);

        repository.insert(entry);
        Log.v(LOG_TAG_AddEntryActivity, "entry push function addEntryToTheDB completed");
    }

    void returnResultIntent(){
        //creating intent fo passing info back to the main activity
        Intent intent = new Intent();
        intent.putExtra("updatedDBSize", total_count_from_db);
        intent.putExtra("message", ACTIVITY_RESULT_ENTRY_INSERTED);

        //the re
        setResult(RESULT_OK, intent);
        finish();

        Log.v("TAG", "Activity completed since push process completed");
    }

    @Override
    public void DBreaderResponse(Object data) {
        if(STATUS_PROCESS == STATUS_PROCESS_WAITING_FOR_COUNT_READING){
            Log.v(LOG_TAG_AddEntryActivity, "callback called | status : "+STATUS_PROCESS);
            total_count_from_db = (int) data;
            STATUS_PROCESS = STATUS_PROCESS_WAITING_FOR_PUSHING_ENTRY_TO_DB;
            addEntryToTheDB();
        }
        else if(STATUS_PROCESS == STATUS_PROCESS_WAITING_FOR_PUSHING_ENTRY_TO_DB){
            Log.v(LOG_TAG_AddEntryActivity, "callback called | status : "+STATUS_PROCESS);
            repository.getCountOfTotalDBEntries();
            STATUS_PROCESS = STATUS_PROCESS_POST_PUSH_COUNT_READ;
        }
        else if(STATUS_PROCESS == STATUS_PROCESS_POST_PUSH_COUNT_READ){
            Log.v(LOG_TAG_AddEntryActivity, "callback called | status : "+STATUS_PROCESS);
            total_count_from_db = (int) data;
            Toast.makeText(getApplicationContext(), "new Data pushed, latest count :"+total_count_from_db, Toast.LENGTH_SHORT).show();
            returnResultIntent();
        }
        Log.v(LOG_TAG_AddEntryActivity, "callback called and completed | status : "+STATUS_PROCESS);
    }
}
