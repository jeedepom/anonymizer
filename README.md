anonymizer
==========

Goal :
------

Anonymizer is a java tool for anonymize my own projet to publish it under another name. (currently JeeDePom)

It does : 
- change the name of the directory
- change the name of the package in source code files
- change the name of author
- revert the changes with the -i option.


installation :
--------------

- place a config file named anonymizer.properties in the *root directory* of your projet
- eventually tell git to forget anonymizer.properties file via the command 

	echo "anonymizer.properties" >> .gitignore


syntax of a config file (named anonymizer.properties)
-----------------------

	valeur1=valeur2
	masociete=jeedepom
	repertoire1=repertoire2
	marcel=guillotin

Usage :
-------

    java anonymizer [-h|-i] project-directory

or (if packaged in a jar-file)
 
    java -jar anonymizer.jar

or (if you've getted sources with maven)

    process-classes org.codehaus.mojo:exec-maven-plugin:1.2.1:exec -Dexec.args=-classpath %classpath fr.jeedepom.anonymizer.Anonymizer  src/test/resources -Dexec.executable=/usr/java/latest/bin/java -Dexec.classpathScope=runtime


TODO :
------
- probably some refactoring to clarify classes
- find a way to completly refactor package directory : 
    * a directory became two directories ( mycompagny <-> fr.newname ), actually it only rename one to another.
    * correct the package name with the news directories
- find a way to replace only a word by another. Actually changing "de" by "fr" would cause serious problems, for 'default' java keyword
- Build some UnitTesting with a file-repository mock.

BUILDING :
----------
To build (compiling) this project, you need a JDK1.7 and Maven3.
The easier way to build is to import the project into Netbeans Java IDE provided by Oracle Inc.
You could also import the project into Eclipse as a "Maven Projet with existing sources". 