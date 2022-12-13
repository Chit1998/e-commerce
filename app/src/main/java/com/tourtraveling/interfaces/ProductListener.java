package com.tourtraveling.interfaces;

import com.tourtraveling.models.AddProductModel;

public interface ProductListener {
    void onClickListener(AddProductModel model);

    void onLongClickListener(AddProductModel model);
}
