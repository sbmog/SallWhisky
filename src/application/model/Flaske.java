package application.model;

public class Flaske {
    private int flaskeID;

    private Whisky whisky;

    protected Flaske(int flaskeID, Whisky whisky) {
        this.flaskeID = flaskeID;
        this.whisky = whisky;
    }

    public int getFlaskeID() {
        return flaskeID;
    }

    public void setFlaskeID(int flaskeID) {
        this.flaskeID = flaskeID;
    }

    public Whisky getWhisky() {
        return whisky;
    }

    public void setWhisky(Whisky whisky) {
        this.whisky = whisky;
    }
}
