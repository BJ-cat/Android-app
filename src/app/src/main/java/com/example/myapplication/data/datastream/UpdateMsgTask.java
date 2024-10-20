package com.example.myapplication.data.datastream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateMsgTask {
    private ArrayList<String> msgs;

    public UpdateMsgTask() {
        this.msgs = new ArrayList<>();
    }

    public ArrayList<String> getMsgStream() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("../../../../../../assets/msg_stream.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                msgs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.msgs;
    }
}

