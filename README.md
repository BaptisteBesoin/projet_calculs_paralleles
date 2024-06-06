# projet_calculs_paralleles

### Lancer les différents services 

Pour pouvoir lancer le projet vous devez être dans le dossier /Projet du github.

|                   | Compilation                                           | Lancement                                                                 |  
| :---:             | :---:                                                 |    :----:                                                                 |
| Client            | javac -cp .:client/ client/*.java                     | java -cp .:client/ MainClient [-ip du service central]                    |
| Service Calcul    | javac -cp .:ServiceCalcul/ ServiceCalcul/*.java       | java -cp .:ServiceCalcul/ ServiceCalculClient [-ip du service central]    |
| Service Central   | javac -cp .:serviceCentral/ serviceCentral/*.java     | java -cp .:serviceCentral/ LancerService                                  |