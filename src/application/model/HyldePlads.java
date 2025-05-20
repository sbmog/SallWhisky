package application.model;

public class HyldePlads {
    private int hyldePladsID;

    private Reol reol;
    private FadPlacering fadPlaceret;

    protected HyldePlads(int hyldePladsID, Reol reol) {
        if (hyldePladsID <= 0) {
            throw new IllegalArgumentException("HyldePladsID skal være et tal over 0.");
        } else if (reol == null) {
            throw new IllegalArgumentException("Reol kan ikke være null.");
        }
        this.hyldePladsID = hyldePladsID;
        this.reol = reol;
    }

    public FadPlacering getFadPlaceret() {
        return fadPlaceret;
    }

    public void setFadPlaceret(FadPlacering fadPlaceret) {
            this.fadPlaceret = fadPlaceret;
    }

    public int getHyldePladsID() {
        return hyldePladsID;
    }

    public boolean isPladsFri() {
        return fadPlaceret == null;
    }

    public void setPladsFri() {
        this.fadPlaceret = null;
    }

    public Reol getReol() {
        return reol;
    }

    public void setReol(Reol reol) {
        this.reol = reol;
    }

    public String toString() {
        return "Hyldeplads #" + hyldePladsID;
    }
}