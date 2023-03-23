Driver ANAPO
============

Pilote d'accès aux bases ANAPO en construisant une couche objet métier, à l'aide du pilote JDBC HXTT.

HXTT
----
La librairie HXTT doit être installé dans le dépôt Maven local avec la série commande suivante en fonction de la version à installer.

Version 5.1

    mvn install:install-file -Dfile=Access_JDBC40.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4 -Dversion=5.1 -Dpackaging=jar
    mvn install:install-file -Dfile=Access_JDBC41.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4.1 -Dversion=5.1 -Dpackaging=jar
    mvn install:install-file -Dfile=Access_JDBC42.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4.2 -Dversion=5.1 -Dpackaging=jar

Version 5.2

    mvn install:install-file -Dfile=Access_JDBC40.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4 -Dversion=5.2 -Dpackaging=jar
    mvn install:install-file -Dfile=Access_JDBC41.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4.1 -Dversion=5.2 -Dpackaging=jar
    mvn install:install-file -Dfile=Access_JDBC42.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4.2 -Dversion=5.2 -Dpackaging=jar