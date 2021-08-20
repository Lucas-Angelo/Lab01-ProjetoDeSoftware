package entidades;

import java.io.*;
import java.util.*;
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

    private static synchronized long criarID() {
        return idCounter++;
    }

    public Usuario( String email, String senha ){
        this.email = email;
        this.senha = senha;
        this.id = criarID();
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
    public String getSenha() {
        return senha;
    }
    protected void setSenha(String senha) {
        this.senha = senha;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        try {
            Usuario objetoUsuario = (Usuario) obj;
            if (this.id == objetoUsuario.getId())
                return true;
            else 
                return false;
        } catch (ClassCastException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        sb.append("ID: " + this.id);
        sb.append("Email: " + this.email);
        return sb.toString();
    }

}
