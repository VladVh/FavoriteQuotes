package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.injection.ActivityContext;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;
import com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect.ModalMultiSelectorCallback;
import com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect.MultiSelector;
import com.development.vvoitsekh.favoritequotes.ui.favorites.multiselect.MultiSelectorBindingHolder;
import com.development.vvoitsekh.favoritequotes.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends BaseActivity implements FavoritesMvpView {

    @Inject FavoritesPresenter mFavoritesPresenter;
    private QuotesAdapter mQuotesAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.favorites_empty_textView) TextView mEmptyTextView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;

    private MultiSelector mMultiSelector = new MultiSelector();
    private boolean isActionModeStarted = false;
    private ActionMode mActionMode;

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
        getSupportActionBar().setTitle(R.string.favorites);

        mQuotesAdapter = new QuotesAdapter(this);
        mRecyclerView.setAdapter(mQuotesAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

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
    public void checkItemsCount() {
        if (mQuotesAdapter.getItemCount() == 0) {
            showFavoritesEmpty();
        }
    }

    @Override
    public void showError() {
        Log.e("Error accessing DB", "Error accessing DB");
    }


    private ActionMode.Callback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            isActionModeStarted = true;
            getMenuInflater().inflate(R.menu.favorites_menu, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_item_delete_favorites:
                    // Need to finish the action mode before doing the following,
                    // not after. No idea why, but it crashes.
                    isActionModeStarted = false;
                    actionMode.finish();

                    for (int position = mQuotesAdapter.getItemCount() - 1; position >= 0; position--) {
                        if (mMultiSelector.isSelected(position)) {
                            Quote quote = mQuotesAdapter.getItem(position);

                            mQuotesAdapter.delete(quote);
                            mQuotesAdapter.notifyItemRemoved(position);

                            mFavoritesPresenter.deleteQuoteFromFavorites(quote);
                        }
                    }

                    mMultiSelector.clearSelections();
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isActionModeStarted) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                mActionMode.finish();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

        private List<Quote> mQuotes;
        private Context mContext;

        protected List<Quote> mSelectedQuotes;

        //@Inject
        public QuotesAdapter(@ActivityContext Context context) {
            mSelectedQuotes = new ArrayList<>();
            mQuotes = new ArrayList<>();
            mContext = context;
        }

        public void setContext(Context context) {
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

            mMultiSelector.bindHolder(holder, position, -1);

            if (mSelectedQuotes.contains(mQuotes.get(position))) {
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGray));
            } else {
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
            }
        }

        public void setQuotes(List<Quote> quotes) {
            mQuotes = quotes;
        }

        public List<Quote> getQuotes() { return mQuotes; }

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

        class QuotesViewHolder extends MultiSelectorBindingHolder implements View.OnClickListener, View.OnLongClickListener {

            @BindView(R.id.quote_item_textview) TextView mQuoteTextView;
            @BindView(R.id.author_item_textview) TextView mAuthorTextView;

            private boolean isSelected;

            public QuotesViewHolder(View itemView) {
                super(itemView, mMultiSelector);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);


                isSelected = false;
            }

            @Override
            public void onClick(View v) {
                if (!mMultiSelector.tapSelection(this)) {
                    Intent intent = MainActivity.newIntent(mContext,
                            new Quote(mQuoteTextView.getText().toString(),
                                    mAuthorTextView.getText().toString()));
                    mContext.startActivity(intent);
                }
            }

            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) mContext;
                mActionMode = activity.startSupportActionMode(mDeleteMode);
                mMultiSelector.setSelected(this, true);
                return true;
            }

            private void refreshState() {
                if (!isSelected) {
                    this.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGray));
                } else {
                    this.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
                }

            }

            @Override
            public void setSelectable(boolean selectable) {
                if (isSelected != selectable) {
                    refreshState();
                }
                isSelected = selectable;
            }

            @Override
            public boolean isSelectable() {
                return isSelected;
            }

            @Override
            public void setActivated(boolean activated) {

            }

            @Override
            public boolean isActivated() {
                return false;
            }
        }
    }
}
