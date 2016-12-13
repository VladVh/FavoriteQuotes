package com.development.vvoitsekh.favoritequotes.quote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment implements QuoteContract.View {


    public QuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote, container, false);
    }

    public static QuoteFragment newInstance() {
        return null;
    }

    @Override
    public void showQuote(Quote quote) {

    }

    @Override
    public void showMarkedFavorite() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(QuoteContract.Presenter presenter) {

    }
}
