Benjamin Allaert, Francois lepan

Dans ce dossier:

Compilation : 
	javac -cp bin src/Game/Game.java

Execution : 
	java -classpath bin Game.Game

Lors de l'execution la matrice des jeux est créée (marix.csv).
Ensuite un prompt s'affiche demandant à l'utilisateur d'entrer des données.
Lorsque les données sont entrées la confrontation des stratégies à lieu et la création du fichier curve.csv est faite.
Enfin on redemande à l'utilisateur d'entrer des données.

EXAMPLE:

You must enter at least 4 argument separated by a comma in order to confront 2 strategy:

	<num_of_strategy>,<number_of_population>,<num_of_strategy>,<number_of_population>[,<num_of_strategy>,<number_of_population>]*

num_of_strategy :

	|0 : All_C |	|1 : All_D |	|2 : Spiteful |	|3 : Easy_Go |	|4 : Tit_For_Tat |	|5 : Mistrust |	|6 : Two_Tit_For_Tat |	|7 : Three_Tit_For_Tat |	
	|8 : Tf2t |	|9 : Hard_tf2t |	|10 : Slow_tft |	|11 : Hard_tft |	|12 : Soft_Majo |	|13 : Hard_Majo |	
	|14 : Soft_Not_More |	|15 : Hard_Not_More |	|16 : PavLov |	|17 : Prober |	|18 : Per_CD |	|19 : Per_DC |	|20 : Per_CCD |	|21 : Per_DDC |	

Enter quit to exit the program.

> 1,200,3,500,11,500,13,600
The data has been saved to the file: ./curve.csv

> quit