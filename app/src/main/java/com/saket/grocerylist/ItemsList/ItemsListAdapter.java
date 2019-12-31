package com.saket.grocerylist.ItemsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.grocerylist.R;
import com.saket.grocerylist.data.ItemsList;

import java.util.List;

/**
 * Created by sshriwas on 2019-11-02
 */
public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ListItemViewHolder> {


    private List<ItemsList> mItemsLists;

    public interface OnItemSelectedListener {
        void onItemSelected(ItemsList itemsList);
    }

    private OnItemSelectedListener mOnItemSelectedListener;
    public ItemsListAdapter(List<ItemsList> lists, OnItemSelectedListener listener) {
        mItemsLists = lists;
        mOnItemSelectedListener = listener;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
        ListItemViewHolder listItemViewHolder = new ListItemViewHolder(itemView);
        return listItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.txtName.setText(mItemsLists.get(position).list_name);
        holder.mView.setOnClickListener(view -> {
            mOnItemSelectedListener.onItemSelected(mItemsLists.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mItemsLists.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        View mView;
        ListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            txtName = itemView.findViewById(R.id.txtListName);
        }
    }
}
