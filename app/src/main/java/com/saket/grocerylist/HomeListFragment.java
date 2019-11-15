package com.saket.grocerylist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.grocerylist.ItemsList.ItemsListAdapter;
import com.saket.grocerylist.data.ItemsList;
import com.saket.grocerylist.data.SingletonGroceryDBInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sshriwas on 2019-11-02
 */
public class HomeListFragment  extends Fragment implements ItemsListAdapter.OnItemSelectedListener{

    List<ItemsList> lstItems = new ArrayList<>();
    ItemsListAdapter mItemsListAdapter;

    static HomeListFragment createInstance() {
        return new HomeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.lstGroceries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemsListAdapter = new ItemsListAdapter(lstItems, HomeListFragment.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.drawable.item_decoration));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mItemsListAdapter);

        Button btnGetList = root.findViewById(R.id.btnGetList);
        btnGetList.setOnClickListener(view -> {
            getAllLists();
        });

        Button btnCreateProduct = root.findViewById(R.id.btnCreateProduct);
        btnCreateProduct.setOnClickListener(view -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CreateProductFragment.newInstance("", ""))
                    //.add(R.id.fragment_container, CreateProductFragment.newInstance("", ""), CreateProductFragment.class.getName())
                    .addToBackStack(null)
                    .commit();
        });
        Button btnGetProduct = root.findViewById(R.id.btnGetProducts);
        btnGetProduct.setOnClickListener(view -> {
        });

        return root;
    }

    private void getAllLists() {
        new GetListsAsync().execute();
    }

    @Override
    public void onItemSelected(ItemsList itemsList) {
        //Go to List details
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CreateListFragment.getInstance(itemsList))
                .addToBackStack(null)
                .commit();
    }

    private class GetListsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            lstItems.clear();
            lstItems.addAll(SingletonGroceryDBInstance.getInstance(
                    getActivity()).listDao().getAllLists());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mItemsListAdapter.notifyDataSetChanged();
        }
    }
}