package com.adashkevich.transport_by_parser.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtil {

    public static <T> List<T> read(Path path, Class<T> clazz) throws Exception {
        HeaderColumnNameMappingStrategy<T> ms = new HeaderColumnNameMappingStrategy<>();
//        ms.setType(clazz);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
//                .withType(clazz)
                .withMappingStrategy(ms)
                .build();

        return csvToBean.parse();
    }
}
