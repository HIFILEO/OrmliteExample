/*
Property of Daniel Leonardis 2015.
Free to distribute, use, or modify under open source license
*/
package com.example.leonardis.ormliteexample.model.daos.interfaces;

import com.example.leonardis.ormliteexample.model.Conversation;
import com.j256.ormlite.dao.Dao;

/**
 * Interface for data access object. Define new methods for sql queries here.
 */
public interface ConversationDao extends Dao<Conversation, Integer> {

}
