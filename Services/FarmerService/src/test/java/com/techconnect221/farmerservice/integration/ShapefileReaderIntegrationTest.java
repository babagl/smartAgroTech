package com.techconnect221.farmerservice.integration;
import com.techconnect221.farmerservice.service.geospatial.ShapeFileReaderService;
import com.techconnect221.farmerservice.service.helper.File.FileHelper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

import static java.sql.DriverManager.println;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ShapefileReaderIntegrationTest {

    private  ShapeFileReaderService shapeFileReaderService;


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
        }catch (Exception e){
            log.error(e.getMessage(),e);
            fail();
        }

    }

    @Test
    void shouldReadFeaturesFromZippedShapefileWithDefaultGeometry() throws IOException {
        File file = new File("src/test/resources/test.zip");

    }


    @Test
    void shouldAnalyseShapeAndReturnAttributeValues() throws IOException {
        File file = new File("src/test/resources/test.zip");
        assertTrue(file.exists(), "Le fichier test.zip est introuvable");
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            MockMultipartFile mmpf = new MockMultipartFile("file", file.getName(),"application/zip", fileInputStream);
            var response = shapeFileReaderService.analyseShape(mmpf);
            assertNotNull(response, "Le fichier test.zip est introuvable");
            assertTrue(response.isSuccess(), "Le fichier test.zip est introuvable");
            assertNotNull(response.getData(), "les donnees ne doit pas etre null");
            assertInstanceOf(List.class, response.getData(), "Les donnees doit etre une liste ");

            @SuppressWarnings("uncheked")
            var attributeValues = response.getData();

            assertFalse(attributeValues.isEmpty(), "La liste des attributs ne doit pas être vide");
            log.info("Attributs détectés : {}", attributeValues);
        }
    }


    @BeforeEach
    void setUp() {
        shapeFileReaderService = new ShapeFileReaderService();
    }

    @Test
    void shouldFindShapeFile()throws Exception {
        File zipFile = new File("src/test/resources/delimitation4.zip");
        assertTrue(zipFile.exists(), "Le fichier delimitation est introuvable");

        File extractedDir = FileHelper.unzipFile(new FileInputStream(zipFile));

        var method = ShapeFileReaderService.class.getDeclaredMethod("findShapeFiles",File.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<File> result = (List<File>) method.invoke(shapeFileReaderService, extractedDir);
        log.debug("####### Result",result);
        assertNotNull(result, "Le fichier delimitation est introuvable");
        assertFalse(result.isEmpty(), "Le fichier .shp est introuvable");
        assertTrue(result.stream().allMatch(a->a.getName().endsWith(".shp")), "Tous les fichiers doivent être des .shp");
    }



}
