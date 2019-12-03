package johnfatso.flashcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity implements DBreader{

    final String LOG_TAG = "TAG";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DictEntryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v(LOG_TAG, "activity on create entered");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        recyclerView = findViewById(R.id.recyclerview_review_list_holder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        repository = new DictEntryRepository(getApplication(), this);
        repository.getDataSet();
        Log.v(LOG_TAG, "activity on create completed");
    }

    public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.CustomViewHolder>{

        ArrayList<DictEntry> dataset;

        RecyclerViewCustomAdapter(ArrayList<DictEntry> dataset) {
            this.dataset = dataset;
            Log.v(LOG_TAG, "RecyclerViewCustomAdapter created");
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView specimen;
            TextView type;
            TextView translation;
            ImageView deleteButton;

             CustomViewHolder(@NonNull View itemView, TextView specimen, TextView type, TextView translation, ImageView deleteButton) {
                 super(itemView);
                 this.specimen = specimen;
                 this.type = type;
                 this.translation = translation;
                 this.deleteButton = deleteButton;

                 this.deleteButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Log.v(LOG_TAG, "delete button clicked");
                         repository.deleteWord(specimen.getText().toString());
                         dataset.remove(getAdapterPosition());
                         adapter.notifyItemRemoved(getAdapterPosition());
                     }
                 });
                 Log.v(LOG_TAG,"CustomViewHolder created");
            }
        }

        void removeItemFromTheList(int position){
            Log.v("TAG", "item to be removed :"+position+" | count in list : "+getItemCount());

            dataset.remove(position);
            layoutManager.removeViewAt(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, getItemCount());
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            Log.v(LOG_TAG, "OnCreateViewHolder Entered");

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_review_list_item, parent, false);
            TextView specimen =  view.findViewById(R.id.list_item_specimen);
            TextView type =  view.findViewById(R.id.list_item_word_type);
            TextView translation =  view.findViewById(R.id.list_item_translation);
            ImageView deleteButton = view.findViewById(R.id.deleteEntry_reviewActivity_listEntry);

            return new CustomViewHolder(view, specimen, type, translation, deleteButton);
        }


        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

            Log.v(LOG_TAG, "OnBindViewHolder Entered");

            holder.specimen.setText(dataset.get(position).getDeutcheWort());
            holder.type.setText(dataset.get(position).getDeutcheWortType());
            holder.translation.setText(dataset.get(position).getEnglischeTranslation());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            Log.v(LOG_TAG, "getItmCount Entered");
            return dataset.size();
        }
    }

    @Override
    public void DBreaderResponse(Object data) {
        adapter = new RecyclerViewCustomAdapter(toArrayList((DictEntry[]) data));
        recyclerView.setAdapter(adapter);
    }

    ArrayList<DictEntry> toArrayList(DictEntry[] list){
        ArrayList<DictEntry> arrayList = new ArrayList<>();
        for( DictEntry item: list){
            arrayList.add(item);
        }
        return arrayList;
    }
}
