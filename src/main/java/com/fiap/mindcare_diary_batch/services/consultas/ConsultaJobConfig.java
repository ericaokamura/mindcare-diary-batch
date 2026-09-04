package com.fiap.mindcare_diary_batch.services.consultas;


import com.fiap.mindcare_diary_batch.models.Consulta;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ConsultaJobConfig {

    @Bean
    public Job consultaNotificationJob(
            JobRepository jobRepository,
            Step consultaNotificationStep) {

        return new JobBuilder("consultaNotificationJob", jobRepository)
                .start(consultaNotificationStep)
                .build();
    }

    @Bean
    public Step consultaNotificationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Consulta> reader,
            ItemWriter<Consulta> writer) {

        return new StepBuilder("consultaNotificationStep", jobRepository)
                .<Consulta, Consulta>chunk(10)
                .reader(reader)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }
}