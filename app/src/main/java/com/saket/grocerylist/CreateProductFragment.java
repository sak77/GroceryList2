package com.saket.grocerylist;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.saket.grocerylist.data.Product;
import com.saket.grocerylist.data.SingletonGroceryDBInstance;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProductFragment newInstance(String param1, String param2) {
        CreateProductFragment fragment = new CreateProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_product, container, false);
        TextInputEditText edtProductName = root.findViewById(R.id.edtProductName);
        TextInputEditText edtProductQty = root.findViewById(R.id.edtProductQty);
        Button btnsave = root.findViewById(R.id.btnSave);
        btnsave.setOnClickListener(view -> {
            if (!(edtProductName.getText().toString().isEmpty()
                    || edtProductQty.getText().toString().isEmpty())) {
                Product newProduct = new Product();
                newProduct.product_name = edtProductName.getText().toString();
                newProduct.qty = edtProductQty.getText().toString();
                addProduct(newProduct);
                //Close fragment
                getFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Please enter all values.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        Button btnCancel = root.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> {
            //Close fragment
            getFragmentManager().popBackStack();
        });
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void addProduct(Product newProduct) {
        AsyncTask.execute(() -> {
            //SingletonGroceryDBInstance.getInstance(getActivity()).clearAllTables();
            SingletonGroceryDBInstance.getInstance(getActivity()).productDao().addProduct(
                    newProduct);
        });
    }

}
