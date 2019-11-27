package johnfatso.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_ENTRY_TO_THE_DB_REQUEST_CODE = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_button = (Button) findViewById(R.id.button_add_entry);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked(view);
            }
        });

        Button review_button = (Button) findViewById(R.id.button_review_entries);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReviewButtonClicked(view);
            }
        });

        Button practice_button = (Button) findViewById(R.id.button_card_scene);
        practice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPracticeButtonClicked(view);
            }
        });
    }

    public void onAddButtonClicked(View view){
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivityForResult(intent, ADD_ENTRY_TO_THE_DB_REQUEST_CODE);
    }

    public void onReviewButtonClicked(View view){
        Intent intent = new Intent(this, ReviewListActivity.class);
        startActivity(intent);
    }

    public void onPracticeButtonClicked(View view){
        Intent intent = new Intent(this, CardFlashActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
