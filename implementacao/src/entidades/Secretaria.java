package entidades;

public class Secretaria extends Usuario{

    private long telefone;
    
    public Secretaria(String email, String senha, long telefone) {
        super(email, senha);
        this.telefone = telefone;
    }

    static public Secretaria Cadastrar(String email, String senha, long telefone){
        return new Secretaria(email, senha, telefone);
    }

    @Override
    public String toString() {
        sb.append("Informações Secretaria:");
        sb.append("Telefone: " + telefone);
        sb.append(super.toString());
        return sb.toString();
    }
    
}