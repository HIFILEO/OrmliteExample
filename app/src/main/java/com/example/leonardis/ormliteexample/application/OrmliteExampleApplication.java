/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.application;

import android.app.Application;
import android.util.Log;

import com.example.leonardis.ormliteexample.model.Contact;
import com.example.leonardis.ormliteexample.model.Conversation;
import com.example.leonardis.ormliteexample.model.ConversationGroup;
import com.example.leonardis.ormliteexample.model.Message;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ContactDao;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ConversationDao;
import com.example.leonardis.ormliteexample.model.helpers.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the single application class
 */
public class OrmliteExampleApplication extends Application {

    public static final String TAG = OrmliteExampleApplication.class.getSimpleName();
    private static OrmliteExampleApplication application;
    private static DatabaseHelper databaseHelper;

    /**
     * Setup logging information and hockey app crash manager.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        //
        //Create Database
        //
        databaseHelper = DatabaseHelper.create(OrmliteExampleApplication.this);
        databaseHelper.getWritableDatabase();

        //
        //Clear Tables on every start
        //
        try {
            TableUtils.clearTable(OrmliteExampleApplication.getDatabase().getConnectionSource(), ConversationGroup.class);
            TableUtils.clearTable(OrmliteExampleApplication.getDatabase().getConnectionSource(), Contact.class);
            TableUtils.clearTable(OrmliteExampleApplication.getDatabase().getConnectionSource(), Conversation.class);
            TableUtils.clearTable(OrmliteExampleApplication.getDatabase().getConnectionSource(), Message.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        //Create default data
        //

        //Create 2 contacts and insert them into database
        Contact dan = new Contact("A1981", "Dan", "Leo");
        Contact doug = new Contact("A1982","Doug", "Funny");
        ContactDao contactDao = OrmliteExampleApplication.getDatabase().getContactDao();
        try {
            contactDao.createIfNotExists(dan);
            contactDao.createIfNotExists(doug);
        } catch (SQLException e) {
            Log.e(TAG, "Error: ", e);
        }

        //Create 1 conversation between the two contacts and insert it into database.
        List<Contact> groupMembers = new ArrayList<>();
        groupMembers.add(dan);
        groupMembers.add(doug);

        Conversation conversation = new Conversation("ABC123C1", groupMembers);
        ConversationDao conversationDao = OrmliteExampleApplication.getDatabase().getConversationDao();

        try {
            conversationDao.create(conversation);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //create 2 messages for the above conversation
        Message message1 = new Message("ABC1", conversation, dan);
        Message message2 = new Message("ABC2", conversation, doug);
        Dao<Message, Integer> messageDao = OrmliteExampleApplication.getDatabase().getMessageDao();
        try {
            messageDao.create(message1);
            messageDao.create(message2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ease of access method to return the application class
     * @return
     */
    public static OrmliteExampleApplication getInstance() {
        return application;
    }

    /***
     * Get database helper for application. If null, we attempt to open.
     * @return db - database helper or null
     */
    public static DatabaseHelper getDatabase() {
        if (databaseHelper == null) {
            Log.w(TAG, "Database was null, create failed!");
            databaseHelper = DatabaseHelper.create(application);
        }

        return databaseHelper;
    }

    /***
     * Close Database and null out the handle.
     */
    public static void closeDatabase() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }

        databaseHelper = null;
    }
}
