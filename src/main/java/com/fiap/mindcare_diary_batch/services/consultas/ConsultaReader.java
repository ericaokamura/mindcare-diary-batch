package com.fiap.mindcare_diary_batch.services.consultas;

import com.fiap.mindcare_diary_batch.models.Consulta;
import com.fiap.mindcare_diary_batch.repositories.ConsultaRepository;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Component
public class ConsultaReader implements ItemReader<Consulta> {

    @Autowired
    private ConsultaRepository consultaRepository;

    private Iterator<Consulta> iterator;

    private void carregarConsultas() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(1440);

        List<Consulta> consultas = consultaRepository.findAll()
                .stream()
                .filter(c ->
                        !c.isAtendida()
                                && !c.isCancelada()
                                && c.getDataHoraConsulta() != null
                                && c.getDataHoraConsulta().isAfter(start)
                                && c.getDataHoraConsulta().isBefore(end)
                )
                .toList();

        System.out.println("=================================");
        System.out.println("CONSULTAS ENCONTRADAS: " + consultas.size());
        System.out.println("INÍCIO: " + start);
        System.out.println("FIM:    " + end);
        System.out.println("=================================");

        iterator = consultas.iterator();
    }

    @Override
    public Consulta read() {
        if (iterator == null) {
            carregarConsultas();
        }

        if(iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }
}