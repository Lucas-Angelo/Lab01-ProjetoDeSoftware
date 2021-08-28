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
        sb.append("\n----------------------");
        sb.append("\nInformações Secretaria:");
        sb.append("\nTelefone: " + telefone);
        sb.append(super.toString());
        return sb.toString();
    }

}
