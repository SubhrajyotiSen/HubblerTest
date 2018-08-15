package com.subhrajyoti.hubblertest.util;

import android.content.Context;

import com.subhrajyoti.hubblertest.data.FieldSchema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JSONUtils {

    public static ArrayList<FieldSchema> parse(String json) throws JSONException {

        ArrayList<FieldSchema> fieldSchemaArrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            fieldSchemaArrayList.add(new FieldSchema(jsonObject));
        }

        return fieldSchemaArrayList;

    }


    public static String loadJSONFromAsset(String JSONFileName, Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open(JSONFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }



}
