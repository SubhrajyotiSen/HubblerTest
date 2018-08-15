package com.subhrajyoti.hubblertest.util;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.subhrajyoti.hubblertest.data.FieldSchema;
import com.subhrajyoti.hubblertest.data.RecordModel;

import java.util.ArrayList;

public class CustomViewHandler {

    public static View recordModelToView(RecordModel recordModel, Context context) {

        ScrollView scrollView = new ScrollView(context);
        ScrollView.LayoutParams scrollViewLayoutParams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollViewLayoutParams);
        scrollView.setPadding(20, 20, 20, 20);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        scrollView.addView(linearLayout);

        ArrayList<FieldSchema> fieldSchemaArrayList = recordModel.getFieldSchemaArrayList();

        for (int i = 0; i < fieldSchemaArrayList.size(); i++) {
            FieldSchema fieldSchema = fieldSchemaArrayList.get(i);

            View view = handleView(fieldSchema, context);
            // if view is visible, only then add it
            if (view.getVisibility() == View.VISIBLE) {
                String fieldName = fieldSchema.getFieldName();

                // Convert the starting of the string to uppercase
                fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length());

                TextView textView = new TextView(context);
                textView.setText(fieldName);
                textView.setTextSize(20);
                textView.setPadding(0, 20, 0, 0);

                linearLayout.addView(textView);
                linearLayout.addView(view);
            }
        }

        return scrollView;
    }

    private static View handleView(FieldSchema fieldSchema, Context context) {
        String fieldType = fieldSchema.getFieldType();
        String fieldName = fieldSchema.getFieldName();
        View view;

        switch (fieldType) {
            case "text":
                view = new EditText(context);
                break;
            case "dropdown":
                Spinner spinner = new Spinner(context);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, fieldSchema.getFieldOptions());
                spinner.setAdapter(adapter);
                view = spinner;
                break;
            case "number":
                EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                view = editText;
                break;
            case "multiline":
                editText = new EditText(context);
                editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                view = editText;
                break;
            default:
                // create an invisible view if an unknown view is encountered
                view = new View(context);
                view.setVisibility(View.INVISIBLE);
        }

        view.setTag(fieldName);
        return view;
    }
}
