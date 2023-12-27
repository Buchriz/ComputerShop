package com.example.comupershop;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class FragmentAdd extends Fragment {

    private View view;
    private EditText etname, etprice;
    private Button btn;


    MyDatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
        databaseHelper = new MyDatabaseHelper(requireContext());
        etname = view.findViewById(R.id.name);
        etprice = view.findViewById(R.id.price);


        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                FragmentList f = new FragmentList();
                f.setArguments(bundle);

                if(etname.getText().toString().length() == 0 || etprice.getText().toString().length() == 0)
                {
                    Toast.makeText(requireActivity(), "Enter Name or Price", Toast.LENGTH_SHORT).show();
                }
                else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, f).commit();
                    databaseHelper.addProduct(etname.getText().toString(), Integer.parseInt(etprice.getText().toString()));
                    etname.setText("");
                    etprice.setText("");
                }


            }
        });
        return view;
    }

}