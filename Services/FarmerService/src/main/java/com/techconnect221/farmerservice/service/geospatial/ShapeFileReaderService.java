package com.techconnect221.farmerservice.service.geospatial;

import com.techconnect221.farmerservice.dto.Response.FarmerServiceResponse;
import com.techconnect221.farmerservice.service.helper.File.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.geotools.data.*;
import org.geotools.data.simple.*;

import java.io.File;
import java.util.*;

@Slf4j
@Service
public class ShapeFileReaderService {

    public FarmerServiceResponse<Map<String, Object>> analyseShape(MultipartFile multipartFile) {
        var reponse = new FarmerServiceResponse<Map<String, Object>>();
        try {
            var features = readFeatures(multipartFile);
            if (features.isEmpty()){
                reponse.setSuccess(false);
                reponse.setMessage("Aucune entites trouve dans le shape");
                reponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
                reponse.setData(null);
                return FarmerServiceResponse.error("Le fichier test.zip est introuvable");
            }
            SimpleFeature sample = features.get(0);
            SimpleFeatureType featureType = sample.getFeatureType();
            List<String> attributes = new ArrayList<>();
            featureType.getAttributeDescriptors().forEach(attributeDescriptor -> {
                attributes.add(attributeDescriptor.getLocalName());
            });
            List<Map<String, Object>> simpleData = new ArrayList<>();
            for (int i = 0; i < Math.min(features.size(), 5); i++) {
                SimpleFeature f = features.get(i);
                Map<String, Object> row = new LinkedHashMap<>();
                f.getProperties().forEach(p-> row.put(p.getName().toString(), p.getValue()));
                simpleData.add(row);
            }

            String geometryType =  sample.getDefaultGeometry().getClass().getSimpleName() ;
            Map<String, Object> attributesMap = new LinkedHashMap<>();
            attributesMap.put("attributes", attributes);
            attributesMap.put("geometryType", geometryType);
            attributesMap.put("count", features.size());
            attributesMap.put("sampleData", simpleData);

            reponse.setSuccess(true);
            reponse.setData(attributesMap);
            reponse.setMessage("Analyse réussie");
            reponse.setStatus(HttpStatus.ACCEPTED);
            reponse.setDebugMessage(null);

            return reponse;
        }catch (Exception e){
            log.error("Erreur lors de l'analyse du shapefile", e);
            reponse.setSuccess(false);
            reponse.setDebugMessage(e.getMessage());
            reponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
            reponse.setData(null);
            return reponse;
        }
    }


    public List<SimpleFeature> readFeatures(MultipartFile zipFile){
        List<SimpleFeature> features = new ArrayList<>();
        try {
            File extractedFile = FileHelper.unzipFile(zipFile.getInputStream());
            List<File> shapeFiles = findShapeFiles(extractedFile);
            if (shapeFiles.isEmpty()) throw new Exception("No shape files found");

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
            log.info(" Total des entités combinées : {}", features);
            return features;
        }catch (Exception e){
            log.error("Erreur lors de la lecture du shapefile",e.getMessage());
            throw new RuntimeException("Erreur lors de la lecture");
        }
    }

    private List<File> findShapeFiles(File directory){
        List<File> shapeFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null ){
            for (File file: files){
                if (file.isDirectory()) shapeFiles.addAll(findShapeFiles(file));
                else if (file.getName().toLowerCase().endsWith(".shp")) shapeFiles.add(file);
            }
        }
        return shapeFiles;
    }
}
