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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewListActivity extends AppCompatActivity implements DBreader{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DictEntryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        recyclerView = findViewById(R.id.recyclerview_review_list_holder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        repository = new DictEntryRepository(getApplication(), this);
        repository.getDataSet();
    }

    public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.CustomViewHolder>{

        public List<DictEntry> dataset;

        public RecyclerViewCustomAdapter(List<DictEntry> dataset) {
            this.dataset = dataset;
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView specimen;
            TextView type;
            TextView translation;
            ImageView deleteButton;

            public CustomViewHolder(@NonNull View itemView, TextView specimen, TextView type, TextView translation, ImageView deleteButton) {
                super(itemView);
                this.specimen = specimen;
                this.type = type;
                this.translation = translation;
                this.deleteButton = deleteButton;

                this.deleteButton.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                repository.deleteWord(specimen.getText().toString());
                removeItemFromTheList(getAdapterPosition());
            }
        }

        void removeItemFromTheList(int position){
            Log.v("TAG", "item to be removed :"+position+" | count in list : "+getItemCount());
            dataset.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, getItemCount());
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_review_list_item, parent, false);
            TextView specimen =  view.findViewById(R.id.list_item_specimen);
            TextView type =  view.findViewById(R.id.list_item_word_type);
            TextView translation =  view.findViewById(R.id.list_item_translation);
            ImageView deleteButton = view.findViewById(R.id.deleteEntry_reviewActivity_listEntry);
            return new CustomViewHolder(view, specimen, type, translation, deleteButton);
        }


        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
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
            return dataset.size();
        }
    }

    @Override
    public void DBreaderResponse(Object data) {
        adapter = new RecyclerViewCustomAdapter(Arrays.asList((DictEntry[]) data));
        recyclerView.setAdapter(adapter);
    }
}
