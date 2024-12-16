Membres du groupe:
IDRES Anis
num étudiant:22212192
REZALA Ayoub
num étudiant:22309776


Tout d'abord, on ouvre un terminal dans le répertoire source src de notre projet.
pour la compilation:
	javac -d ../build -cp .:../lib/blocksworld.jar:../lib/bwgenerator.jar modelling/*.java planning/*.java cp/*.java datamining/*.java blocksworld/*.java

pour l'éxecution:
	java -cp ../build:../lib/blocksworld.jar:../lib/bwgenerator.jar blocksworld.NOM_CLASSE_EXECUTABLE
