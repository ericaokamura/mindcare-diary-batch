package com.fiap.mindcare_diary_batch.models;

import com.fiap.mindcare_diary_batch.models.enums.TipoProfissional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue("Profissional")
public class Profissional extends Usuario {

    @ManyToMany(mappedBy = "profissionais")
    private List<Paciente> pacientes = new ArrayList<>();

    @OneToMany(mappedBy = "profissional")
    private List<Consulta> consultas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TipoProfissional tipoProfissional = TipoProfissional.PSICOLOGO;

    private String registroProfissional;

    @ManyToOne
    private Clinica clinica;

}
