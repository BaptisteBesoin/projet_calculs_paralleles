import java.rmi.RemoteException;
import java.rmi.Remote;

import raytracer.*;
public interface ServiceCentral extends Remote {
    public void enregistrerServiceCalcul(ServiceCalcul c) throws RemoteException;
    public void enregistrerClient(ServiceClient c) throws RemoteException;
    public void effectuerCalcul(Scene s, int nbCarreParLigne) throws RemoteException;
    public void afficherImage(Image i, int x, int y) throws RemoteException;
    public void removeServiceCalcul(ServiceCalcul serviceCalcul) throws RemoteException;
}
