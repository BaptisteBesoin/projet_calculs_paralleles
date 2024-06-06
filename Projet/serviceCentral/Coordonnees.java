public class Coordonnees {
    private volatile int x, y;

    public Coordonnees(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (obj instanceof Coordonnees) {
            Coordonnees c = (Coordonnees)obj;
            return (c.getX() == this.x)&&(c.getY() == this.y);
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "x : "  + this.x + " y : " + this.y; 
    }
}
