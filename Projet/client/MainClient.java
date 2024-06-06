import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainClient {
     public static void main(String[] args) throws RemoteException, NotBoundException{
        Registry reg;
        if(args.length == 0){
            reg = LocateRegistry.getRegistry("localhost");
        } else if(args.length == 1){
            reg = LocateRegistry.getRegistry(args[0]);
        } else {
            reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
        }
        
        ServiceCentral s = (ServiceCentral) reg.lookup("RayCasting");

        Client c = new Client("simple3.txt", 512, 512);

        ServiceClient serviceClient = (ServiceClient) UnicastRemoteObject.exportObject(c, 0);
        
        try{
            s.enregistrerClient(serviceClient);
            s.effectuerCalcul(c.getScene(), 8);
        } catch(RemoteException e){
            System.out.println("Erreur lors de l'enregistrement du service sur le serveur central");
            e.printStackTrace();
        }
    }    
}
