package com.retailpro.service;

import com.retailpro.model.Settings;
import com.retailpro.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SettingsService {

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public List<Settings> getAllSettings() {
        return settingsRepository.findAll();
    }

    public Settings getSetting(String key) {
        return settingsRepository.findBySettingKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
    }

    public Settings updateSetting(String key, String value) {
        Settings setting = settingsRepository.findBySettingKey(key)
                .orElse(new Settings());
        setting.setSettingKey(key);
        setting.setSettingValue(value);
        return settingsRepository.save(setting);
    }
}