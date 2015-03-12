/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model.daos.implementation;

import com.example.leonardis.ormliteexample.model.Contact;
import com.example.leonardis.ormliteexample.model.daos.interfaces.ContactDao;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


/**
 * The implementation of data access object.
 */
public class ContactDaoImp extends BaseDaoImpl<Contact, Integer> implements ContactDao {

    public ContactDaoImp(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Contact.class);
    }

}
