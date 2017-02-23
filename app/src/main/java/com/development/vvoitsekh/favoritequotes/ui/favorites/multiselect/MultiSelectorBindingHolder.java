package com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class MultiSelectorBindingHolder extends RecyclerView.ViewHolder implements SelectableHolder {
    private final MultiSelector mMultiSelector;

    public MultiSelectorBindingHolder(View itemView, MultiSelector multiSelector) {
        super(itemView);
        mMultiSelector = multiSelector;
    }
}
