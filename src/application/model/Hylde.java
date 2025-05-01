package application.model;

public class Hylde {
    private int hyldeID;
    private boolean pladsFri = true;

    private Reol reol;

    public Hylde(int hyldeID, Reol reol) {
        this.hyldeID = hyldeID;
        this.reol = reol;
    }

    public int getHyldeID() {
        return hyldeID;
    }

    public void setHyldeID(int hyldeID) {
        this.hyldeID = hyldeID;
    }

    public boolean isPladsFri() {
        return pladsFri;
    }

    public void setPladsFri(boolean pladsFri) {
        this.pladsFri = pladsFri;
    }

    public Reol getReol() {
        return reol;
    }

    public void setReol(Reol reol) {
        this.reol = reol;
    }
}
