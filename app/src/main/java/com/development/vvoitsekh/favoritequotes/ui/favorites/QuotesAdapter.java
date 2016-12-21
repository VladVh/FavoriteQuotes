package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v.voitsekh on 21.12.2016.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private List<Quote> mQuotes;

    @Inject
    public QuotesAdapter() {
        mQuotes = new ArrayList<>();
    }
    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);
        return new QuotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuotesViewHolder holder, int position) {
        Quote quote = mQuotes.get(position);
        holder.quoteTextView.setText(quote.getQuoteText());
        holder.authorTextView.setText(quote.getQuoteAuthor());
    }

    public void setQuotes(List<Quote> quotes) {
        mQuotes = quotes;
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    class QuotesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quote_item_textview) TextView quoteTextView;
        @BindView(R.id.author_item_textview) TextView authorTextView;

        public QuotesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
