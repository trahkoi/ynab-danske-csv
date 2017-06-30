package fi.rahkoi.ynab.processor;

import fi.rahkoi.ynab.DanskeRecord;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by terorahko on 30/06/2017.
 */
public class DanskeRecordFilterProcessor implements ItemProcessor<DanskeRecord, DanskeRecord> {

    @Override
    public DanskeRecord process(DanskeRecord item) throws Exception {
        if (item.getPayee().equals("Varaus")) {
            return null;
        }
        return item;
    }
}
