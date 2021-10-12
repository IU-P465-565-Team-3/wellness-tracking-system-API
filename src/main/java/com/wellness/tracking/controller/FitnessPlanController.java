package com.wellness.tracking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.wellness.tracking.model.FitnessPlan;
import com.wellness.tracking.repository.FitnessPlanRepository;


import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessPlanController {

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    /**
     * Create a fitness plan.
     * 
     * @return the fitness plan
     */
    @GetMapping("/fitness_plan_")
    public FitnessPlan createFitnessPlan(@Valid @RequestBody FitnessPlan fitnessPlan) {
        return fitnessPlanRepository.save(fitnessPlan);
    }

    /**
     * Get all Fitness Plans list.
     * 
     * @return the list
     */
    @GetMapping("/fitness_plan_")
    public List<FitnessPlan> getAllFitnessPlans() {
        return fitnessPlanRepository.findAll();
    }

    /**
     * Update the fitness plan.
     * 
     * @return the response entity
     */
    public ResponseEntity<FitnessPlan> updateFitnessPlan(
        @PathVariable(value = "id") Long id, @Valid @RequestBody FitnessPlan updatedDetails)
        throws ResourceNotFoundException {
            
            FitnessPlan fitnessPlan = fitnessPlanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fitness Plan not found on ::" + id)); 

            fitnessPlan.setDescription(updatedDetails.getDescription());
            fitnessPlan.setImageUrl(updatedDetails.getImageUrl());
            fitnessPlan.setImageAnnotation(updatedDetails.getImageAnnotation());

            final FitnessPlan updatedFitnessPlan = fitnessPlanRepository.save(fitnessPlan);

            return ResponseEntity.ok(updatedFitnessPlan);            
    }

    /**
     * Deletes the given fitness plan.
     * 
     * @return the map
     */
    public Map<String, Boolean> deleteFitnessPlan(@PathVariable(value ="id") Long id) throws Exception {
        FitnessPlan fitnessPlan = fitnessPlanRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Fitness plan not found on :: " + id));

        fitnessPlanRepository.delete(fitnessPlan);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}