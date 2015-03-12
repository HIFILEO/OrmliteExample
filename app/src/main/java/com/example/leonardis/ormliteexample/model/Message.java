/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A message object. You can have multiple messages per conversation. Look to the conversation class for 'ONE to MANY' relationship example
 */
@DatabaseTable(tableName = "Message")
public class Message {

    public static final String MESSAGE_TABLE = "Contact";
    public static final String ID = "_id";
    public static final String IDENTIFIER = "Identifier";//messageId
    public static final String CONVERSATION_ID = "ConversationId";
    public static final String SENDER_ID = "SenderId";

    //Primary Key
    @DatabaseField(columnName = ID, generatedId = true)
    private long id;

    @DatabaseField(columnName = IDENTIFIER, unique =  true, canBeNull = false)
    private String identifier;

    //Note - foreign term let's the ORMLITE know that we have a foreign reference to Conversation
    //Note - to get cascade deletes to work, meaning a conversation deleted will delete all messages that are linked to it, you have insert the column definition for ORMLITE to create the table correctly
    //Note - Ormlite needs you to link primary keys. This way the objects get filled when doing a query for Message. You get the Conversation object for free.
    @DatabaseField(columnName = CONVERSATION_ID, canBeNull = false,  foreign = true, foreignAutoRefresh = true, columnDefinition = "BIGINT REFERENCES " + Conversation.CONVERSATION_TABLE + "(" + Conversation.ID + ")" + " ON DELETE CASCADE")
    private Conversation conversation;

    @DatabaseField(columnName = SENDER_ID, canBeNull = false, foreign = true, columnDefinition = "BIGINT REFERENCES " + Contact.CONTACT_TABLE + "(" + Contact.ID + ")" + " ON DELETE CASCADE")
    private Contact sender;

    /**
     * Constructor for ORMLITE only.
     */
    public Message() {
        //Do Nothing. Here for ORMLITE,
    }

    /**
     * Constructor
     */
    public Message(String messageId, Conversation conversation, Contact sender) {
        this.identifier = messageId;
        this.conversation = conversation;
        this.sender = sender;
    }

    public String getMessageId() {
        return identifier;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Contact getSender() {
        return sender;
    }
}
