package com.udacity.sandwichclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    //UI Elements
    private TextView mAlsoKnownAs;
    private TextView mPlaceOfOrigin;
    private TextView mIngredients;
    private TextView mDescription;
    private View mAlsoKnownAsView;
    private View mOriginView;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSandwich = null;

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);
        mAlsoKnownAsView = findViewById(R.id.also_known_as_view);
        mOriginView = findViewById(R.id.origin_view);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            try {
                mSandwich = JsonUtils.parseSandwichJson(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mSandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }
        }

        Picasso.with(this)
                .load(mSandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        //Set activity title to sandwich name
        setTitle(mSandwich.getMainName());

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Method to display the details of the selected sandwich
    @SuppressLint("StringFormatInvalid")
    private void populateUI() {

        mDescription.setText(mSandwich.getDescription());

        if (mSandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginView.setVisibility(View.GONE);
        } else {
            mPlaceOfOrigin.setText(mSandwich.getPlaceOfOrigin());
        }

        //Convert alsoKnownAs array to string
        StringBuilder akaBuilder = new StringBuilder();
        List<String> alsoKnownAs = mSandwich.getAlsoKnownAs();
        for (String word : alsoKnownAs) {
            if (alsoKnownAs.indexOf(word) == alsoKnownAs.size() - 1) {
                akaBuilder.append(word);
            } else {
                akaBuilder.append(word);
                akaBuilder.append(", ");
            }
        }

        if (mSandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsView.setVisibility(View.GONE);
        } else {
            mAlsoKnownAs.setText(akaBuilder);
        }

        //Convert ingredients array to string
        StringBuilder ingredientsBuilder = new StringBuilder();
        List<String> ingredients = mSandwich.getIngredients();
        for (String ingredient : ingredients) {
            if (ingredients.indexOf(ingredient) == ingredients.size() - 1) {
                ingredientsBuilder.append(ingredient);
                ingredientsBuilder.append(".");
            } else {
                ingredientsBuilder.append(ingredient);
                ingredientsBuilder.append(", ");
            }
        }

        mIngredients.setText(ingredientsBuilder);

    }
}
