package com.techconnect221.farmerservice.integration;
import com.techconnect221.farmerservice.service.geospatial.ShapeFileReaderService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@AllArgsConstructor
public class ShapefileReaderIntegrationTest {
    @Autowired
    private ShapeFileReaderService shapeFileReaderService;

//    private final ShapeFileReaderService shapeFileReaderService;

    @Test
    void shouldReadFeaturesFromZippedShapefile() throws IOException {
        File file = new File("src/test/resources/test.zip");
        assertTrue(file.exists(),"Le fichier test.zip est introuvable");
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(),"application/zip", fileInputStream);

            List<SimpleFeature> simpleFeatures = shapeFileReaderService.readFeatures(multipartFile);

            assertNotNull(simpleFeatures, "La liste des features ne doit pas etre null");
            //quand on dit entites on fait allusion aux geom et au attribut associe a cette entites (@Ousseynou )
            assertFalse(simpleFeatures.isEmpty(), "Aucune entites est trouve dans le shapefile");
            assertNotNull(simpleFeatures.get(0).getDefaultGeometry(), "Aucune geom trouve dans le shapefile");
            simpleFeatures.forEach(System.out::println);
        }catch (Exception e){

        }

    }


}
