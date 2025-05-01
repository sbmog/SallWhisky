package application.model;

public class HyldePlads {
    private int hyldePladsID;
    private boolean pladsFri = true;
    private Reol reol;

    public HyldePlads(int hyldePladsID, Reol reol) {
        this.hyldePladsID = hyldePladsID;
        this.reol = reol;
    }

    public int getHyldePladsID() {
        return hyldePladsID;
    }

    public void setHyldePladsID(int hyldePladsID) {
        this.hyldePladsID = hyldePladsID;
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
