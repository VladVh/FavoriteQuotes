package com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.voitsekh on 23.02.2017.
 */

public class MultiSelector {
    private SparseBooleanArray mSelections = new SparseBooleanArray();
    private WeakHolderTracker mTracker = new WeakHolderTracker();

    private boolean mIsSelectable;

    public void setSelectable(boolean isSelectable) {
        mIsSelectable = isSelectable;
        //refreshAllHolders();
    }

    public boolean isSelectable() {
        return mIsSelectable;
    }

    private void refreshAllHolders() {
        for (SelectableHolder holder : mTracker.getTrackedHolders()) {
            refreshHolder(holder, mIsSelectable);
        }
    }

    private void refreshHolder(SelectableHolder holder, boolean isSelected) {
        if (holder == null) {
            return;
        }
        holder.setSelectable(isSelected);

        boolean isActivated = mSelections.get(holder.getPosition());
        holder.setActivated(isActivated);
    }

    public boolean isSelected(int position) {
        return mSelections.get(position);
    }

    public void setSelected(int position, boolean isSelected) {
        mSelections.put(position, isSelected);
        refreshHolder(mTracker.getHolder(position), isSelected);
    }

    public void clearSelections() {
        mSelections.clear();
        refreshAllHolders();
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < mSelections.size(); i++) {
            if (mSelections.valueAt(i)) {
                positions.add(mSelections.keyAt(i));
            }
        }

        return positions;
    }

    public void bindHolder(SelectableHolder holder, int position, long id) {
        mTracker.bindHolder(holder, position);
        refreshHolder(holder, mIsSelectable);
    }

    public void setSelected(SelectableHolder holder, boolean isSelected) {
        //setSelected(holder.getPosition(), holder.getItemId(), isSelected);
        setSelected(holder.getPosition(), isSelected);
    }

    public boolean tapSelection(SelectableHolder holder) {
        return tapSelection(holder.getPosition(), holder.getItemId());
    }

    private boolean tapSelection(int position, long itemId) {
        if (mIsSelectable) {
            boolean isSelected = isSelected(position);
            setSelected(position, !isSelected);
            return true;
        } else {
            return false;
        }

    }
}
