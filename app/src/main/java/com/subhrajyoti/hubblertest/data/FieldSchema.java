package com.subhrajyoti.hubblertest.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FieldSchema {

    private String fieldName;
    private String fieldType;
    private List<String> fieldOptions = new ArrayList<>();
    private int fieldMinValue;
    private int fieldMaxValue;
    private boolean fieldRequired;


    public FieldSchema(JSONObject jsonObject) throws JSONException {
        fieldName = jsonObject.optString("field-name", null);
        fieldType = jsonObject.optString("type", null);
        fieldMinValue = jsonObject.optInt("min", Integer.MIN_VALUE);
        fieldMaxValue = jsonObject.optInt("max", Integer.MAX_VALUE);
        fieldRequired = jsonObject.optBoolean("required", false);

        if (jsonObject.has("options")) {
            JSONArray jsonArray = jsonObject.getJSONArray("options");
            for(int i =0; i < jsonArray.length(); i++) {
                fieldOptions.add(jsonArray.getString(i));
            }
        }

    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public List<String> getFieldOptions() {
        return fieldOptions;
    }

    public int getFieldMinValue() {
        return fieldMinValue;
    }

    public int getFieldMaxValue() {
        return fieldMaxValue;
    }

    public boolean isFieldRequired() {
        return fieldRequired;
    }

}
