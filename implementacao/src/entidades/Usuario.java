package entidades;

import java.io.Serializable;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public abstract class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

    private static long idCounter;

    protected long id;
    protected String email;
    protected String senha;

    static {
        idCounter = 100_000L; // ID's dos Usuários começam com 100.000;
    }

    public Usuario( String email, String senha ){
        this.email = email;
        this.senha = senha;
        this.id = idCounter++;
    }
    
    public void logar() throws NotImplementedException{
        throw new NotImplementedException("Metodo nao implmentado");
    }

    public void deslogar() throws NotImplementedException{
        throw new NotImplementedException("Metodo nao implmentado");
    }

    public String getEmail() {
        return email;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }
    protected void setId(long id) {
        this.id = id;
    }
    public String getSenha() {
        return senha;
    }
    protected void setSenha(String senha) {
        this.senha = senha;
    }
}
