package jp.co.vermore.service;

import jp.co.vermore.common.Constant;
import jp.co.vermore.entity.Inquire;
import jp.co.vermore.form.admin.InquireForm;
import jp.co.vermore.form.admin.InquireListForm;
import jp.co.vermore.mapper.InquireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * InquireService
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/04/04 19:19
 * Copyright: sLab, Corp
 */

@Service
public class InquireService {

    @Autowired
    private InquireMapper inquireMapper;

    public int addInquire(Inquire inquire) {
        return inquireMapper.insertSelective(inquire);
    }

    public List<Inquire> getInquireAll() {
        List<Inquire> inquireList = inquireMapper.getInquireAll();
        return inquireList;
    }

    public List<Inquire> getInquireAllByCondition(InquireListForm form) {
        List<Inquire> detail = inquireMapper.getInquireAllByCondition(form);
        return detail;
    }

    public int getInquireCountByCondition(InquireListForm form) {
        return inquireMapper.getInquireCountByCondition(form);
    }

    public int getInquireCount() {
        return inquireMapper.getInquireCount();
    }

    public int updateInquire(InquireForm form) {
        Inquire inquire = new Inquire();
        inquire.setId(form.getId());
        if(form.getStatus()==null || form.getStatus()==0){
            inquire.setStatus((byte) Constant.INQUIRE_STATUS.INQUIRE_STATUS_UNFINISH);
        }else{
            inquire.setStatus((byte) Constant.INQUIRE_STATUS.INQUIRE_STATUS_DONE);
        }
        inquire.setUpdateDatetime(new Date(System.currentTimeMillis()));
        return inquireMapper.updateInquire(inquire);
    }

    public int deleteInquire(InquireForm form) {
        Inquire inquire = new Inquire();
        inquire.setId(form.getId());
        inquire.setDelFlg(Boolean.TRUE);
        int count = inquireMapper.deleteInquire(inquire);
        return count;
    }

    public Inquire selectById(Long id) {
        return inquireMapper.selectById(id);
    }
}
