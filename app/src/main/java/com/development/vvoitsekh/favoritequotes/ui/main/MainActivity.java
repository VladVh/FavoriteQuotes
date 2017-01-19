package com.development.vvoitsekh.favoritequotes.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.local.PersistentContract;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;
import com.development.vvoitsekh.favoritequotes.ui.favorites.FavoritesActivity;
import com.development.vvoitsekh.favoritequotes.ui.settings.SettingsActivity;
import com.development.vvoitsekh.favoritequotes.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.quote_textview)
    TextView mQuoteTextView;
    @BindView(R.id.author_textview)
    TextView mAuthorTextView;
    @BindView(R.id.favorites_imageButton)
    ImageButton mFavoritesImageButton;

    private Menu mMenu;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        setContentView(R.layout.activity_quote);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.quotes);

        ButterKnife.bind(this);

        mMainPresenter.attachView(this);

        if (savedInstanceState != null) {
            mQuoteTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT));
            mAuthorTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR));
        } else {
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(getApplicationContext()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mMenu = menu;

//        MenuItem item = mMenu.findItem(R.id.action_share);
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, mQuoteTextView.getText());
//        sendIntent.setType("text/plain");
//        setShareIntent(sendIntent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(getApplicationContext()));
            item.setIcon(R.mipmap.ic_close_octagon_black_48dp);
            item.setEnabled(false);
        } else if (item.getItemId() == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mQuoteTextView.getText());
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (item.getItemId() == R.id.action_favorites) {
            startActivity(FavoritesActivity.getStartIntent(getApplicationContext()));
        } else if (item.getItemId() == R.id.action_settings) {
            startActivityForResult(SettingsActivity.getStartIntent(getApplicationContext()),
                    SettingsActivity.SettingsFragment.LANGUAGE_CHANGED);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SettingsActivity.SettingsFragment.LANGUAGE_CHANGED:
                if (resultCode == SettingsActivity.SettingsFragment.LANGUAGE_CHANGED) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void addToFavorites(View view) {
        mMainPresenter.addToFavorites(mQuoteTextView.getText().toString(), mAuthorTextView.getText().toString());
        showExistsInFavorites(true);
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
        mMainPresenter.isQuoteInFavorites(quote.getQuoteText());
        mQuoteTextView.setText(quote.getQuoteText());
        mAuthorTextView.setText(quote.getQuoteAuthor());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMenu.findItem(R.id.action_refresh).setIcon(R.mipmap.ic_autorenew_black_48dp);
                mMenu.findItem(R.id.action_refresh).setEnabled(true);
            }
        }, 2000);

    }

    @Override
    public void showExistsInFavorites(boolean exists) {
        if (exists) {
            mFavoritesImageButton.setImageResource(R.mipmap.ic_heart_black_48dp);
            mFavoritesImageButton.setEnabled(false);
        } else {
            mFavoritesImageButton.setImageResource(R.mipmap.ic_thumb_up_black_48dp);
            mFavoritesImageButton.setEnabled(true);
        }
    }

    @Override
    public void showError() {
        mQuoteTextView.setText(R.string.error_loading_quote);
        mAuthorTextView.setText("");
    }
}
