#!/usr/bin/env bash
java -jar target/danske-ynab-csv-converter-0.1.0.jar --spring.batch.job.names=$1 $2 $3 $4