package fi.rahkoi.ynab.processor;

import fi.rahkoi.ynab.DanskeRecord;
import fi.rahkoi.ynab.YnabRecord;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by terorahko on 30/06/2017.
 */
public class DanskeRecordYnabRecordProcessor implements ItemProcessor<DanskeRecord, YnabRecord> {
    @Override
    public YnabRecord process(DanskeRecord item) throws Exception {
        return convert(item);
    }

    private YnabRecord convert(DanskeRecord danskeRecord) {
        YnabRecord ynabRecord = new YnabRecord();
        ynabRecord.setDate(danskeRecord.getDate());
        if (danskeRecord.getAmount() < 0) {
            ynabRecord.setOutflow(danskeRecord.getAmount() * -1);
        } else {
            ynabRecord.setInflow(danskeRecord.getAmount());
        }
        ynabRecord.setPayee(danskeRecord.getPayee());
//        ynabRecord.setMemo("Imported from Danske Bank");
        return ynabRecord;
    }
}
