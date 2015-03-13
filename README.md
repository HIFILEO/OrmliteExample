# OrmliteExample

This project is to demonstrate how to use ORMLITE in order to create a list of conversations and messages within those conversations. Although the visual components are simple, the database being built by ormlite is what the real project demonstrates.

You can add conversations and messages. Delete a conversation and it will cascade the deletions of all messages within that conversation. The following tables are created:
1.) Conversation table - Represents a single conversation. Has multiple messages and multiple contacts
2.) Message table - represents individual messages. Linked to conversation table
3.) Contact table - represents an individual contact. Used to demonstrate many to many relationship with the Conversation table.
4.) ConversationGroup table - represents the linkage between the conversation table and contact table. Required for Many to Many relationships.

The menu option on the conversation activity allows you to export the database that was created using ormlite. This way you can see how the relationships are set up and how data is stored in the database. Use ADB monitor to remove the database file once export. Files are exported
into a folder with the same name as the application.

Ormlite has the following benefits (IMHO)
- cascade deletes are an option in the class definition
- DAOs can be customized. Needed for Many To Many if you want to enforce the creation and updates behind the scenes
- Very easy to customize ORM entities.
- Many online examples including great documentation
- Many to Many table setup took class objects and build the IDs correctly

Ormlite has the following drawbacks (IMHO)
- annotations in android are not read in the same order you lay out the entities. In other words, extract the database and _id primary key will NOT be the first column in the table.
- claimed by many other ORMs that ORMlite is slow because of annotations.
- more manual work up front required to create robust database.
- need to write sql code in the annotations to support more SQL options like cascade delete