/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.viewcontrollers.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.leonardis.ormliteexample.R;
import com.example.leonardis.ormliteexample.adapters.ConversationAdapter;
import com.example.leonardis.ormliteexample.application.OrmliteExampleApplication;
import com.example.leonardis.ormliteexample.model.Contact;
import com.example.leonardis.ormliteexample.model.Conversation;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ContactDao;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ConversationDao;
import com.example.leonardis.ormliteexample.services.CopyDatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows a list of conversations that are available
 */
public class ConversationActivity extends ListActivity {
    public static final String TAG = ConversationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity);

        //
        //Listeners
        //
        Button addButton = (Button) findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationDao conversationDao = OrmliteExampleApplication.getDatabase().getConversationDao();
                ContactDao contactDao = OrmliteExampleApplication.getDatabase().getContactDao();
                try {
                    long lastId = conversationDao.countOf();

                    List<Contact> contactList = contactDao.queryForAll();

                    //create group
                    List<Contact> groupMembers = new ArrayList<>();
                    groupMembers.add(contactList.get(0));
                    groupMembers.add(contactList.get(1));

                    //create conversation and save to database
                    String conversationId = "ABC123C" + (lastId + 1);
                    Conversation conversation = new Conversation(conversationId, groupMembers);
                    conversationDao.create(conversation);

                    //add conversation to list
                    ((ConversationAdapter)getListAdapter()).add(conversation);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //
        //Create Adapter
        //
        ConversationDao conversationDao = OrmliteExampleApplication.getDatabase().getConversationDao();
        try {
            ConversationAdapter conversationAdapter = new ConversationAdapter(this, 0, conversationDao.queryForAll() );
            setListAdapter(conversationAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Commented out - example of delete cascade from contact
//        if (true) {
//            try {
//                contactDao.delete(doug);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onListItemClick(android.widget.ListView listView, android.view.View view, int position, long id) {
        Conversation conversation = (Conversation) getListAdapter().getItem(position);
        MessageActivity.startActivity(this, conversation.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_copy_database:
                Intent intent= new Intent(ConversationActivity.this, CopyDatabaseService.class);
                startService(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
