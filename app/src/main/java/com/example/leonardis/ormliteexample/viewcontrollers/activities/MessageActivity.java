/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.viewcontrollers.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.leonardis.ormliteexample.R;
import com.example.leonardis.ormliteexample.adapters.MessageAdapter;
import com.example.leonardis.ormliteexample.application.OrmliteExampleApplication;
import com.example.leonardis.ormliteexample.model.Conversation;
import com.example.leonardis.ormliteexample.model.Message;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ConversationDao;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Shows a list of messages given the conversation ID sent in.
 */
public class MessageActivity extends ListActivity {
    public static final String TAG = MessageActivity.class.getSimpleName();
    private static final String CONVERSATION_ID = "Conversation_ID";
    private long conversationId;
    private Conversation conversation;

    /**
     * Start Activity
     * @param context
     * @param conversationId
     */
    public static void startActivity(Context context, long conversationId) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(CONVERSATION_ID, conversationId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        //Load conversation
        conversationId = getIntent().getExtras().getLong(MessageActivity.CONVERSATION_ID);
        ConversationDao conversationDao = OrmliteExampleApplication.getDatabase().getConversationDao();

        try {
            conversation = conversationDao.queryForId((int) conversationId);
        } catch (SQLException e) {
            Log.e(TAG, " Issue loading conversation.", e);
            finish();
        }

        //
        //Listener
        //
        Button addButton = (Button) findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dao<Message, Integer> messageDao = OrmliteExampleApplication.getDatabase().getMessageDao();

                try {
                    long lastId = messageDao.countOf();
                    String messageId = "ABC" + (lastId + 1);

                    //create message and save to database
                    Message message = new Message(messageId, conversation, conversation.getGroupdMembers().get(0));//Sender is the 1st entry in the list
                    messageDao.create(message);

                    //add message to list
                    ((MessageAdapter)getListAdapter()).add(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //
        //Create adapter
        //
        /*
        Note - example of how the ONE to MANY is accessed
         */
        ForeignCollection<Message> messages = conversation.getMessages();
        MessageAdapter messageAdapter = new MessageAdapter(this, 0, new ArrayList<>(messages));
        setListAdapter(messageAdapter);
    }
}
