package com.task.service;

import com.task.entity.Feature;
import com.task.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    public Optional<Feature> getFeatureById(Integer featureId) {
        return featureRepository.findById(featureId);
    }

    public Feature createFeature(Feature feature) {
        // Add any additional validation or business logic before saving
        return featureRepository.save(feature);
    }

    public Feature updateFeature(Integer featureId, Feature updatedFeature) {
        // Add logic to check if the feature with featureId exists before updating
        // Add any additional validation or business logic before updating
        updatedFeature.setFeatureId(featureId);
        return featureRepository.save(updatedFeature);
    }

    public void deleteFeature(Integer featureId) {
        // Add logic to check if the feature with featureId exists before deleting
        featureRepository.deleteById(featureId);
    }
}
