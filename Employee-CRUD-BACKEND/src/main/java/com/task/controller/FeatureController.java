package com.task.controller;

import com.task.entity.Feature;
import com.task.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/features")
public class FeatureController {

    private final FeatureService featureService;

    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public List<Feature> getAllFeatures() {
        return featureService.getAllFeatures();
    }

    @GetMapping("/{featureId}")
    public ResponseEntity<Feature> getFeatureById(@PathVariable Integer featureId) {
        Optional<Feature> feature = featureService.getFeatureById(featureId);
        return feature.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        Feature createdFeature = featureService.createFeature(feature);
        return new ResponseEntity<>(createdFeature, HttpStatus.CREATED);
    }

    @PutMapping("/{featureId}")
    public ResponseEntity<Feature> updateFeature(@PathVariable Integer featureId, @RequestBody Feature updatedFeature) {
        Optional<Feature> feature = featureService.getFeatureById(featureId);
        if (feature.isPresent()) {
            Feature result = featureService.updateFeature(featureId, updatedFeature);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{featureId}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Integer featureId) {
        Optional<Feature> feature = featureService.getFeatureById(featureId);
        if (feature.isPresent()) {
            featureService.deleteFeature(featureId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
