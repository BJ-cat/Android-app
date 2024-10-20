package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.data.model.Message;
import com.example.myapplication.data.model.NormalUser;
import com.google.android.material.button.MaterialButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private ArrayList<String> msgList;
    private final int INTERVAL = 10000;
    private Handler handler;
    private Runnable runnable;
    private int streamCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        // Get user
        NormalUser usr = (NormalUser) getIntent().getExtras().getSerializable("USR");
        // Get sender and receiver
        int senderId = usr.getId();
        // Todo: Need pass right receiver id
        int receiverId = getIntent().getIntExtra("receiver", -1);
        String receiver = getIntent().getStringExtra("receiver");
        Toast.makeText(getApplicationContext(), "Send msg 2 " + receiver, Toast.LENGTH_SHORT).show();

        //Init msg li
        msgList = new ArrayList<>();

        // Set adapter to msg list
        ListView msg_li = (ListView) findViewById(R.id.msg_li);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                msgList
        );
        msg_li.setAdapter(adapter);

        // Send msg thru send btn
        MaterialButton send_btn = (MaterialButton) findViewById(R.id.send_btn);
        EditText msg_text = (EditText) findViewById(R.id.msg_text);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_content = msg_text.getText().toString();
                // Check if text empty
                if (msg_content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Msg is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Message msg = new Message(senderId, receiverId, msg_content);
                    msgList.add(msg.getMsg(usr.getUsername()));
                    msg_text.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Change title
        TextView title = (TextView) findViewById(R.id.message_title);
        title.setText(receiver);

        // Update msg
        //UpdateMsgTask task = new UpdateMsgTask();
        ArrayList<String> msgStream = loadDataStream();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message(receiverId, senderId, msgStream.get(streamCount));
                msgList.add(msg.getMsg(receiver));
                adapter.notifyDataSetChanged();
                streamCount++;
                Toast.makeText(getApplicationContext(), "Receive a msg", Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, INTERVAL);
            }
        };

        // Run Runnable
        handler.postDelayed(runnable, INTERVAL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private ArrayList<String> loadDataStream() {
        ArrayList<String> msgs = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("msg_stream.csv"), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                msgs.add(line);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Read data stream error!", Toast.LENGTH_SHORT).show();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Close reader error!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return msgs;
    }
}