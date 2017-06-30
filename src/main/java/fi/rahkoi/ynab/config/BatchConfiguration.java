package fi.rahkoi.ynab.config;

import fi.rahkoi.ynab.DanskeRecord;
import fi.rahkoi.ynab.YnabRecord;
import fi.rahkoi.ynab.extractor.YnabRecordFieldExtractor;
import fi.rahkoi.ynab.mapper.DanskeRecordFieldSetMapper;
import fi.rahkoi.ynab.processor.DanskeRecordFilterProcessor;
import fi.rahkoi.ynab.processor.DanskeRecordYnabRecordProcessor;
import fi.rahkoi.ynab.reader.ResourceAwareFlatFileItemReader;
import fi.rahkoi.ynab.writer.DanskeRecordItemWriter;
import fi.rahkoi.ynab.writer.ResourceAwareFlatFileItemWriter;
import fi.rahkoi.ynab.writer.YnabRecordItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terorahko on 30/06/2017.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<DanskeRecord> danskeRecordFlatFileItemReader() {
        FlatFileItemReader<DanskeRecord> itemReader = new ResourceAwareFlatFileItemReader<>();
        itemReader.setEncoding("iso-8859-1");
        itemReader.setLinesToSkip(1);

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"Pvm", "Saaja/Maksaja", "Määrä", "Saldo", "Tila", "Tarkastus"});

        FieldSetMapper<DanskeRecord> fieldSetMapper = new DanskeRecordFieldSetMapper();

        DefaultLineMapper<DanskeRecord> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        itemReader.setLineMapper(lineMapper);

        return itemReader;
    }

    @Bean
    public DanskeRecordFilterProcessor danskeRecordFilterProcessor() {
        return new DanskeRecordFilterProcessor();
    }

    @Bean
    public DanskeRecordYnabRecordProcessor danskeRecordYnabRecordProcessor() {
        return new DanskeRecordYnabRecordProcessor();
    }

    @Bean
    public ItemProcessor<DanskeRecord, YnabRecord> danskeRecordFilteringYnabRecordProcessor() {
        CompositeItemProcessor<DanskeRecord, YnabRecord> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<?,?>> processors = new ArrayList<>();
        processors.add(danskeRecordFilterProcessor());
        processors.add(danskeRecordYnabRecordProcessor());
        processor.setDelegates(processors);
        return processor;
    }

    @Bean
    public DanskeRecordItemWriter danskeRecordItemWriter() {
        return new DanskeRecordItemWriter();
    }

    @Bean
    public YnabRecordItemWriter ynabRecordItemWriter() {
        return new YnabRecordItemWriter();
    }

    @Bean
    public FlatFileItemWriter<YnabRecord> ynabRecordFlatFileItemWriter() {
        YnabRecordFieldExtractor fieldExtractor = new YnabRecordFieldExtractor();

        DelimitedLineAggregator<YnabRecord> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<YnabRecord> writer = new ResourceAwareFlatFileItemWriter<>();
        writer.setHeaderCallback(cp -> cp.write("Date,Payee,Category,Memo,Outflow,Inflow"));
        writer.setLineAggregator(lineAggregator);
        writer.setShouldDeleteIfEmpty(true);
        return writer;
    }

    @Bean
    public Step printDanskeRecordStep() {
        return stepBuilderFactory.get("print-danske-record-step")
                .<DanskeRecord, DanskeRecord> chunk(10)
                .reader(danskeRecordFlatFileItemReader())
                .writer(danskeRecordItemWriter())
                .build();
    }

    @Bean
    public Step printYnabRecordStep() {
        return stepBuilderFactory.get("print-ynab-record-step")
                .<DanskeRecord, YnabRecord> chunk(10)
                .reader(danskeRecordFlatFileItemReader())
                .processor(danskeRecordYnabRecordProcessor())
                .writer(ynabRecordItemWriter())
                .build();
    }

    @Bean
    public Step writeYnabRecordStep() {
        return stepBuilderFactory.get("write-ynab-record-step")
                .<DanskeRecord, YnabRecord> chunk(10)
                .reader(danskeRecordFlatFileItemReader())
                .processor(danskeRecordFilteringYnabRecordProcessor())
                .writer(ynabRecordFlatFileItemWriter())
                .build();
    }

    @Bean
    public Job printDanskeRecordJob() {
        return jobBuilderFactory.get("print-danske-record")
                .flow(printDanskeRecordStep())
                .end()
                .validator(new DefaultJobParametersValidator(new String [] { "inFile" }, new String[0]))
                .build();
    }


    @Bean
    public Job printYnabRecordJob() {
        return jobBuilderFactory.get("print-ynab-record")
                .flow(printYnabRecordStep())
                .end()
                .validator(new DefaultJobParametersValidator(new String[] { "inFile" }, new String[0]))
                .build();
    }

    @Bean
    public Job writeYnabRecordJob() {
        return jobBuilderFactory.get("convert-to-ynab-record")
                .flow(writeYnabRecordStep())
                .end()
                .validator(new DefaultJobParametersValidator(new String[] { "inFile", "outFile"}, new String[0]))
                .build();
    }
}
