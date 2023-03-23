## Installation

### Ajouter driver hxtt

mvn install:install-file -Dfile=XXX/Access_JDBC42.jar -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4 -Dversion=5.2 -Dpackaging=jar 

### Récupérer les dépots depuis github

```bash
git clone git@github.com:OB7-IRD/akado2.git
```

### Lancer ce pom

mvn clean install

[![Dependency Review](https://github.com/OB7-IRD/akado2/actions/workflows/dependency-review.yml/badge.svg?branch=develop)](https://github.com/OB7-IRD/akado2/actions/workflows/dependency-review.yml)