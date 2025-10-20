package com.techconnect221.farmerservice.unit.service;

import com.techconnect221.farmerservice.service.helper.File.FileHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileHelperTest {

    @Test
    void shouldUnzipFileCorrectly() {
        File file = new File("src/test/resources/delimitation4.zip");
        assertTrue(file.exists(), "File should exist");
        try(FileInputStream fis = new FileInputStream(file)) {
            File outputDir = FileHelper.unzipFile(fis);
            assertNotNull(outputDir);
            assertTrue(outputDir.isDirectory());
            assertTrue(Files.list(outputDir.toPath()).anyMatch(p->p.toString().endsWith(".shp")),"Aucun fichier .shp trouvé après décompression");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
