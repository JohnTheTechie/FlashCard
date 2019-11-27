package johnfatso.flashcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewListActivity extends AppCompatActivity implements DBreader{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DictEntryRepository repository;
    private DictEntry[] dataset;

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

        public DictEntry[] dataset;

        public class CustomViewHolder extends RecyclerView.ViewHolder{
            public TextView specimen;
            public TextView type;
            public TextView translation;

            public CustomViewHolder(@NonNull View itemView, TextView specimen, TextView type, TextView translation) {
                super(itemView);
                this.specimen = specimen;
                this.type = type;
                this.translation = translation;
            }


        }

        public RecyclerViewCustomAdapter(DictEntry[] dataset) {
            this.dataset = dataset;
        }

        public void setDataset(DictEntry[] dataset){
            this.dataset = dataset;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_review_list_item, parent, false);
            TextView specimen = (TextView) view.findViewById(R.id.list_item_specimen);
            TextView type = (TextView) view.findViewById(R.id.list_item_word_type);
            TextView translation = (TextView) view.findViewById(R.id.list_item_translation);

            CustomViewHolder viewHolder = new CustomViewHolder(view, specimen, type, translation);

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.specimen.setText(dataset[position].getDeutcheWort());
            holder.type.setText(dataset[position].getDeutcheWortType());
            holder.translation.setText(dataset[position].getEnglischeTranslation());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return dataset.length;
        }
    }

    @Override
    public void DBreaderResponse(Object data) {
        dataset = (DictEntry[]) data;

        adapter = new RecyclerViewCustomAdapter(dataset);
        recyclerView.setAdapter(adapter);
    }
}
