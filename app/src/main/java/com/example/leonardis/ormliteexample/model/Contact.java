/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model;

import com.example.leonardis.ormliteexample.model.daos.implementation.ContactDaoImp;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Represents a contact. We don't show any of this to the screen. A contact can be in many conversations. A conversation can have many contacts. Note, without any manually lists or queries, there is no way ormlite
 * can back this object with the many conversations.
 */
@DatabaseTable(tableName = "Contact", daoClass = ContactDaoImp.class)
public class Contact {

    public static final String CONTACT_TABLE = "Contact";
    public static final String ID = "_id";
    public static final String GUID = "GUID";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";

    //Note - generate the IDs. We'll be deleting everything and inserting a new if updates are detected.
    @DatabaseField(columnName = ID, generatedId = true)
    private long id;

    @DatabaseField(columnName = GUID, unique =  true, canBeNull = false)
    private String guid;

    @DatabaseField(columnName = FIRST_NAME, canBeNull = false)
    private String firstName;

    @DatabaseField(columnName = LAST_NAME, canBeNull = false)
    private String lastName;

    /**
     * Constructor for ORMLITE only.
     */
    public Contact() {
        //Do Nothing. Here for ORMLITE,
    }

    /**
     * Constructor
     */
    public Contact(String guid, String firstName, String lastName) {
        this.guid = guid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getGuid() {
        return guid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
