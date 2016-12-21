package com.development.vvoitsekh.favoritequotes.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.local.PersistentContract;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;
import com.development.vvoitsekh.favoritequotes.ui.favorites.FavoritesActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;

    @BindView(R.id.quote_textview) TextView mQuoteTextView;
    @BindView(R.id.author_textview) TextView mAuthorTextView;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        setContentView(R.layout.activity_quote);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        ButterKnife.bind(this);

        mMainPresenter.attachView(this);

        if (savedInstanceState != null) {
            mQuoteTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT));
            mAuthorTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR));
        } else {
            mMainPresenter.loadQuote();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mMainPresenter.loadQuote();
            item.setEnabled(false);
            item.setVisible(false);
        } else if(item.getItemId() == R.id.action_favorites) {
            startActivity(FavoritesActivity.getStartIntent(getApplicationContext()));
        } else if(item.getItemId() == R.id.favorites_imageButton) {
            mMainPresenter.addToFavorites(mQuoteTextView.getText().toString(), mAuthorTextView.getText().toString());
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT, mQuoteTextView.getText().toString());
        outState.putString(PersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR, mAuthorTextView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    @Override
    public void showQuote(Quote quote) {
        mQuoteTextView.setText(quote.getQuoteText());
        mAuthorTextView.setText(quote.getQuoteAuthor());
        mMenu.findItem(R.id.action_refresh).setEnabled(true);
        mMenu.findItem(R.id.action_refresh).setVisible(true);
    }

    @Override
    public void showError() {
        mQuoteTextView.setText(R.string.error_loading_quote);
        mAuthorTextView.setText("");
    }
}
