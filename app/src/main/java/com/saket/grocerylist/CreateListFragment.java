package com.saket.grocerylist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.saket.grocerylist.data.ItemsList;
import com.saket.grocerylist.data.Product;
import com.saket.grocerylist.data.SingletonGroceryDBInstance;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sshriwas on 2019-11-03
 */
public class CreateListFragment extends Fragment implements ProductsAdapter.onItemSelectedListener {

    private static CreateListFragment sCreateListFragment;

    private static final String NAME = "name";
    private static final String PRODUCTS = "products";
    private static final String ID = "id";

    List<Product> lstProducts = new ArrayList<>();
    List<Product> selectedProductsList = new ArrayList<>();
    ProductsAdapter productsAdapter;
    RecyclerView mRecyclerView;
    TextInputEditText mTextInputEditText;
    Button mBtnDelete;
    static ItemsList mCurrentItemList;

    public static CreateListFragment getInstance(ItemsList itemsList) {
        if (sCreateListFragment == null) {
            sCreateListFragment = new CreateListFragment();
        }
        if (itemsList !=null) {
            mCurrentItemList = itemsList;
            Bundle args = new Bundle();
            args.putString(NAME, itemsList.list_name);
            args.putString(PRODUCTS, itemsList.products);
            args.putInt(ID, itemsList.id);
            sCreateListFragment.setArguments(args);
        } else {
            sCreateListFragment.setArguments(null);
        }
        return sCreateListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_list, container, false);
        mRecyclerView = root.findViewById(R.id.lstProducts);

        productsAdapter = new ProductsAdapter(lstProducts, CreateListFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.drawable.item_decoration));
        mRecyclerView.setAdapter(productsAdapter);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mTextInputEditText = root.findViewById(R.id.edtInputListName);
        Button btnSave = root.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            if (selectedProductsList.size() != 0 && !mTextInputEditText.getText().toString().isEmpty()) {
                try {
                    addList(selectedProductsList, mTextInputEditText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(getActivity(), "Enter all fields", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnCancel = root.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> {
                getFragmentManager().popBackStack();
        });

        mBtnDelete = root.findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(view -> new RemoveListTask().execute());

        getAllProducts();
        return root;
    }


    /**
     * Updating value of inputedittext view does not work in onCreateView.
     * Hence it is required to be implemented in onResume() view.
     */
    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null) {
            // = args.getInt(ID);
            mTextInputEditText.setText(args.getString(NAME));
            String products = args.getString(PRODUCTS);
            products = products.substring(1, products.length()-1);
            Log.v("TAG", "Products - " + products);
            String[] arrProducts = products.split(",");
            new GetProductByIdAsync().execute(arrProducts);
            //Since this is an existing list, give option to delete it
            mBtnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void getAllProducts() {
        new GetProductsAsync().execute();
    }

    @Override
    public void onItemSelected(Product selectedProduct, View selectedView) {
        if (!selectedProductsList.contains(selectedProduct)) {
            //INSERT
            selectedProductsList.add(selectedProduct);
            selectedView.setSelected(true);
        } else {
            // DELETE
            selectedProductsList.remove(selectedProduct);
            selectedView.setSelected(false);
        }
    }

    private class GetProductByIdAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            lstProducts.clear();
            for (String string : strings) {
                int product_id = Integer.parseInt(string);
                Product product = SingletonGroceryDBInstance.getInstance(
                        getActivity()).productDao().getProductById(product_id);
                lstProducts.add(product);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productsAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * Here we get a warning to make this asynctask static or a top-level class or else
     * it may cause a memory leak. This is true if the async task is performing a
     * long-running task. However, since in our case it is a local query and is done
     * soon, so it should not be a problem to keep it as a non-static inner class.
     */
    private class GetProductsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            lstProducts.clear();
            lstProducts.addAll(SingletonGroceryDBInstance.getInstance(
                    getActivity()).productDao().getAllProducts());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productsAdapter.notifyDataSetChanged();
        }
    }


    private void addList(List<Product> lstProduct, String lstName) throws JSONException {

        JSONArray jsonArray = new JSONArray();

        for (Product product : lstProduct) {
            jsonArray.put(product.product_id);
        }

        ItemsList newItemList = new ItemsList();
        newItemList.list_name = lstName;
        newItemList.products = jsonArray.toString();  //Array of product Ids
        new AddlistAsync().execute(newItemList);
    }

    /**
     *
     * Here we get a warning to make this asynctask static or a top-level class or else
     * it may cause a memory leak. This is true if the async task is performing a
     * long-running task. However, since in our case it is a local query and is done
     * soon, so it should not be a problem to keep it as a non-static inner class.
     */
    private class AddlistAsync extends AsyncTask<ItemsList, Void, Void> {

        @Override
        protected Void doInBackground(ItemsList... itemsLists) {
            //SingletonGroceryDBInstance.getInstance(getActivity()).clearAllTables();
            SingletonGroceryDBInstance.getInstance(getActivity()).listDao().addGroceryList(
                    itemsLists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Close fragment
            getFragmentManager().popBackStack();
        }
    }

    private class RemoveListTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return SingletonGroceryDBInstance.getInstance(getActivity()).listDao()
                    .removeGroceryList(mCurrentItemList);
        }

        @Override
        protected void onPostExecute(Integer l) {
            if (l>-1) {
                Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_SHORT)
                        .show();
                //Close fragment
                getFragmentManager().popBackStack();
            }
            super.onPostExecute(l);
        }
    }
}
