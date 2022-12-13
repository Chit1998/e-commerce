package com.tourtraveling.interfaces;

import android.widget.RadioButton;

import com.tourtraveling.models.AddressModel;

public interface AddressSelectedListener {
    void addOnItemSelected(AddressModel model, RadioButton button);
}
