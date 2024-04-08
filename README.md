Jersey + DataNucleus + MySQL
============================

This example relies on the DataNucleus Maven plugin. Check the database configuration in the datanucleus.properties file and the JDBC driver dependency specified in the pom.xml file. In addition, the project contains the server and client example codes.

Run the following command to build everything and enhance the DB classes:

      mvn clean compile

Make sure that the database was correctly configured. Use the contents of the file create-message.sql to create the database and grant privileges. For example,

      mysql -u root -p > sql/messagesDB.sql

Ejecute el siguiente comando para crear el enhance de base de datos para esta muestra.

      mvn datanucleus:enhance


Run the following command to see ig th db works correctly.

      mvn datanucleus:schema-create


To launch the server run the command

    mvn jetty:run

Now, the client sample code can be executed in a new COMMAND WINDOW with

    mvn exec:java -Pclient

