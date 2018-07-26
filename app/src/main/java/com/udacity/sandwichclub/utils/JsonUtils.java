package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    /**
     * Extracts sandwich details from json and returns a sandwich object
     *
     * @param json string
     * @return Sandwich
     * @throws JSONException e
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        //The root JSON object
        JSONObject rootObject = new JSONObject(json);

        //Keys in the json whose values will be extracted
        final String NAME = "name";

        final String MAIN_NAME = "mainName";

        final String ALSO_KNOWN_AS = "alsoKnownAs";

        final String PLACE_OF_ORIGIN = "placeOfOrigin";

        final String DESCRIPTION = "description";

        final String IMAGE = "image";

        final String INGREDIENTS = "ingredients";

        //Extract the values of each detail of the sandwich
        JSONObject nameObject = rootObject.optJSONObject(NAME);

        String mainName = nameObject.optString(MAIN_NAME);

        String placeOfOrigin = rootObject.optString(PLACE_OF_ORIGIN);

        String description = rootObject.optString(DESCRIPTION);

        String image = rootObject.optString(IMAGE);

        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientsArray = rootObject.optJSONArray(INGREDIENTS);
        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        List<String> alsoKnownAs = new ArrayList<>();
        JSONArray alsoKnownAsArray = nameObject.optJSONArray(ALSO_KNOWN_AS);
        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                description, image, ingredients);
    }
}
