package com.subhrajyoti.hubblertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.subhrajyoti.hubblertest.adapter.RecyclerViewAdapter;
import com.subhrajyoti.hubblertest.data.MyPair;
import com.subhrajyoti.hubblertest.data.RecordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.reports_recyclerView)
    RecyclerView reportRecyclerView;
    @BindView(R.id.report_count)
    TextView totalReportTextView;

    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<MyPair> myPairArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            myPairArrayList = savedInstanceState.getParcelableArrayList("Pairs");
            totalReportTextView.setText("Total Reports: " + myPairArrayList.size());

        }
        else {
            myPairArrayList = new ArrayList<>();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(myPairArrayList);

        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportRecyclerView.setAdapter(recyclerViewAdapter);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        // Switch case is used since there can be more main_item later on
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, CustomActivity.class);
                intent.putExtra("fileName", "layout.json");
                startActivityForResult(intent, 1);
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Pairs", myPairArrayList);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String str1 = data.getStringExtra("FirstField");
                String str2 = "";
                if (data.hasExtra("SecondField"))
                    str2 =  data.getStringExtra("SecondField");

                recyclerViewAdapter.addItem(new MyPair(str1, str2));

                totalReportTextView.setText("Total Reports: " + recyclerViewAdapter.getItemCount());
            }
        }
    }
}
