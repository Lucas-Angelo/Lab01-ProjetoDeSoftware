package entidades;

import java.util.Optional;

public class Aluno extends Usuario {

    public static final int MAX_DISCIPLINAS;
    public static final int MAX_DISCIPLINAS_OPTATIVAS;

    private String nome;
    private String CPF;
    
    static {
        MAX_DISCIPLINAS = 4; // Máximo de 60 alunos por disciplina
        MAX_DISCIPLINAS_OPTATIVAS = 2; // Mínimo de 3 alunos para ter a disciplina
    }

    private Aluno(String email, String senha, String nome, String CPF) {
        super(email, senha);
        this.nome = nome;
        this.CPF = CPF;
    }
    static public Aluno Cadastrar(String email, String senha, String nome, String CPF){
        return new Aluno(email, senha, nome, CPF);
    }
    public void Atualizar(Optional<String> email, Optional<String> senha, Optional<String> nome, Optional<String> CPF){
        email.ifPresent( this::setEmail );
        senha.ifPresent( this::setSenha );
        nome.ifPresent( this::setNome );
        CPF.ifPresent( this::setCPF );
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
    public String getCPF() {
        return CPF;
    }
    private void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
