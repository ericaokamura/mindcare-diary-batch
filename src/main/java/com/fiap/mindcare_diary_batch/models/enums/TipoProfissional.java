package com.fiap.mindcare_diary_batch.models.enums;

public enum TipoProfissional {

    PSICOLOGO(0, "PSICOLOGO"),
    PSIQUIATRA(1, "PSIQUIATRA");

    private int codigo;
    private String descricao;

    TipoProfissional(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}

