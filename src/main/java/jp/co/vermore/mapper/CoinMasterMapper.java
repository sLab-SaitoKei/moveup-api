package jp.co.vermore.mapper;

import jp.co.vermore.entity.CoinMaster;

import java.util.List;


public interface CoinMasterMapper {

    List<CoinMaster> getCoinMaster();

    CoinMaster getCoinMasterList(Integer coinId, Byte coinType);

}