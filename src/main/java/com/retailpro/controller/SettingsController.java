package com.retailpro.controller;

import com.retailpro.model.Settings;
import com.retailpro.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Settings>> getAllSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

    @GetMapping("/{key}")
    public ResponseEntity<Settings> getSetting(@PathVariable String key) {
        return ResponseEntity.ok(settingsService.getSetting(key));
    }

    @PutMapping("/{key}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Settings> updateSetting(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(settingsService.updateSetting(key, body.get("value")));
    }
}