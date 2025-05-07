package application.model;

public class FadType {
    private String navn;

    public FadType(String navn) {
        if (navn == null || navn.isEmpty()) {
            throw new IllegalArgumentException("Navn kan ikke være null eller tom.");
        }
        this.navn = navn;
    }

    public String getNavn() {
        return navn;

    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
