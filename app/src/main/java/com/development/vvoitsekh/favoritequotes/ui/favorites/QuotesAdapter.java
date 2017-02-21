package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.main.MainActivity;

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
    private Context mContext;

    @Inject
    public QuotesAdapter() {
        mQuotes = new ArrayList<>();
        mContext = null;
    }

    public void setContext(Context context) {
        mContext = context;
    }
    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);
        return new QuotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuotesViewHolder holder, int position) {
        Quote quote = mQuotes.get(position);
        holder.mQuoteTextView.setText(quote.getQuoteText());
        holder.mAuthorTextView.setText(quote.getQuoteAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newIntent(mContext,
                        new Quote(holder.mQuoteTextView.getText().toString(),
                                holder.mAuthorTextView.getText().toString()));
                mContext.startActivity(intent);

            }
        });
    }

    public void setQuotes(List<Quote> quotes) {
        mQuotes = quotes;
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    public Quote getItem(int id) {
        return mQuotes.get(id);
    }

    public void delete(Quote quote) {
        mQuotes.remove(quote);
    }

    class QuotesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quote_item_textview) TextView mQuoteTextView;
        @BindView(R.id.author_item_textview) TextView mAuthorTextView;

        public QuotesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
