package fi.rahkoi.ynab.writer;

import fi.rahkoi.ynab.YnabRecord;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by terorahko on 30/06/2017.
 */
public class YnabRecordItemWriter implements ItemWriter<YnabRecord> {
    @Override
    public void write(List<? extends YnabRecord> items) throws Exception {
        items.forEach(System.out::println);
    }
}
