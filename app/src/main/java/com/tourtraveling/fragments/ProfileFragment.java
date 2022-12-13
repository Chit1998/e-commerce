package com.tourtraveling.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tourtraveling.R;
import com.tourtraveling.activities.MyCartActivity;
import com.tourtraveling.activities.admin.OrderListActivity;
import com.tourtraveling.adapters.AddressAdapter;
import com.tourtraveling.interfaces.AddressSelectedListener;
import com.tourtraveling.models.AddressModel;
import com.tourtraveling.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment implements AddressSelectedListener {

    private TextView text_user_name_profile,text_email_profile,text_addToCard_profile,text_myOrder_profile;
    private ImageView add_address_image_profile;
    private RecyclerView recyclerView_add_address;
    private FirebaseAuth auth;
    private AddressAdapter addressAdapter;
    private List<AddressModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        init(v);

        getUserData();

        add_address_image_profile.setOnClickListener(v1 ->{
            openAddressDialog();
        });

        text_myOrder_profile.setOnClickListener(v1 -> startActivity(new Intent(requireContext(), OrderListActivity.class)));

        list = new ArrayList<>();
        addressAdapter = new AddressAdapter(requireContext(), list, this);
        recyclerView_add_address.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView_add_address.setAdapter(addressAdapter);

        getAddress();

        text_addToCard_profile.setOnClickListener(v1 ->
                startActivity(new Intent(requireContext(), MyCartActivity.class)));

        return v;
    }

    private void init(View v) {
        auth = FirebaseAuth.getInstance();
        text_user_name_profile = v.findViewById(R.id.text_user_name_profile);
        text_email_profile = v.findViewById(R.id.text_email_profile);
        text_addToCard_profile = v.findViewById(R.id.text_addToCard_profile);
        add_address_image_profile = v.findViewById(R.id.add_address_image_profile);
        recyclerView_add_address = v.findViewById(R.id.recyclerView_add_address);
        text_myOrder_profile = v.findViewById(R.id.text_myOrder_profile);
    }

    private void getUserData() {
        FirebaseFirestore.getInstance()
                .collection(Constants.USER_DB)
                .document(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String name = task.getResult().getString(Constants.NAME);
                        String email = task.getResult().getString(Constants.EMAIL_ADDRESS);
                        text_user_name_profile.setText(name);
                        text_email_profile.setText(email);
                    }
                });
    }

    private void openAddressDialog() {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.add_address_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        EditText addressLineOne = view.findViewById(R.id.eAddressLine);
        EditText addressLineTwo = view.findViewById(R.id.eAddressBuilding);
        EditText addressCity = view.findViewById(R.id.eAddressCity);
        EditText addressZipcode = view.findViewById(R.id.eAddressZipcode);
        EditText addressState = view.findViewById(R.id.eAddressState);
        EditText addressCountry = view.findViewById(R.id.eAddressCountry);
        EditText addressPhone = view.findViewById(R.id.eAddressPhoneNumber);
        EditText name = view.findViewById(R.id.eName);
        TextView text_cancel_dialog = view.findViewById(R.id.text_cancel_address);
        TextView text_add_dialog = view.findViewById(R.id.text_save_address);

        validationAddress(addressLineOne, addressCity,addressZipcode, addressState, addressCountry, addressPhone, name);

        text_cancel_dialog.setOnClickListener(v -> dialog.dismiss());

        text_add_dialog.setOnClickListener(v ->
            setAddressData(
                    addressLineOne,
                    addressCity,
                    addressZipcode,
                    addressState,
                    addressCountry,
                    addressPhone,
                    addressLineTwo,
                    name,
                    dialog
            )
        );

        dialog.show();

    }

    private void validationAddress(EditText addressLineOne, EditText addressCity, EditText addressZipcode,
                                   EditText addressState, EditText addressCountry, EditText addressPhone, EditText name) {

        addressLineOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressLineOne.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressLineOne.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        addressCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressCity.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressCity.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        addressZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressZipcode.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressZipcode.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        addressState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressState.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressState.setBackgroundResource(R.drawable.edit_background);
                }

            }
        });

        addressCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressCountry.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressCountry.setBackgroundResource(R.drawable.edit_background);
                }

            }
        });

        addressPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    addressPhone.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    addressPhone.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    name.setBackgroundResource(R.drawable.edit_wrong_background);
                }

                if (!s.toString().isEmpty()){
                    name.setBackgroundResource(R.drawable.edit_background);
                }
            }
        });

    }

    private void setAddressData(EditText addressLineOne, EditText addressCity, EditText addressZipcode,
                                EditText addressState, EditText addressCountry, EditText addressPhone, EditText building, EditText name, AlertDialog dialog) {
        if (name.getText().toString().isEmpty()){
            name.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressLineOne.getText().toString().isEmpty()){
            addressLineOne.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressCity.getText().toString().isEmpty()){
            addressCity.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressZipcode.getText().toString().isEmpty()){
            addressZipcode.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressState.getText().toString().isEmpty()){
            addressState.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressCountry.getText().toString().isEmpty()){
            addressCountry.setBackgroundResource(R.drawable.edit_wrong_background);
        }else if (addressPhone.getText().toString().isEmpty()){
            addressPhone.setBackgroundResource(R.drawable.edit_wrong_background);
        }else {
            sendDataToDB(
                    addressLineOne.getText().toString(),
                    building.getText().toString(),
                    addressCity.getText().toString(),
                    addressZipcode.getText().toString(),
                    addressState.getText().toString(),
                    addressCountry.getText().toString(),
                    addressPhone.getText().toString(),
                    name.getText().toString(),
                    dialog);
        }
    }

    private void sendDataToDB(String addressOne, String addressTwo, String city, String zipcode,
                              String state, String country, String phone, String name, AlertDialog dialog) {

        AddressModel model = new AddressModel(addressOne+" "+addressTwo,city, state,zipcode,
                country, phone,name,false,new Date(System.currentTimeMillis()));

        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("address")
                .collection(Objects.requireNonNull(auth.getUid()))
                .document()
                .set(model).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Constants.setMessage("saved", requireContext());
                        dialog.dismiss();
                    }
                });
    }

    private void getAddress() {
        FirebaseFirestore.getInstance()
                .collection("orders")
                .document("address")
                .collection(Objects.requireNonNull(auth.getUid()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        list.clear();
                        for (DocumentSnapshot snapshot: task.getResult()){
                            AddressModel model = new AddressModel(snapshot.getString("address"),
                                    snapshot.getString("city"),
                                    snapshot.getString("state"),
                                    snapshot.getString("zipcode"),
                                    snapshot.getString("country"),
                                    snapshot.getString("phone"),
                                    snapshot.getString("full_name"),
                                    Boolean.TRUE.equals(snapshot.getBoolean("stateSelected")),
                                    snapshot.getDate("current_date"));

                            list.add(model);
                        }
                        addressAdapter.notifyDataSetChanged();
                    }
                });


    }

    @Override
    public void addOnItemSelected(AddressModel model, RadioButton button) {
        FirebaseFirestore.getInstance().collection("orders")
                .document("address")
                .collection(auth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    for (DocumentSnapshot snapshot : task.getResult()){
                        String docId = snapshot.getId();

                        if (model.isStateSelected() == true){
                            button.setChecked(true);
                        }else{
                            if (snapshot.getString("address").equals(model.getAddress())){
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("stateSelected", true);
                                button.setChecked(true);
                                DocumentReference reference = FirebaseFirestore.getInstance().collection("orders")
                                        .document("address")
                                        .collection(auth.getUid())
                                        .document(docId);
                                reference.update(map);
                            }else{
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("stateSelected", false);
                                button.setChecked(false);
                                DocumentReference reference = FirebaseFirestore.getInstance().collection("orders")
                                        .document("address")
                                        .collection(auth.getUid())
                                        .document(docId);
                                reference.update(map);
                            }
                        }


                    }
                });
    }
}