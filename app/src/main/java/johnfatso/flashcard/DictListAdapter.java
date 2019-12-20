package johnfatso.flashcard;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DictListAdapter extends RecyclerView.Adapter<DictListAdapter.ListItemViewHolder> {

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        public TextView wort;
        public TextView plural;
        public TextView translation;

        public ListItemViewHolder(@NonNull TextView wort, @NonNull TextView plural, @NonNull TextView translation) {
            super(wort);
            this.wort = wort;
            this.translation = translation;
            this.plural = plural;
        }
    }

    private DictDefinition[] dictDefinitionEntries;

    public DictListAdapter(DictDefinition[] dictDefinitionEntries) {
        this.dictDefinitionEntries = dictDefinitionEntries;
    }

    @NonNull
    @Override
    public DictListAdapter.ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemViewHolder viewHolder;

        TextView wort_view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_item_layout, parent, false);
        TextView wort_type = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_item_layout, parent, false);
        TextView wort_translation = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_item_layout, parent, false);

        viewHolder = new ListItemViewHolder(wort_view, wort_type, wort_translation);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ListItemViewHolder VH, int position) {
        VH.wort.setText(dictDefinitionEntries[position].wort);
        VH.plural.setText(dictDefinitionEntries[position].plural);
        VH.translation.setText(dictDefinitionEntries[position].translation);
    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
