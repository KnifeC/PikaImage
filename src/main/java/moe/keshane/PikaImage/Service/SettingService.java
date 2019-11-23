package moe.keshane.PikaImage.Service;

import moe.keshane.PikaImage.Dao.Entity.Setting;

public interface SettingService {
    Setting getSettingByKey(String key);
    String getBaseUrl();
    boolean setBaseUrl(String baseUrl);
}
