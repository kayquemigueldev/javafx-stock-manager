package com.kayque.stockmanager.javafxstockmanager.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupBanco {

    public static boolean gerarBackup() {

        try {

            String data = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            String arquivo = System.getProperty("user.home")
                    + "/Desktop/backup_stock_manager_" + data + ".sql";

            ProcessBuilder pb = new ProcessBuilder(

                    "/Applications/XAMPP/xamppfiles/bin/mysqldump",

                    "-u",
                    "root",

                    "stock_manager",

                    "-r",
                    arquivo

            );

            Process process = pb.start();

            int retorno = process.waitFor();

            return retorno == 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }

}