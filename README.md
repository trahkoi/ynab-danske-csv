# ynab-danske-csv
Spring Batch application to convert Danske Bank csv to Ynab format

Prerequisites:
- java 8
- maven

Usage:
```
mvn clean package
chmod u+x runJob.sh
./runJob.sh convert-to-ynab-record -inFile=<your danske csv> -outFile=<path_to_out_file : will be created>
```