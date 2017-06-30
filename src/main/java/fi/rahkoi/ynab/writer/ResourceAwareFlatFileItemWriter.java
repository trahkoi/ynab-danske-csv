package fi.rahkoi.ynab.writer;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by terorahko on 30/06/2017.
 */
public class ResourceAwareFlatFileItemWriter<T> extends FlatFileItemWriter<T> implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String outFilePath = stepExecution.getJobExecution().getJobParameters().getString("outFile");
        FileSystemResource resource = new FileSystemResource(outFilePath);
        setResource(resource);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
