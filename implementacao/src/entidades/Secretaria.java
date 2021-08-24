package entidades;

public class Secretaria extends Usuario {

    private long telefone;

    private void init(long telefone) {
        this.telefone = telefone;
    }

    public Secretaria(String email, String senha, long telefone) {
        super(email, senha);
        init(telefone);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informações Secretaria:");
        sb.append("Telefone: " + telefone);
        sb.append(super.toString());
        return sb.toString();
    }

}
