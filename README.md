Aeropuerto MVN
============================

Este proyecto se basa en el complemento **DataNucleus** y  **Maven**. Compruebe la configuración de la base de datos en el archivo *datanucleus.properties* y la dependencia del controlador JDBC especificada en el archivo *pom.xml*. Además, el proyecto contiene los códigos de ejemplo de servidor y cliente.

## Ejecución

Ejecute el siguiente comando para compilar todo y mejorar las clases de base de datos:

	mvn clean
Elimina los archivos y carpetas generados por una compilación previa de un proyecto Maven.
	
	mvn compile

Para asegurarse de que la base de datos esté correctamente configurada, le guiaré a través de los pasos para utilizar los comandos SQL que se encuentran típicamente en el archivo messagesDB.sql para crear una base de datos y otorgar privilegios. Antes de ejecutar el siguiente comando es necesario tener la aplicación de la BBDD abierta y haber ejecutado en ella el archivo "messagesDB.sql".

      Get-Content sql/messagesDB.sql | mysql -u root -p messagesDB

Ejecute el siguiente comando para crear el enhance de base de datos para esta muestra.

      mvn datanucleus:enhance


Ejecuta el siguiente comando para ver si la base de datos funciona correctamente.

      mvn datanucleus:schema-create


Para iniciar el servidor, ejecuta el comando

    mvn jetty:run

Ahora, el código de muestra del cliente se puede ejecutar en una NUEVA VENTANA DE COMANDOS con

    mvn exec:java -Pclient

