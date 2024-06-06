import java.rmi.RemoteException;

import raytracer.*;


public class Client implements ServiceClient {
    private Disp display;
    private Scene scene;
    private int largeur, hauteur;
    
    public Client(String fichier_description, int largeur, int hauteur){
        this.display = new Disp("Raytracer", largeur, hauteur);
        this.scene = new Scene(fichier_description, largeur, hauteur);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    @Override
    public void afficherPartieImage(Image i, int x, int y) throws RemoteException {
        display.setImage(i, x, y);
    }

    public Scene getScene(){
        return this.scene;
    }

    public int getLargeur(){
        return this.largeur;
    }

    public int getHauteur(){
        return this.hauteur;
    }
}