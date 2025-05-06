package application.model;

public class HyldePlads {
    private int hyldePladsID;
    private boolean pladsFri = true;
    private Reol reol;

    protected HyldePlads(int hyldePladsID, Reol reol) {
        if (hyldePladsID <= 0) {
            throw new IllegalArgumentException("HyldePladsID skal være et tal over 0.");
        } else if (reol == null) {
            throw new IllegalArgumentException("Reol kan ikke være null.");
        }
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
