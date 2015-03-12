# OrmliteExample

This project is to demonstrate how to use ORMLITE in order to create a list of conversations and messages within those conversations. Although the visual components are simple, the database being built by ormlite is what the real project demonstrates.

You can add conversations and messages. Delete a conversation and it will cascade the deletions of all messages within that conversation. The following tables are created:
1.) Conversation table - Represents a single conversation. Has multiple messages and multiple contacts
2.) Message table - represents individual messages. Linked to conversation table
3.) Contact table - represents an individual contact. Used to demonstrate many to many relationship with the Conversation table.
4.) ConversationGroup table - represents the linkage between the conversation table and contact table. Required for Many to Many relationships.

The menu option on the conversation activity allows you to export the database that was created using ormlite. This way you can see how the relationships are set up and how data is stored in the database. Use ADB monitor to remove the database file once export. Files are exported
into a folder with the same name as the application.