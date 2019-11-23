package moe.keshane.PikaImage.Dao.Repo;

import moe.keshane.PikaImage.Dao.Entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepo extends JpaRepository<Setting,Integer> {
    Setting findSettingByKey(String key);
}
