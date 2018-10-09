package com.example.indus.moviecollection.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.indus.moviecollection.R;
import com.example.indus.moviecollection.model.TheMovie;
import com.example.indus.moviecollection.network.IOnDataLoaded;
import com.example.indus.moviecollection.network.Network;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText searchView;
    private ImageView searchButton;
    private ImageView clearSearchButton;
    private TextWatcher searchWatcher;

    private FavoritesFragment favoritesFragment;
    private FrameLayout searchFragmentContainer;
    private FrameLayout favoritesFragmentContainer;

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiView();

        clearSearchButton.setVisibility(View.INVISIBLE);
        searchFragmentContainer.setVisibility(View.INVISIBLE);
        searchWatcher = initTextWatcher();
        searchView.addTextChangedListener(searchWatcher);
        clearSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragmentContainer.setVisibility(View.INVISIBLE);
                searchView.setText("");
                searchFragment.clear();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchView.getText().toString();
                if (TextUtils.isEmpty(search)) {
                    Snackbar.make(view, "Search error", Snackbar.LENGTH_SHORT);

                } else {
                    internetRequest(search);
                }

            }
        });

    }

    private void intiView() {
        searchView = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.icon_search);
        clearSearchButton = findViewById(R.id.icon_clear_search);
        searchFragmentContainer = findViewById(R.id.fragment_search_container);
        favoritesFragmentContainer = findViewById(R.id.favorites_container);
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        favoritesFragment = (FavoritesFragment) getSupportFragmentManager().findFragmentById(R.id.favorites_fragment);
    }

    private void showClearButton(boolean isVisible) {
        clearSearchButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void internetRequest(String search){

        Network.getInstance().searchMovie(search, new IOnDataLoaded<List<TheMovie>>() {
            @Override
            public void onNewDataLoaded(List<TheMovie> data) {
                Log.e("onNewData","data size is: " + data.size());
                searchFragment.setData(data);
            }


            @Override
            public void onErrorDataLoaded() {
                Snackbar.make(favoritesFragmentContainer, "Search Error", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private TextWatcher initTextWatcher(){

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length() > 1 && (editable.length()%2) == 0 ){
                    showClearButton(true);
                    searchFragmentContainer.setVisibility(View.VISIBLE);
                    favoritesFragmentContainer.setVisibility(View.INVISIBLE);
                   internetRequest(editable.toString());
                }else {
                    searchFragmentContainer.setVisibility(View.INVISIBLE);
                    favoritesFragmentContainer.setVisibility(View.VISIBLE);
                    showClearButton(false);
                }
            }
        };
    }

}
