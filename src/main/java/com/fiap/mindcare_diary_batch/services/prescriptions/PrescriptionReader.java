package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.models.Prescription;
import com.fiap.mindcare_diary_batch.repositories.PrescriptionRepository;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class PrescriptionReader implements ItemReader<Prescription> {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    private Iterator<Prescription> iterator;

    public void carregarPrescriptions() {

        LocalDate today = LocalDate.now();

        List<Prescription> prescriptions = prescriptionRepository.findAll()
                .stream()
                .filter(r ->
                    r.getExpirationDate().isEqual(today)
                )
                .toList();

        System.out.println("=================================");
        System.out.println("RECEITAS MÉDICAS ENCONTRADAS: " + prescriptions.size());
        System.out.println("HOJE: " + today);
        System.out.println("=================================");

        iterator = prescriptions.iterator();
    }

    @Override
    public Prescription read() {
        if (iterator == null){
            carregarPrescriptions();
        }

        if(iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }
}
