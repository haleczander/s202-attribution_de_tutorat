package oop;

public enum Resource {

    R101("1"),
    R102("2"),
    R103("3"),
    R104("4"),
    R105("5"),
    R106("6");

    private String label;

    Resource(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
