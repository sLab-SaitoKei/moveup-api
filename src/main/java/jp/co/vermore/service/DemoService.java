package jp.co.vermore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.Demo;
import jp.co.vermore.mapper.DemoMapper;

/**
 * Created by xieyoujun on 2017/11/20.
 */
@Service
public class DemoService {
    @Autowired
    private DemoMapper demoMapper;

    public Demo getDemo(Integer id) {
        Demo entity = demoMapper.getDemo(id);
        return entity;
    }
    public List<Demo> getDemoAll() {
        List<Demo> demoList = demoMapper.getDemoAll();
        return demoList;
    }

    public Demo getDemoByEntity(Demo demoEntity) {
        Demo entity = demoMapper.getDemoByEntity(demoEntity);
        return entity;
    }

    public int insertDemo(String name) {
        int count = demoMapper.insertDemo(name);
        return count;
    }

    public int deleteDemo(long id) {
        int count = demoMapper.deleteDemo(id);
        return count;
    }

    public int updateDemo(Demo dto) {
        int count = demoMapper.updateDemo(dto);
        return count;
    }
}
