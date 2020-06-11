package com.telegram.simpletoast;

import android.view.View;

public interface ItemClickListener<T> {
    void onItemClickListener(T  view, int position);
}
