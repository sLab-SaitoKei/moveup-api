package jp.co.vermore.service;



import jp.co.vermore.entity.Settlement;
import jp.co.vermore.form.admin.SettlementEditForm;
import jp.co.vermore.form.admin.SettlementListForm;
import jp.co.vermore.mapper.SettlementMapper;
import jp.co.vermore.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyan on 2018/06/13.
 */
@Service
public class SettlementService {
    @Autowired
    private SettlementMapper settlementMapper;

    @Autowired
    private ShopMapper shopMapper;


    public void setSettlementMapper(SettlementMapper settlementMapper) {
        this.settlementMapper = settlementMapper;
    }

    public void setShopMapper(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public Long insertSettlement(Settlement settlement){
        settlementMapper.insert(settlement);
        return settlement.getId();
    }

    public List<SettlementListForm> getSettlementAllByCondition(SettlementListForm form) {
        return settlementMapper.getSettlementAllByMonth(form);
    }

    public List<SettlementListForm> getSettlementAllByMonthForNotify(int month) {
        return settlementMapper.getSettlementAllByMonthForNotify(month);
    }

    public int getSettlementCountByCondition(SettlementListForm form) {
        return settlementMapper.getSettlementCountByMonth(form);
    }

    public int getSettlementCount() {
        return settlementMapper.getSettlementCount();
    }

    public List<SettlementEditForm> getSettlementEditByMonth(SettlementEditForm form) {
        if(form.getMon() != "" && form.getMon() != null){
            form.setMonth(Integer.valueOf((form.getMon().replace("-",""))));
        }
        form.setShopID(shopMapper.getShopIdByUUID(form.getShopUUID()));
        return settlementMapper.getSettlementEditByMonth(form);
    }
    public List<SettlementEditForm> getSettlementEditByMonth2(Long shopId,int month) {
        return settlementMapper.getSettlementEditByMonth2(shopId,month);
    }
}
