package com.development.vvoitsekh.favoritequotes.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.local.PersistentContract;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.notification.Receiver;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;
import com.development.vvoitsekh.favoritequotes.ui.favorites.FavoritesActivity;
import com.development.vvoitsekh.favoritequotes.ui.settings.SettingsActivity;
import com.development.vvoitsekh.favoritequotes.utils.AppUtils;

import java.util.Calendar;

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
    private Toast mToast;

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
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(getApplicationContext()));
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || bundle.getString("notification") == null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent alarmIntent = new Intent(this, Receiver.class); // AlarmReceiver1 = broadcast receiver

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);

            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 12);
            alarmStartTime.set(Calendar.MINUTE, 0);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
                alarmStartTime.add(Calendar.DATE, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("Alarm","Alarms set for everyday 12 am.");
        }
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
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
            mMainPresenter.loadQuote(AppUtils.getCurrentLocale(getApplicationContext()));
            item.setEnabled(false);
            item.setVisible(false);
        } else if(item.getItemId() == R.id.action_favorites) {
            startActivity(FavoritesActivity.getStartIntent(getApplicationContext()));
        } else if(item.getItemId() == R.id.action_settings) {
            startActivity(SettingsActivity.getStartIntent(getApplicationContext()));
        }
        return true;
    }


    public void addToFavorites(View view) {

        long result = mMainPresenter.addToFavorites(mQuoteTextView.getText().toString(), mAuthorTextView.getText().toString());
        if (result == -1) {
            mToast.setText(R.string.favorites_already_added_toast);
            mToast.show();
            //Toast.makeText(this, R.string.favorites_already_added_toast, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, R.string.favorites_added_toast, Toast.LENGTH_SHORT).show();
            mToast.setText(R.string.favorites_added_toast);
            mToast.show();
        }
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMenu.findItem(R.id.action_refresh).setEnabled(true);
                mMenu.findItem(R.id.action_refresh).setVisible(true);
            }
        }, 2000);

    }

    @Override
    public void showError() {
        mQuoteTextView.setText(R.string.error_loading_quote);
        mAuthorTextView.setText("");
    }
}
