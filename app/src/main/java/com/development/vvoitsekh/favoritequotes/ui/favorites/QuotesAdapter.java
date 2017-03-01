package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.injection.ActivityContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private List<Quote> mQuotes;
    private Context mContext;

    List<Quote> mSelectedQuotes;

    @Inject
    QuotesAdapter(@ActivityContext Context context) {
        mSelectedQuotes = new ArrayList<>();
        mQuotes = new ArrayList<>();
        mContext = context;
    }

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote_cardview, parent, false);
        return new QuotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuotesViewHolder holder, final int position) {
        Quote quote = mQuotes.get(position);
        holder.mQuoteTextView.setText(quote.getQuoteText());
        holder.mAuthorTextView.setText(quote.getQuoteAuthor());

        if (mSelectedQuotes.contains(mQuotes.get(position))) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGray));
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }
    }

    public void setQuotes(List<Quote> quotes) {
        mQuotes = quotes;
    }


    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    Quote getItem(int id) {
        return mQuotes.get(id);
    }

    void delete(Quote quote) {
        mQuotes.remove(quote);
    }

    class QuotesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quote_item_textview) TextView mQuoteTextView;
        @BindView(R.id.author_item_textview) TextView mAuthorTextView;

        QuotesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
