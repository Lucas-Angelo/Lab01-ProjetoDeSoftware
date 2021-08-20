package entidades;

import java.util.Optional;

public class Professor extends Usuario{

    private String nome;

    private Professor(String email, String senha, String nome) {
        super(email, senha);
        this.nome = nome;
    }
    static public Professor Cadastrar(String email, String senha, String nome){
        return new Professor(email, senha, nome);
    }

    public void Atualizar( Optional<String> email, Optional<String> senha, Optional<String> nome ){
        email.ifPresent( this::setEmail );
        senha.ifPresent( this::setSenha );
        nome.ifPresent( this::setNome );
    }
    public void Consultar(){
        System.out.println(this);
    }

    public String getNome() {
        return nome;
    }
    private void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        sb.append("Informações Professor:");
        sb.append("Nome: " + nome);
        sb.append(super.toString());
        return sb.toString();
    }
}
