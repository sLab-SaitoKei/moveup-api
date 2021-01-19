package jp.co.vermore.service;

import jp.co.vermore.entity.SystemConf;
import jp.co.vermore.mapper.SystemConfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SystemConfService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/29 16:21
 * Copyright: sLab, Corp
 */

@Service
public class SystemConfService {

    @Autowired
    private SystemConfMapper systemConfMapper;

    public List<SystemConf> getSystemConfAll() {
        return systemConfMapper.selectAll();
    }

    public SystemConf getSystemConfByKey(int setting) {
        return systemConfMapper.getSystemConfByKey(setting);
    }
}
