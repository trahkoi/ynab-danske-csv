package fi.rahkoi.ynab.mapper;

import fi.rahkoi.ynab.DanskeRecord;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by terorahko on 30/06/2017.
 */
public class DanskeRecordFieldSetMapper implements FieldSetMapper<DanskeRecord> {

    @Override
    public DanskeRecord mapFieldSet(FieldSet fieldSet) throws BindException {
            DanskeRecord record = new DanskeRecord();
            record.setDate(fieldSet.readDate("Pvm", "dd.MM.yyyy"));
            record.setPayee(fieldSet.readString("Saaja/Maksaja"));
            record.setAmount(parseDouble(fieldSet.readString("Määrä")));
            record.setState(fieldSet.readString("Tila"));
            record.setChecked(fieldSet.readString("Tarkastus"));
            return record;

    }

    private Double parseDouble(String str) {
        return Double.parseDouble(str.replaceAll(",", ".").replaceAll("\\s+", ""));
    }
}
