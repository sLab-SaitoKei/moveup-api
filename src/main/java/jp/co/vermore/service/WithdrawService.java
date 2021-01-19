package jp.co.vermore.service;

import jp.co.vermore.entity.Withdraw;
import jp.co.vermore.mapper.WithdrawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WithdrawService
 * Created by wubin.
 * <p>
 * DateTime: 2018/05/21 23:52
 * Copyright: sLab, Corp
 */
@Service
public class WithdrawService {
    @Autowired
    private WithdrawMapper withdrawMapper;

    public int insertWithdraw(Withdraw withdraw) {
        return withdrawMapper.insertWithdraw(withdraw);
    }
}
