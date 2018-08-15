package com.subhrajyoti.hubblertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.subhrajyoti.hubblertest.data.FieldSchema;
import com.subhrajyoti.hubblertest.data.RecordModel;
import com.subhrajyoti.hubblertest.util.CustomViewHandler;
import com.subhrajyoti.hubblertest.util.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {

    private View view = null;
    private RecordModel recordModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName = getIntent().getStringExtra("fileName");
        String json= JSONUtils.loadJSONFromAsset(fileName, this);
        if (json == null)
            setContentView(R.layout.invalid_layout);
        else {

            try {
                recordModel = new RecordModel(JSONUtils.parse(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(recordModel != null) {
                view = CustomViewHandler.recordModelToView(recordModel, this);
                setContentView(view);
            }
            else
                setContentView(R.layout.invalid_layout);
        }

    }

    private boolean validate() {
        ArrayList<FieldSchema> fieldSchemaArrayList = recordModel.getFieldSchemaArrayList();
        for(int i = 0; i < fieldSchemaArrayList.size(); i++) {
            FieldSchema fieldSchema = fieldSchemaArrayList.get(i);
            View tempView = view.findViewWithTag(fieldSchema.getFieldName());
            switch (fieldSchema.getFieldType()) {
                case "text":
                    String text = ((EditText) tempView).getText().toString();
                    if (fieldSchema.isFieldRequired())
                        if (text.length() == 0) {
                            Toast.makeText(this, fieldSchema.getFieldName() + "cannot be empty", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    break;
                case "number":
                    String value = ((EditText) tempView).getText().toString();
                    int number = value.equals("") ? 0 : Integer.valueOf(value);
                    if (fieldSchema.isFieldRequired())
                        if (!(number >= fieldSchema.getFieldMinValue() && number <= fieldSchema.getFieldMaxValue())) {
                            Toast.makeText(this,
                                    fieldSchema.getFieldName() + " value has to be between " +
                                            fieldSchema.getFieldMinValue() + " and " + fieldSchema.getFieldMaxValue(),
                                    Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    break;
                case "multiline":
                    text = ((EditText) tempView).getText().toString();
                    if (fieldSchema.isFieldRequired())
                        if (text.length() == 0) {
                            Toast.makeText(this, fieldSchemaArrayList.get(i).getFieldName() + "cannot be empty", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    break;
            }
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (view != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.custom_item, menu);
            return super.onCreateOptionsMenu(menu);
        }
        else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        // Switch case is used since there can be more main_item later on
        switch (item.getItemId()) {
            case R.id.done:
                //start activity here
                if(view != null)
                    if (validate()) {
                        Intent intent = new Intent();
                        intent.putExtra("FirstField",
                                recordModel.getFieldSchemaArrayList().get(0).getFieldName() + ": "
                                        + getViewValue(recordModel.getFieldSchemaArrayList().get(0)));
                        if (recordModel.getFieldSchemaArrayList().size()>1) {
                            intent.putExtra("SecondField", recordModel.getFieldSchemaArrayList().get(1).getFieldName() + ": "
                                    + getViewValue(recordModel.getFieldSchemaArrayList().get(1)));
                        }
                        setResult(RESULT_OK, intent);

                        JSONObject jsonObject = new JSONObject();
                        ArrayList<FieldSchema> fieldSchemaArrayList = recordModel.getFieldSchemaArrayList();
                        for(int i = 0; i < fieldSchemaArrayList.size(); i++) {
                            try {
                                jsonObject.put(fieldSchemaArrayList.get(i).getFieldName(),getViewValue(fieldSchemaArrayList.get(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("JSON of input data", jsonObject.toString());
                        finish();
                    }
                break;
        }
        return true;
    }

    public String getViewValue(FieldSchema fieldSchema) {
        View tempView = view.findViewWithTag(fieldSchema.getFieldName());
        if (fieldSchema.getFieldType().equals("dropdown")) {
            return ((Spinner) tempView).getSelectedItem().toString();

        }
        else {
            return ((EditText) tempView).getText().toString();
        }

    }
}
