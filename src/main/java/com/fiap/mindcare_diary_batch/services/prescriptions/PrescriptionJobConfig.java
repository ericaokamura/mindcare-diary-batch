package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.models.Consulta;
import com.fiap.mindcare_diary_batch.models.Prescription;
import com.google.firebase.messaging.Message;
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
            PrescriptionReader prescriptionReader,
            PrescriptionProcessor prescriptionProcessor,
            PrescriptionWriter prescriptionWriter
    ) {

        return new StepBuilder("prescriptionNotificationStep", jobRepository)
                .<Prescription, Message>chunk(10)
                .reader(prescriptionReader)
                .processor(prescriptionProcessor)
                .writer(prescriptionWriter)
                .transactionManager(transactionManager)
                .build();
    }
}
