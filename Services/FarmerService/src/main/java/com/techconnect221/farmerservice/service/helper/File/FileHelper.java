package com.techconnect221.farmerservice.service.helper.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHelper {

    /**
     *
     * decompresse un fichier zipe recu par le multipartfile
     * @param inputStream
     * @return le repertoire temporaire contenant le fichiers decompresses
     */
    public static File unzipFile(InputStream inputStream) throws IOException {
        File tempDire = Files.createTempDirectory("import_shp").toFile();
        try (ZipInputStream zis = new ZipInputStream(inputStream)){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(tempDire, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                }else {
                    file.getParentFile().mkdirs();
                    try(FileOutputStream fos = new FileOutputStream(file)) {
                        zis.transferTo(fos);
                    }
                }
                zis.closeEntry();
            }

        }
        return tempDire;
    }
}
