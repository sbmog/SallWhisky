package application.model;

public enum WhiskyType {
    SINGLE_CASK("Single Cask"),
    CASK_STRENGTH("Cask Strength"),
    SINGLE_MALT("Single Malt"),
    BLENDED("Blended"),
    PEATED_SINGLE_MALT("Peated Single Malt"),
    GRAIN("Grain");


    private final String displayName;

    WhiskyType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
