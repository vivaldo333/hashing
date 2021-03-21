package com.ks.hashing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GenerateInitDataSql {
    private static final String NEW_LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) throws IOException {
        System.out.println("Start setup.sql creating");
        String absoluteFilePath = "D:\\ITEA\\_PROJECT\\hashing\\src\\main\\resources\\sql\\init-data.sql";
        long initPhoneNumber = 380000000000L;
        int countOfRowsInDb = 50_000_000;
        int chunkSize = 5_000_000;

        for (int j = 0; j < countOfRowsInDb / chunkSize; j++) {
            StringBuilder text = new StringBuilder();
            int k = j * chunkSize;
            int maxChunkSize = k + chunkSize;
            for (int i = k; i < maxChunkSize; i++) {
                System.out.println(i);
                text.append(getSqlInsetStatement(initPhoneNumber, i));
            }
            byte[] textByteArray = text.toString().getBytes();
            System.out.println("SQL text was generated with length: " + textByteArray.length);
            Files.write(Paths.get(absoluteFilePath), textByteArray, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            text = null;
            textByteArray = null;
            System.gc();
        }


        System.out.println("End setup.sql creating");
    }

    private static String getSqlInsetStatement(long initPhoneNumber, int idx) {
        return "INSERT INTO KS.PHONES (PHONE) VALUES ('" + (initPhoneNumber + idx) + "');" + NEW_LINE_SEPARATOR;
    }
}
