import java.rmi.ConnectException;
import java.rmi.RemoteException;

import raytracer.Image;
import raytracer.Scene;

public class ThreadServiceCalcul extends Thread {
    Scene scene;
    int x, y;
    int taille;
    ServiceCentral serviceCentral;
    ServiceCalcul serviceCalcul;

    public ThreadServiceCalcul(ServiceCentral scr, ServiceCalcul sc, Scene s, int x, int y, int taille){
        this.scene = s;
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.serviceCentral = scr;
        this.serviceCalcul = sc;
    }

    @Override
    public void run() {
        Image i;
        try {
            i = serviceCalcul.calculerImage(scene, x, y, taille, taille);
            this.serviceCentral.afficherImage(i, x, y);
        } catch (Exception e) {
            try {
                this.serviceCentral.removeServiceCalcul(serviceCalcul);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
        
    }

}