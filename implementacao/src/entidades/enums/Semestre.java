package entidades.enums;

public enum Semestre {
    PRIMEIRO("Primeiro", 1),
    SEGUNDO("Segundo", 2);

    private String extenso;
    private int numerico;
    
    Semestre(String extenso, int numerico) {
        this.extenso = extenso;
        this.numerico = numerico;
    }

    public String getExtenso() {
        return this.extenso;
    }

    public int getNumerico() {
        return this.numerico;
    }
}
