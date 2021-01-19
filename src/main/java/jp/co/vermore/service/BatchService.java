package jp.co.vermore.service;


import jp.co.vermore.entity.BatchStatus;
import jp.co.vermore.mapper.BatchStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyan on 2018/06/13.
 */
@Service
public class BatchService {
    @Autowired
    private BatchStatusMapper batchStatusMapper;

    public List<BatchStatus> getBatchStatus(Byte batchType, Byte status){
        return batchStatusMapper.getBatchStatus(batchType,status);
    }

    public Long insertBatch(BatchStatus batchStatus){
        batchStatusMapper.insert(batchStatus);
        return batchStatus.getId();
    }

    public Long UpdateBatch(BatchStatus batchStatus){
        batchStatus.setUpdateDatetime(new Date(System.currentTimeMillis()));
        batchStatusMapper.updateByPrimaryKeySelective(batchStatus);
        return batchStatus.getId();
    }

}
