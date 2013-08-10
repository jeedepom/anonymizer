anonymizer
==========

Goal :
------

Anonymizer is a tool for anomize my own projet to publish it under the JeeDePom name.

It does : 
- change the name of the directory
- change the name of the package in source code files
- change the name of author

For the moment, this projet is written in bash

installation :
--------------

place anonymizer.sh in your $PATH (or run it will an absolute path)
place a config file named anonymizer.conf in your projet
tell git to forget anonymizer.conf file 
	echo "anonymizer.conf" >> .gitignore


syntax of a config file
-----------------------

	valeur1=valeur2
	companie=jeedepom
	repertoire1=repertoire2
	marcel=guillotin



Usage :
-------

anonimyzer.sh 




