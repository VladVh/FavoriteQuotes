package com.development.vvoitsekh.favoritequotes.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    private MainPresenter mMainPresenter;

    @BindView(R.id.quote_textview) TextView mQuoteTextView;
    @BindView(R.id.author_textview) TextView mAuthorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_quote);
        ButterKnife.bind(this);

        mMainPresenter.attachView(this);
        mMainPresenter.loadQuote();
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
    }

    @Override
    public void showError() {
        mQuoteTextView.setText(R.string.error_loading_quote);
        mAuthorTextView.setText("");
    }
}
