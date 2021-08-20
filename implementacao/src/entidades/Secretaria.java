package entidades;

public class Secretaria extends Usuario{

    private long telefone;
    
    public Secretaria(String email, String senha, long telefone) {
        super(email, senha);
        this.telefone = telefone;
    }

    public void Consultar(){
        System.out.println(this);
    }
    
}
