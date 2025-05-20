package application.model;

public class Flaske {
    private int flaskeID;

    private Whisky whisky;

    protected Flaske(int flaskeID, Whisky whisky) {
        if (flaskeID <= 0) {
            throw new IllegalArgumentException("FlaskeID skal være et tal over 0.");
        } else if (whisky == null) {
            throw new IllegalArgumentException("Whisky kan ikke være null.");
        }
        this.flaskeID = flaskeID;
        this.whisky = whisky;
    }

    public int getFlaskeID() {
        return flaskeID;
    }

    public Whisky getWhisky() {
        return whisky;
    }

    public void setWhisky(Whisky whisky) {
        this.whisky = whisky;
    }

    public String toString() {
        return "Flaske: #" + flaskeID;
    }
}