package com.tourtraveling.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tourtraveling.R;
import com.tourtraveling.interfaces.AddressSelectedListener;
import com.tourtraveling.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressVH> {
    Context context;
    private List<AddressModel> list;
    private final AddressSelectedListener listener;

    public AddressAdapter(Context context, List<AddressModel> list, AddressSelectedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_address_view_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressVH holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AddressVH extends RecyclerView.ViewHolder {

        TextView text_user_full_name, text_full_address, text_full_address_pin, text_phoneNumber;
        RadioButton rv_radio_button;

        public AddressVH(@NonNull View itemView) {
            super(itemView);
            text_user_full_name = itemView.findViewById(R.id.text_user_full_name);
            text_full_address = itemView.findViewById(R.id.text_full_address);
            text_full_address_pin = itemView.findViewById(R.id.text_full_address_pin);
            text_phoneNumber = itemView.findViewById(R.id.text_phoneNumber);
            rv_radio_button = itemView.findViewById(R.id.rv_radio_button);
        }

        @SuppressLint("SetTextI18n")
        void setData(AddressModel model) {
            text_user_full_name.setText(model.getFull_name());
            text_full_address.setText(model.getAddress()+"\n"+model.getCity()+", "+model.getState()+","+model.getCountry());
            text_full_address_pin.setText(model.getZipcode());
            text_phoneNumber.setText(model.getPhone());

            if (model.isStateSelected() == true){
                rv_radio_button.setChecked(true);
            }
            itemView.setOnClickListener(v -> listener.addOnItemSelected(model,rv_radio_button));
        }
    }
}
