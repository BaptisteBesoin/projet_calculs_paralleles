import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import raytracer.*;

public class ServiceCentralRay implements ServiceCentral {
    ArrayList<ServiceCalcul> listeClient;
    ConcurrentLinkedQueue<Coordonnees> coordonneesAfaire;
    ServiceClient client;

    public ServiceCentralRay(){
	    listeClient = new ArrayList<ServiceCalcul>();
        coordonneesAfaire = new ConcurrentLinkedQueue<Coordonnees>();
    }

    public void enregistrerServiceCalcul(ServiceCalcul c) throws RemoteException{
	    listeClient.add(c);
	    System.out.println("Nouveau service de calcul ajouté");
    }

    public void enregistrerClient(ServiceClient c) throws RemoteException{
	    client = c;
	    System.out.println("Nouveau service de client ajouté");
    }

    public void effectuerCalcul(Scene scene, int nbCarreParLigne) throws RemoteException {
        int largeur = client.getLargeur();
        int hauteur = client.getHauteur();

        for (int y = 0; y < hauteur; y+=nbCarreParLigne) {
            for (int x = 0; x < largeur; x+=nbCarreParLigne) {
                coordonneesAfaire.add(new Coordonnees(x, y));
            }
        }

        while (coordonneesAfaire.size() != 0) {
            Iterator<Coordonnees> iterator = coordonneesAfaire.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Coordonnees coordonnees = iterator.next();

                //Attente pour le client le temps que des services de calcul s'initialise
                // TO DO
                //while (verifierServiceCalculDispo()) {
                //    System.out.println("Attente d'un nouveau service de calcul");
                //}

                ServiceCalcul client = listeClient.get(i % listeClient.size());

                ThreadServiceCalcul thread = new ThreadServiceCalcul(this, client, scene, coordonnees.getX(), coordonnees.getY(), nbCarreParLigne);
                thread.start();

                i++;
                
            }
        }

        System.out.println(coordonneesAfaire.size());
    }

    @Override
    public void afficherImage(Image i, int x, int y) throws RemoteException {
        this.client.afficherPartieImage(i, x, y);
        coordonneesAfaire.remove(new Coordonnees(x, y));
        System.out.println(coordonneesAfaire.size());
    }

    public synchronized void removeServiceCalcul(ServiceCalcul serviceCalcul) throws RemoteException{
        if(listeClient.contains(serviceCalcul)){
            listeClient.remove(serviceCalcul);
        }
    }
}
