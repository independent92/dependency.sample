package dependency.sample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Bean
    public Job importUserJob(JobBuilderFactory jobs) throws Exception {
        return jobs.get("importUserJob")
                .repository(createJobRepository())
                .start(customStep1())
                .next(customStep2())
                .build();
    }

    @Bean
    public Step customStep1() throws Exception {
        AbstractStep a = new AbstractStep() {
            @Override
            protected void doExecute(StepExecution stepExecution) {
                System.out.println("customStep1 executing..."
                                   + stepExecution.getJobParameters().getString("time"));

                stepExecution.getJobExecution().getExecutionContext().put("customStep1", "customStep1PutParameter");
            }
        };
        a.setJobRepository(createJobRepository());

        return a;
    }

    @Bean
    public Step customStep2() throws Exception {
        AbstractStep a = new AbstractStep() {
            @Override
            protected void doExecute(StepExecution stepExecution) {
                System.out.println("customStep2 executing...; customStep1result="
                                   + stepExecution.getJobExecution().getExecutionContext().get("customStep1"));
            }
        };
        a.setJobRepository(createJobRepository());

        return a;
    }
}
