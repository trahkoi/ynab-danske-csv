package fi.rahkoi.ynab.extractor;

import fi.rahkoi.ynab.YnabRecord;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.datetime.DateFormatter;

import java.util.Date;

/**
 * Created by terorahko on 30/06/2017.
 */
public class YnabRecordFieldExtractor implements FieldExtractor<YnabRecord> {

    DateFormatter dateFormatter = new DateFormatter("dd/MM/yyyy");

    @Override
    public Object[] extract(YnabRecord item) {
        return new Object[] {
                printDate(item.getDate()), item.getPayee(), item.getCategory(),
                item.getMemo(), item.getOutflow(), item.getInflow()
        };
    }

    private String printDate(Date date) {
        return dateFormatter.print(date, LocaleContextHolder.getLocale());
    }
}
