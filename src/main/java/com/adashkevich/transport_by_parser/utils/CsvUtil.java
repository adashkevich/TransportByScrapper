package com.adashkevich.transport_by_parser.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtil {

    public static <T> List<T> read(Path path, Class<T> clazz) throws Exception {
        HeaderColumnNameMappingStrategy<T> ms = new HeaderColumnNameMappingStrategy<>();
        ms.setType(clazz);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
//                .withType(clazz)
                .withMappingStrategy(ms)
                .build();

        return csvToBean.parse();
    }

    public static <T> void write(List<T> items, Path path) throws Exception {
        try(Writer writer  = new FileWriter(path.toString())) {
            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withApplyQuotesToAll(true)
                    .build();

            sbc.write(items);
        }
    }
}
