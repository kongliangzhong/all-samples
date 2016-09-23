package cn.klzhong.samples.springdata.repo;

import org.springframework.data.repository.Repository;
import cn.klzhong.samples.springdata.entity.*;

public interface LogRepo extends Repository<Log, Long> {
    Log save(Log log);
}
