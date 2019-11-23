package moe.keshane.PikaImage.Service.Impl;

import moe.keshane.PikaImage.Common.BaseUrlKey;
import moe.keshane.PikaImage.Dao.Entity.Setting;
import moe.keshane.PikaImage.Dao.Repo.SettingRepo;
import moe.keshane.PikaImage.Service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingRepo settingRepo;

    @Override
    public Setting getSettingByKey(String key) {
        return settingRepo.findSettingByKey(key);
    }

    @Override
    public String getBaseUrl() {
        Setting setting = this.getSettingByKey(BaseUrlKey.BASEURL);
        if(setting == null){
            return null;
        }
        return setting.getValue();
    }

    @Override
    public boolean setBaseUrl(String baseUrl) {
        Setting setting = this.getSettingByKey(BaseUrlKey.BASEURL);
        if(setting==null){
            setting = new Setting();
            setting.setKey(BaseUrlKey.BASEURL);
        }
        setting.setValue(baseUrl);
        settingRepo.save(setting);
        return true;
    }
}
