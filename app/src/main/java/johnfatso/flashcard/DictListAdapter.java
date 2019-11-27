package johnfatso.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DictListAdapter extends RecyclerView.Adapter<DictListAdapter.ListItemViewHolder> {

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        public TextView wort;
        public TextView type;
        public TextView translation;

        public ListItemViewHolder(@NonNull TextView wort, @NonNull TextView type, @NonNull TextView translation) {
            super(wort);
            this.wort = wort;
            this.translation = translation;
            this.type = type;
        }
    }

    private DictDefinition[] dictDefinitionEntries;

    public DictListAdapter(DictDefinition[] dictDefinitionEntries) {
        this.dictDefinitionEntries = dictDefinitionEntries;
    }

    /**
     * Called when RecyclerView needs a new {@link ListItemViewHolder} of the given type to represent
     * an item.
     *
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ListItemViewHolder, int)
     */
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

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ListItemViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     *
     *
     * @param VH   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull  ListItemViewHolder VH, int position) {
        VH.wort.setText(dictDefinitionEntries[position].wort);
        VH.type.setText(dictDefinitionEntries[position].wort);
        VH.translation.setText(dictDefinitionEntries[position].translation);
    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
