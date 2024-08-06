package seung.springbatch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import seung.springbatch.entity.BeforeEntity;
import seung.springbatch.repository.BeforeRepository;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class FifthBatch {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BeforeRepository beforeRepository;


    @Bean
    public Job fifthJob() {
        System.out.println("FifthBatch.fifthJob");

        return new JobBuilder("fifthJob",jobRepository)
                .start(fifthStep())
                .build();
    }

    @Bean
    public Step fifthStep() {
        return new StepBuilder("fifthStep",jobRepository)
                .<BeforeEntity, BeforeEntity> chunk(10,platformTransactionManager)
                .reader(fifthBeforeReader())
                .processor(fifthProcessor())
                .writer(excelWriter())
                .build();
    }

    @Bean
    public ItemStreamReader<BeforeEntity> fifthBeforeReader() {
        RepositoryItemReader<BeforeEntity> reader = new RepositoryItemReaderBuilder<BeforeEntity>()
                .name("beforeReader")
                .pageSize(10)
                .methodName("findAll")
                .repository(beforeRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
        //전체 데이터 셋에서 어디까지 수행했는지 값을 저장하지 않음
        //중간에 실패했을때 true이면 이어서 진행하지만 false로 하면 처음부터 다시 처리
        reader.setSaveState(false);
        return reader;
    }

    @Bean
    public ItemProcessor<BeforeEntity, BeforeEntity> fifthProcessor() {
        return item -> item;
    }

    @Bean
    public ItemStreamWriter<BeforeEntity> excelWriter() {
        try{
            return new ExcelRowWriter("/Users/sean/temp/test1.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
