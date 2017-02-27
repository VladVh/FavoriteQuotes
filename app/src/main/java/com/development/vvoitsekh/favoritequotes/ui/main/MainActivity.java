package com.development.vvoitsekh.favoritequotes.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String TAG = "MainActivity";
    private static final String QUOTE_TEXT = "quote_text";
    private static final String QUOTE_AUTHOR = "quote_author";

    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.quote_textview)
    TextView mQuoteTextView;
    @BindView(R.id.author_textview)
    TextView mAuthorTextView;
    @BindView(R.id.favorites_imageButton)
    ImageButton mFavoritesImageButton;

    private Menu mMenu;

    public static Intent newIntent(Context context, Quote quote) {
        Intent intent = new Intent(context, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(QUOTE_TEXT, quote.getQuoteText());
        bundle.putString(QUOTE_AUTHOR, quote.getQuoteAuthor());
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

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

        Bundle extras = getIntent().getExtras();

        if (savedInstanceState != null) {
            mQuoteTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT));
            mAuthorTextView.setText(savedInstanceState.getString(PersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR));
        } else if (extras != null) {
            mQuoteTextView.setText(extras.getString(QUOTE_TEXT));
            mAuthorTextView.setText(extras.getString(QUOTE_AUTHOR));
        } else {
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(this));
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
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(this));
            item.setIcon(R.mipmap.ic_close_octagon_black_48dp);
            item.setEnabled(false);
        } else if (item.getItemId() == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mQuoteTextView.getText()
                    + "  \u00A9 "
                    + mAuthorTextView.getText());
            startActivity(Intent.createChooser(sharingIntent, ""));
        } else if (item.getItemId() == R.id.action_favorites) {
            startActivity(FavoritesActivity.newIntent(this));
        } else if (item.getItemId() == R.id.action_settings) {
            startActivityForResult(SettingsActivity.newIntent(this),
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
                    startActivity(MainActivity.newIntent(this));
                    finish();
                }
                break;
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
    protected void onResume() {
        super.onResume();
        if (mQuoteTextView.getText().length() > 0) {
            mMainPresenter.isQuoteInFavorites(mQuoteTextView.getText().toString());
        }
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMenu.findItem(R.id.action_refresh).setIcon(R.mipmap.ic_autorenew_black_48dp);
                mMenu.findItem(R.id.action_refresh).setEnabled(true);
            }
        }, 500);
    }
}
