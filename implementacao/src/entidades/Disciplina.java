package entidades;

import entidades.enums.TipoDisciplina;

public class Disciplina {
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
    
    public void cadastrar(String nome, int horas, int creditos, TipoDisciplina tipo){
        this.id = criarID();
        this.nome = nome;
        this.horas = horas;
        this.creditos = creditos;
        this.tipo = tipo;
    }

    public void atualizar(String nome, int horas, int creditos, TipoDisciplina tipo){
        if(nome != null && !nome.isEmpty())
            this.nome = nome;
        if(horas > 0)
            this.horas = horas;
        if(creditos > 0)
            this.creditos = creditos;
        if(tipo != null)
            this.tipo = tipo;
    }
    
    public void consultar(){
        System.out.println(this.toString());
    }

    public static synchronized int criarID() {
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.horas = horas;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public TipoDisciplina getTipo() {
        return tipo;
    }

    public void setTipo(TipoDisciplina tipo) {
        this.tipo = tipo;
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
        
        String tipo = this.tipo.toString().substring(0,1) + this.tipo.toString().substring(1).toLowerCase();
        sb.append("Creditos: " + tipo);
        return sb.toString();
    }
}
