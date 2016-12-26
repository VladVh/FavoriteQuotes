package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

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
    @BindView(R.id.favorites_empty_textView) TextView mEmptyTextView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setAdapter(mQuotesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));

        mFavoritesPresenter.attachView(this);
        mFavoritesPresenter.getQuotes();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        return intent;
    }

    public void deleteFromFavorites(View view) {
        ViewParent parent = view.getParent().getParent();
        RecyclerView recyclerView = (RecyclerView) parent.getParent();
        long id = recyclerView.getChildAdapterPosition((View) parent);

        Quote quote = mQuotesAdapter.getItem((int) id);

        mQuotesAdapter.delete(quote);
        mQuotesAdapter.notifyDataSetChanged();

        mFavoritesPresenter.deleteQuoteFromFavorites(quote);
    }

    @Override
    public void showFavorites(List<Quote> quotes) {
        mEmptyTextView.setVisibility(View.INVISIBLE);

        mQuotesAdapter.setQuotes(quotes);
        mQuotesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFavoritesEmpty() {
        mEmptyTextView.setVisibility(View.VISIBLE);
        Log.e("Empty DB", "No quotes in the database");
    }

    @Override
    public void showError() {
        Log.e("Error accessing DB", "Error accessing DB");
    }
}
