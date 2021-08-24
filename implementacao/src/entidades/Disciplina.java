package entidades;

import entidades.enums.TipoDisciplina;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter;

    private int id;
    private String nome;
    private int horas;
    private int creditos;
    private TipoDisciplina tipo;

    static {
        idCounter = 0;
    }

    private void init(String nome, int horas, int creditos, TipoDisciplina tipo) {
        this.id = criarID();
        this.nome = nome;
        this.setHoras(horas);
        this.setCreditos(creditos);
        this.tipo = tipo;
    }

    public Disciplina(String nome, int horas, int creditos, TipoDisciplina tipo) {
        init(nome, horas, creditos, tipo);
    }

    public void atualizar(String nome, int horas, int creditos, TipoDisciplina tipo) {
        if (nome != null && !nome.isEmpty())
            this.nome = nome;
        if (horas > 0)
            this.setHoras(horas);
        if (creditos > 0)
            this.setCreditos(creditos);
        if (tipo != null)
            this.tipo = tipo;
    }

    public void consultar() {
        System.out.println(this.toString());
    }

    private static synchronized int criarID() {
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        if (horas > 0)
            this.horas = horas;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        if (creditos > 0)
            this.creditos = creditos;
    }

    public TipoDisciplina getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        try {
            Disciplina objetoDisciplina = (Disciplina) obj;
            if (this.id == objetoDisciplina.getId())
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Integer id = (int) this.id;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Informações do curso:");
        sb.append("ID: " + this.id + "\n");
        sb.append("Nome: " + this.nome + "\n");
        sb.append("Horas: " + this.horas + "\n");
        sb.append("Creditos: " + this.creditos + "\n");
        sb.append("Tipo: " + this.tipo.toString().toLowerCase());
        return sb.toString();
    }
}
