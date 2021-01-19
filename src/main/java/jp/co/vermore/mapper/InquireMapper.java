package jp.co.vermore.mapper;

import jp.co.vermore.entity.Inquire;
import jp.co.vermore.form.admin.InquireListForm;

import java.util.List;

public interface InquireMapper {

    int insertSelective(Inquire record);

    int updateInquire(Inquire inquire);

    int deleteInquire(Inquire inquire);

    List<Inquire> getInquireAll();

    List<Inquire> getInquireAllByCondition(InquireListForm form);

    int getInquireCountByCondition(InquireListForm form);

    int getInquireCount();

    Inquire selectById(Long id);
}