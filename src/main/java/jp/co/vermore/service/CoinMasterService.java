package jp.co.vermore.service;

import jp.co.vermore.entity.CoinMaster;
import jp.co.vermore.mapper.CoinMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinMasterService {

    @Autowired
    private CoinMasterMapper coinMasterMapper;

    public CoinMaster getCoinMasterList(Integer coinId, Byte coinType) {
        return coinMasterMapper.getCoinMasterList(coinId, coinType);
    }

}
