package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BaseActivity;
import com.development.vvoitsekh.favoritequotes.utils.AlertDialogHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends BaseActivity implements FavoritesMvpView, AlertDialogHelper.AlertDialogListener {

    @Inject FavoritesPresenter mFavoritesPresenter;
    @Inject QuotesAdapter mQuotesAdapter;
    @Inject AlertDialogHelper mAlertDialogHelper;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.favorites_empty_textView) TextView mEmptyTextView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;

    boolean isMultiSelect = false;
    ActionMode mActionMode;

    ArrayList<Quote> multiselect_list = new ArrayList<>();

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


        //mQuotesAdapter.setContext(this);
        mRecyclerView.setAdapter(mQuotesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(FavoritesActivity.this,
                        mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (isMultiSelect)
                                    multi_select(position);
                                else
                                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                if (!isMultiSelect) {
                                    multiselect_list = new ArrayList<>();
                                    isMultiSelect = true;

                                    if (mActionMode == null) {
                                        getSupportActionBar().hide();
                                        mActionMode = startSupportActionMode(mActionModeCallback);
                                    }
                                }

                                multi_select(position);

                            }
                        }));

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
    public void showError() {
        Log.e("Error accessing DB", "Error accessing DB");
    }


    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(mQuotesAdapter.getItem(position)))
                multiselect_list.remove(mQuotesAdapter.getItem(position));
            else
                multiselect_list.add(mQuotesAdapter.getItem(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");

            mQuotesAdapter.mSelectedQuotes = multiselect_list;
            mQuotesAdapter.notifyDataSetChanged();

        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    mAlertDialogHelper.showAlertDialog("","Delete Contact","DELETE","CANCEL",1,false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<>();

            mQuotesAdapter.mSelectedQuotes = multiselect_list;
            mQuotesAdapter.notifyDataSetChanged();

            getSupportActionBar().show();
        }
    };

    @Override
    public void onPositiveClick(int from) {
        if(from==1)
        {
            if(multiselect_list.size()>0)
            {
                for(int i=0;i<multiselect_list.size();i++) {
                    Quote quote = multiselect_list.get(i);

                    mQuotesAdapter.delete(quote);
                    mFavoritesPresenter.deleteQuoteFromFavorites(quote);
                }

                mQuotesAdapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }

//                Intent intent = newIntent(this);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
                List<Quote> quotes = mQuotesAdapter.getQuotes();
                mQuotesAdapter = new QuotesAdapter(this);
                mQuotesAdapter.setQuotes(quotes);

                mRecyclerView.setAdapter(mQuotesAdapter);
                mRecyclerView.refreshDrawableState();
                mRecyclerView.invalidate();
                mRecyclerView.postInvalidate();
                //mRecyclerView.swapAdapter(mQuotesAdapter, true);
            }
        }
        else if(from==2)
        {
            if (mActionMode != null) {
                mActionMode.finish();
            }

//            SampleModel mSample = new SampleModel("Name"+user_list.size(),"Designation"+user_list.size());
//            user_list.add(mSample);
//            multiSelectAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
