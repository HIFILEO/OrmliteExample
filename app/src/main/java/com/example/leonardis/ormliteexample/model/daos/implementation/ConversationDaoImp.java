/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model.daos.implementation;

import com.example.leonardis.ormliteexample.application.OrmliteExampleApplication;
import com.example.leonardis.ormliteexample.model.Contact;
import com.example.leonardis.ormliteexample.model.Conversation;
import com.example.leonardis.ormliteexample.model.ConversationGroup;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ConversationDao;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


/**
 * The implementation of data access object. Here we have an example of Overriding the create method to fulfill the 'MANY to MANY' relationship of a conversation.
 */
public class ConversationDaoImp extends BaseDaoImpl<Conversation, Integer> implements ConversationDao {

    public ConversationDaoImp(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Conversation.class);
    }

    /**
     * We must override the create so we update all tables to enforce the many to many
     */
    @Override
    public int create (Conversation conversation) throws SQLException {
        /*
         * Note - In a many to many relationship, you must enforce it using a 3rd table. Therefore, someone at some point needs to fill in that table. You can do it manually, or you can override the
          * create and other methods for a conversation. Overriding methods allows you to enforce it behind the scenes. Here is that example of how every conversation has a group of contacts that we can update in the
          * ConversationGroup table.
          * Example
          * https://github.com/j256/ormlite-jdbc/blob/master/src/test/java/com/j256/ormlite/examples/manytomany/ManyToManyMain.java
         */

        //first insert the conversation into sql
        int number = super.create(conversation);

        //For every contact in the group, create the link to contact table.
        Dao<ConversationGroup, Integer> conversationGroupDao = OrmliteExampleApplication.getDatabase().getConversationGroupDao();
        for (Contact contact : conversation.getGroupdMembers()) {
            ConversationGroup conversationGroup = new ConversationGroup(conversation, contact);
            conversationGroupDao.createIfNotExists(conversationGroup);
        }

        return 1;
    }

}
