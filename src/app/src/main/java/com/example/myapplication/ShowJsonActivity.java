package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create the show data activity for searching
 * Use tokenizer to deal with the input got from Search.class, i.e. the user input,
 * and parser to search the data in json file to show the result.
 * @author Jia Hou, Qifeng Zheng
 */
public class ShowJsonActivity extends AppCompatActivity implements CarListAdapter.onItemClickListener {
    List<CarInfo> carInfoList = new ArrayList<>();
    List<CarInfo> carInfoList2 = new ArrayList<>();
    Context context;
    CarListAdapter mCarListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_json);
        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        initialize mCarListAdapter
        mCarListAdapter = new CarListAdapter();
//        set adapter
        recyclerView.setAdapter(mCarListAdapter);

        String input = getIntent().getStringExtra("input");
        Tokenizer tokenizer = new Tokenizer(input);
        carInfoList = JsonReader.loadJSONFromAsset(context, "mental_health_resources.json");

        Parser parser = new Parser(tokenizer, carInfoList);
        //make tokenizer begin its work: make the current token points to the first word
        tokenizer.next();
        parser.parseContent();
        //the result
        carInfoList2 = parser.getCarInfoList();
        mCarListAdapter.setCarInfoList(carInfoList2);
        mCarListAdapter.setmOnItemClickListener(this);
    }

    @Override
    public void OnClick(CarInfo carInfo, int position) {
        String url=carInfo.link;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));


        if (intent.resolveActivity(getPackageManager()) != null) {

            startActivity(intent);
        } else {

            Toast.makeText(this, "Unable to process this request", Toast.LENGTH_SHORT).show();
        }
    }
}
