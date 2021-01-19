package jp.co.vermore.mapper;

import jp.co.vermore.entity.PurchaseInfo;

import java.util.List;

public interface PurchaseInfoMapper {

    int insert(PurchaseInfo record);

    List<PurchaseInfo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(PurchaseInfo record);

    PurchaseInfo selectBySerialNumber(String serialNumber);
}