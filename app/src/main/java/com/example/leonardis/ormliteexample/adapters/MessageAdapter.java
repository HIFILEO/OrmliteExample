/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonardis.ormliteexample.R;
import com.example.leonardis.ormliteexample.application.OrmliteExampleApplication;
import com.example.leonardis.ormliteexample.model.Message;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Adapter that takes a list of message objects and displays them to the listview.
 */
public class MessageAdapter extends ArrayAdapter<Message> implements View.OnClickListener {


    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_cell, null);
            convertView.findViewById(R.id.DeleteImageView).setOnClickListener(this);
        }

        //Get conversation
        Message message = getItem(position);

        //Update Text View
        TextView textView = (TextView) convertView.findViewById(R.id.InfoTextView);
        textView.setText("Message ID: " + message.getMessageId());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.DeleteImageView);
        imageView.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        //get conversation
        Message Message = getItem((Integer) view.getTag());

        //remove conversation from database
        Dao<Message, Integer> messageDao = OrmliteExampleApplication.getDatabase().getMessageDao();
        try {
            messageDao.delete(Message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //remove from adapter
        remove(Message);
    }
}
