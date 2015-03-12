/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.leonardis.ormliteexample.model.Contact;
import com.example.leonardis.ormliteexample.model.Conversation;
import com.example.leonardis.ormliteexample.model.ConversationGroup;
import com.example.leonardis.ormliteexample.model.Message;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ContactDao;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ConversationDao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Creates the databases by hooking into Ormlite. Also holds convenience methods for accessing DAOs
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "OrmliteExample.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    /**
     * There was a compile issue with not having the exact constructor as ormlite. Therefore I created this ease of use method to just create the DatabaseHelper.
     * @param context
     */
    public static DatabaseHelper create(Context context) {
        return new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Constructor
     */
    private DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Conversation.class);
            TableUtils.createTable(connectionSource, Contact.class);
            TableUtils.createTable(connectionSource, ConversationGroup.class);
            TableUtils.createTable(connectionSource, Message.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error creating tables", e);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase database) {
        Log.d(TAG,"onConfigure");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            database.setForeignKeyConstraintsEnabled(true);
        } else {
            database.execSQL("PRAGMA foreign_keys = ON");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        /*No upgrades*/
    }

    /**
     * Convenience
     */
    public ContactDao getContactDao() {
        try {
            return DaoManager.createDao(getConnectionSource(), Contact.class);
        } catch (SQLException e) {
            Log.e(TAG, "Failure to get dao", e);
        }

        return null;
    }

    /**
     * Convenience
     */
    public ConversationDao getConversationDao() {
        try {
            return DaoManager.createDao(getConnectionSource(), Conversation.class);
        } catch (SQLException e) {
            Log.e(TAG, "Failure to get dao", e);
        }

        return null;
    }

    /**
     * Convenience
     */
    public Dao<ConversationGroup, Integer> getConversationGroupDao() {
        try {
            return DaoManager.createDao(getConnectionSource(), ConversationGroup.class);
        } catch (SQLException e) {
            Log.e(TAG, "Failure to get dao", e);
        }

        return null;
    }

    /**
     * Convenience
     */
    public Dao<Message, Integer> getMessageDao() {
        try {
            return DaoManager.createDao(getConnectionSource(), Message.class);
        } catch (SQLException e) {
            Log.e(TAG, "Failure to get dao", e);
        }

        return null;
    }
}
