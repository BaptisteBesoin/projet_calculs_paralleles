import java.rmi.Remote;
import java.rmi.RemoteException;

import raytracer.Image;

public interface ServiceClient extends Remote{
    public void afficherPartieImage(Image i, int x, int y) throws RemoteException;
    public int getLargeur() throws RemoteException;
    public int getHauteur() throws RemoteException;
}