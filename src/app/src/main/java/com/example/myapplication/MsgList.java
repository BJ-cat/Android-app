package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.data.model.NormalUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MsgList extends Fragment {
    private ArrayList<String> msgList;
    private NormalUser usr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_msg_list, container, false);

        //Get user init msg list
        NormalUser usr = (NormalUser) requireActivity().getIntent().getExtras().getSerializable("USR");
        BufferedReader bufferedReader = null;
        msgList = new ArrayList<>();
        Toast.makeText(requireContext(), "jump2msglist, uid " + usr.getId(), Toast.LENGTH_SHORT).show();

        try {
            int id = usr.getId();
            //Create a instance of buffered reader
            bufferedReader = new BufferedReader(new InputStreamReader(requireContext().getAssets().open("message_list.csv"), StandardCharsets.UTF_8));
            // Read one line at a time
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (id == Integer.parseInt(tokens[0].trim())) {
                    for (int i = 1; i < tokens.length; i++) {
                        msgList.add(tokens[i].trim());
                    }
                    Toast.makeText(requireContext(), "Import msg list data successfully!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Import msg list data error!", Toast.LENGTH_SHORT).show();
        } finally {
            // Close buffered reader
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Error closing the reader!", Toast.LENGTH_SHORT).show();
            }
        }

        msgList.add("111");
        msgList.add("222");

        // Set adapter to msg list
        ListView msg_list = view.findViewById(R.id.msg_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                msgList
        );
        msg_list.setAdapter(adapter);

        // Set click listener of list view
        msg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uname = msgList.get(position);
                if (uname == null || uname.isEmpty()) {
                    Toast.makeText(requireContext(), "Uname is invalid!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent jump2msg = new Intent(requireContext(), MessageActivity.class);
                    jump2msg.putExtra("receiver", uname);
                    jump2msg.putExtra("USR", usr);
                    startActivity(jump2msg);
                }
            }
        });

        return view;
    }
}