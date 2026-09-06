package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.models.Consulta;
import com.fiap.mindcare_diary_batch.models.Prescription;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PrescriptionJobConfig {

    @Bean
    public Job prescriptionNotificationJob(
            JobRepository jobRepository,
            Step prescriptionNotificationStep) {

        return new JobBuilder("prescriptionNotificationJob", jobRepository)
                .start(prescriptionNotificationStep)
                .build();
    }

    @Bean
    public Step prescriptionNotificationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Prescription> reader,
            ItemWriter<Prescription> writer) {

        return new StepBuilder("prescriptionNotificationStep", jobRepository)
                .<Prescription, Prescription>chunk(10)
                .reader(reader)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }
}
