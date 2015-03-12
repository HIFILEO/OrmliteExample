/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Represents the ConversationGroup table. In order to get 'MANY to MANY' relationships working, you need a middle table. This class represents that table.
 */
@DatabaseTable(tableName = "ConversationGroup")
public class ConversationGroup {

    public static final String CONVERSATION_ID = "ConversationId";
    public static final String CONTACT_ID = "ContactId";

    //Note - generate the IDs. We'll be deleting everything and inserting a new if updates are detected.
    @DatabaseField(generatedId = true)
    private long id;

    //Note - foreign = true - is only for ORMLITE. The database won't have the word Foreign Key in it.
    //Note - ORMLITE wants to use the primary keys for the tables that it references. This way ORMLITE loads objects when queried. In the database, only the IDs are stored.
    @DatabaseField(columnName = CONVERSATION_ID, foreign = true, columnDefinition = "BIGINT REFERENCES " + Conversation.CONVERSATION_TABLE + "(" + Conversation.ID + ")" + " ON DELETE CASCADE")
    private Conversation conversation;

    @DatabaseField(columnName = CONTACT_ID, foreign = true, columnDefinition = "BIGINT REFERENCES " + Contact.CONTACT_TABLE + "(" + Contact.ID + ")" + " ON DELETE CASCADE")
    private Contact contact;

    public ConversationGroup() {
        //Do Nothing. Here for ORMLITE,
    }

    public ConversationGroup(Conversation conversation, Contact contact) {
        this.contact = contact;
        this.conversation = conversation;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Contact getContact() {
        return contact;
    }
}
