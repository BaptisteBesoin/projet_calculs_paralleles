import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerService{
    public static void main(String[] args){
	ServiceCentralRay serviceCentralRay = new ServiceCentralRay();

	try{
	    ServiceCentral serviceCentral = (ServiceCentral) UnicastRemoteObject.exportObject(serviceCentralRay, 0);

	    try{
		Registry reg = LocateRegistry.createRegistry(1099);
	    
		try{
		    reg.rebind("RayCasting", serviceCentral);
		} catch(RemoteException e){
		    System.out.println("Erreur dans l'enregistrement du service, bind");
		}
	    } catch (RemoteException e){
		System.out.println("Erreur dans l'enregistrement du service, annuaire");
	    }
	} catch (RemoteException e){
	    System.out.println("Erreur dans l'enregistrement du service, service central");
	} 
    }
}
