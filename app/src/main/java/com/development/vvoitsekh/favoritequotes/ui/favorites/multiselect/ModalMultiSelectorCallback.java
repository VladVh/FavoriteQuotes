package com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect;

import android.support.v7.view.ActionMode;
import android.view.Menu;

/**
 * Created by v.voitsekh on 23.02.2017.
 */

public abstract class ModalMultiSelectorCallback implements ActionMode.Callback {
    private MultiSelector mMultiSelector;

    private boolean mClearOnPrepare = true;

    public ModalMultiSelectorCallback(MultiSelector multiSelector) {
        mMultiSelector = multiSelector;
    }

    public boolean shouldClearOnPrepare() {
        return mClearOnPrepare;
    }

    public void setClearOnPrepare(boolean clearOnPrepare) {
        mClearOnPrepare = clearOnPrepare;
    }

    public MultiSelector getMultiSelector() {
        return mMultiSelector;
    }

    public void setMultiSelector(MultiSelector multiSelector) {
        mMultiSelector = multiSelector;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        if (mClearOnPrepare) {
            mMultiSelector.clearSelections();
        }
        mMultiSelector.setSelectable(true);
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mMultiSelector.setSelectable(false);
        mMultiSelector.clearSelections();
    }

}
