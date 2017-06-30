package fi.rahkoi.ynab.writer;

import fi.rahkoi.ynab.DanskeRecord;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by terorahko on 30/06/2017.
 */
public class DanskeRecordItemWriter implements ItemWriter<DanskeRecord> {
    @Override
    public void write(List<? extends DanskeRecord> items) throws Exception {
        items.forEach(System.out::println);
    }
}
