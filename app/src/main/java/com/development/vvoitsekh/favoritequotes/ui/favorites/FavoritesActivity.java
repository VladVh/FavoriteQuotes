package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends BaseActivity implements FavoritesMvpView {

    @Inject FavoritesPresenter mFavoritesPresenter;
    @Inject QuotesAdapter mQuotesAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mQuotesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFavoritesPresenter.attachView(this);
        mFavoritesPresenter.getQuotes();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        return intent;
    }

    @Override
    public void showFavorites(List<Quote> quotes) {
        mQuotesAdapter.setQuotes(quotes);
        mQuotesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFavoritesEmpty() {
        Log.e("Empty DB", "No quotes in the database");
    }

    @Override
    public void showError() {
        Log.e("Error accessing DB", "Error accessing DB");
    }
}
