/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model;

import com.example.leonardis.ormliteexample.application.OrmliteExampleApplication;
import com.example.leonardis.ormliteexample.model.daos.implementation.ConversationDaoImp;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation. A conversation can have many Messages in it. The ForeignCollectionField is an example of how to handle the 'ONE to MANY' relationship. A conversation also has a list of contacts. Those
 * contacts that are participating in the conversation. This is an example of a 'MANY to MANY' relationship. These pieces of information are not saved or loaded automatically by ORMLITE. Therefore we have to
 * manually support them.
 */
@DatabaseTable(tableName = "Conversation", daoClass = ConversationDaoImp.class)
public class Conversation {

    public static final String CONVERSATION_TABLE = "Conversation";
    public static final String ID = "_id";
    public static final String IDENTIFIER = "Identifier";//Conversation Id

    @DatabaseField(columnName = ID, generatedId = true)
    private long id;

    @DatabaseField(columnName = IDENTIFIER, unique = true, canBeNull = false)
    private String identifier;

    //Note - example of how to implement a 'ONE to MANY' relationship. Query a conversation and you get the list of messages with it from database as well.
    @ForeignCollectionField
    private ForeignCollection<Message> messages;

    //Note - example of how to implement a 'MANY to MANY' relationship. Must be done manually. Look at ConversationDao to understand how this is done under the hood or look at getGroupdMembers() to see how we must perform a second query
    private List<Contact> groupMembers;

    /**
     * Constructor for ORMLITE only.
     */
    public Conversation() {
        //Do Nothing. Here for ORMLITE,
    }

    /**
     * Constructor
     */
    public Conversation(String conversationId, List<Contact> groupMembers) {
        this.identifier = conversationId;
        this.groupMembers = groupMembers;
    }

    public long getId() {
        return id;
    }

    public String getConversationId() {
        return identifier;
    }

    /**
     * Get the list of group members. If null, perform a query.
     * @return
     */
    public List<Contact> getGroupdMembers() {
         /*
            Note - shows how we do a second sql call to load group members since that is a many to many relationship. Or we can override all the query options in a dao.
         */
        if (groupMembers == null) {

            groupMembers = new ArrayList<>();

            //
            //Query for list of Contacts in this specific conversation
            //
            Dao<ConversationGroup, Integer> conversationGroupDao = OrmliteExampleApplication.getDatabase().getConversationGroupDao();
            try {
                List<ConversationGroup> conversationGroups = conversationGroupDao.queryForEq(ConversationGroup.CONVERSATION_ID, id);
                for (ConversationGroup conversationGroup : conversationGroups) {
                    groupMembers.add(conversationGroup.getContact());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return groupMembers;
    }

    /**
     * Get a list of messages associated with this conversation
     * @return
     */
    public ForeignCollection<Message> getMessages() {
        return messages;
    }


}
