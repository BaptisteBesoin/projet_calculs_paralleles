import java.rmi.RemoteException;
import java.net.ConnectException;
import java.rmi.Remote;

import raytracer.Image;
import raytracer.Scene;

public interface ServiceCalcul extends Remote {
    public Image calculerImage(Scene s, int x0, int y0, int x1, int y1) throws RemoteException;
}