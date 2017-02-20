package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mQuotesAdapter.setContext(this);
        mRecyclerView.setAdapter(mQuotesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        mFavoritesPresenter.attachView(this);
        mFavoritesPresenter.getQuotes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteFromFavorites(View view) {
        ViewParent parent = view.getParent();
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
