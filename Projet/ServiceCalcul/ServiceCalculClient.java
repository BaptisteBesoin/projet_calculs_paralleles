import java.util.Scanner;

import raytracer.Image;
import raytracer.Scene;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.server.ServerNotActiveException;

public class ServiceCalculClient implements ServiceCalcul {


    public static void main(String[] args) throws RemoteException, NotBoundException, ServerNotActiveException {           
        Registry reg;
        if(args.length == 0){
            reg = LocateRegistry.getRegistry("localhost");
        } else if(args.length == 1){
            reg = LocateRegistry.getRegistry(args[0]);
        } else {
            reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
        }
        
        ServiceCentral s = (ServiceCentral) reg.lookup("RayCasting");

        ServiceCalculClient client = new ServiceCalculClient();

        ServiceCalcul serviceCalcul = (ServiceCalcul) UnicastRemoteObject.exportObject(client, 0) ;
        
        try{
            s.enregistrerServiceCalcul(serviceCalcul);
        } catch(RemoteException e){
            System.out.println("Erreur lors de l'enregistrement du service sur le serveur central");
            e.printStackTrace();
        }
    }

    @Override
    public Image calculerImage(Scene s, int x0, int y0, int x1, int y1) throws RemoteException { 
        System.out.printf("Calcul de l'image : (%d, %d) %dx%d \n", x0, y0, x1, y1);
        return s.compute(x0, y0, x1, y1);
    }   
}