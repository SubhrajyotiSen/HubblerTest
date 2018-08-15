package com.subhrajyoti.hubblertest.data;

import java.util.ArrayList;

public class RecordModel {

    private ArrayList<FieldSchema> fieldSchemaArrayList;


    public RecordModel(ArrayList<FieldSchema> fieldSchemaArrayList) {
        this.fieldSchemaArrayList = fieldSchemaArrayList;
    }

    public ArrayList<FieldSchema> getFieldSchemaArrayList() {
        return fieldSchemaArrayList;
    }

}
