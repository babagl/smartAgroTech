package com.techconnect221.farmerservice.service.geospatial;

import com.techconnect221.farmerservice.service.helper.File.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.web.multipart.MultipartFile;
import org.geotools.data.*;
import org.geotools.data.simple.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ShapeFileReaderService {

    public List<SimpleFeature> readFeatures(MultipartFile zipFile){
        List<SimpleFeature> features = new ArrayList<>();
        try {
            File extractedFile = FileHelper.unzipFile(zipFile.getInputStream());
            File[] shapeFiles = extractedFile.listFiles((dir, name) -> name.toLowerCase().endsWith(".shp"));
            if (shapeFiles == null || shapeFiles.length == 0) throw new Exception("No shape files found");

            for (File shapeFile : shapeFiles) {
                log.info("Reading shape file {}", shapeFile.getName());
                Map<String , Object> params = Map.of("url", shapeFile.toURI().toURL());
                DataStore dataStore = null;
                try {
                    dataStore = DataStoreFinder.getDataStore(params);
                    String typeName = dataStore.getTypeNames()[0];
                    log.info("Reading data store", dataStore);
                    log.info("Reading type", typeName);
                    SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
                    log.info("Reading feature source", source);
                    SimpleFeatureCollection collection = source.getFeatures();
                    log.info("Reading feature collection", collection);

                    int count = 0;
                    try(SimpleFeatureIterator iterator = collection.features()) {
                        while (iterator.hasNext()) {
                            features.add(iterator.next());
                            count++;
                        }
                    }
                }catch (Exception e) {
                    log.error("Erreur de lecture du shapefile {}", shapeFile.getName(), e);
                }finally {
                    if (dataStore != null){
                        dataStore.dispose();
                    }
                }
            }

            log.info("📊 Total des entités combinées : {}", features);
            return features;
        }catch (Exception e){
            log.error("Erreur lors de la lecture du shapefile",e.getMessage());
            throw new RuntimeException("Erreur lors de la lecture");
        }
    }
}
