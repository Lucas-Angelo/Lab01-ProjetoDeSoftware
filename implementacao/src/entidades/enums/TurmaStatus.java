package entidades.enums;

public enum TurmaStatus {
    MATRICULA_ABERTA("Turma com matrícula aberta"),
    ATIVA("Turma ativa"),
    INATIVA("Turma inativa"),
    CONCLUIDA("Turma concluída");

    private String descricao;

    TurmaStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
