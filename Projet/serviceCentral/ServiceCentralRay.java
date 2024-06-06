import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.time.Duration;
import java.time.Instant;
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
	    try {
            System.out.println("Nouveau service de calcul ajouté : " + RemoteServer.getClientHost());
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

    }

    public void enregistrerClient(ServiceClient c) throws RemoteException{
	    client = c;
	    try {
            System.out.println("Nouveau service de client ajouté : " + RemoteServer.getClientHost());
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
    }

    public void effectuerCalcul(Scene scene, int nbCarreParLigne) throws RemoteException {
        int largeur = client.getLargeur();
        int hauteur = client.getHauteur();

        for (int y = 0; y < hauteur; y+=nbCarreParLigne) {
            for (int x = 0; x < largeur; x+=nbCarreParLigne) {
                coordonneesAfaire.add(new Coordonnees(x, y));
            }
        }

        Instant debut = Instant.now();
        while (coordonneesAfaire.size() != 0) {
            Iterator<Coordonnees> iterator = coordonneesAfaire.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Coordonnees coordonnees = iterator.next();

                while (listeClient.size() == 0) {
                    System.out.println("Attente d'un nouveau service de calcul");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                ServiceCalcul client = listeClient.get(i % listeClient.size());

                ThreadServiceCalcul thread = new ThreadServiceCalcul(this, client, scene, coordonnees.getX(), coordonnees.getY(), nbCarreParLigne);
                thread.start();

                i++;
                
            }
        }

        Instant fin = Instant.now();
        long duree = Duration.between(debut, fin).toMillis();

        System.out.println("FINI : " + duree + "ms");
    }

    @Override
    public void afficherImage(Image i, int x, int y) throws RemoteException {
        this.client.afficherPartieImage(i, x, y);
        coordonneesAfaire.remove(new Coordonnees(x, y));
    }

    public synchronized void removeServiceCalcul(ServiceCalcul serviceCalcul) throws RemoteException{
        if(listeClient.contains(serviceCalcul)){
            listeClient.remove(serviceCalcul);
        }
    }
}
