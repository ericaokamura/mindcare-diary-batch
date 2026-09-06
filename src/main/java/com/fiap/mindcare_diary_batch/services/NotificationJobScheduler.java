package com.fiap.mindcare_diary_batch.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationJobScheduler {

    private static final String TIME_ZONE = "America/Sao_Paulo";

    private final JobLauncher jobLauncher;

    private final Job consultaNotificationJob;

    private final Job prescriptionNotificationJob;

    @Scheduled(cron = "0 32 19 * * *", zone = TIME_ZONE)
    public void runDailyNotificationJobs() {

        LocalDate today = LocalDate.now(ZoneId.of(TIME_ZONE));

        startJob(consultaNotificationJob, today);

        startJob(prescriptionNotificationJob, today);
    }

    private void startJob(Job job, LocalDate date) {

        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addString("run.date", date.toString(), true)
                            .toJobParameters();

            JobExecution execution =
                    jobLauncher.run(job, jobParameters);

            log.info(
                    "Started job '{}' with execution id {}",
                    job.getName(),
                    execution.getId()
            );

        } catch (Exception exception) {

            log.error(
                    "Failed to start job '{}'",
                    job.getName(),
                    exception
            );
        }
    }
}