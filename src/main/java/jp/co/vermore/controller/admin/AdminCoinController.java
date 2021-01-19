package jp.co.vermore.controller.admin;

import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.CoinHistory;
import jp.co.vermore.entity.CoinMaster;
import jp.co.vermore.form.admin.CoinDetailForm;
import jp.co.vermore.form.admin.CoinListForm;
import jp.co.vermore.service.CoinMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jp.co.vermore.service.CoinHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * AdminController
 * Created by LiGuo.
 * <p>
 * DateTime: 2018/05/08 12:10
 * Copyright: sLab, Corp
 */

@Controller
public class AdminCoinController extends BaseController {

    @Autowired
    private CoinHistoryService coinHistoryService;

    @Autowired
    private CoinMasterService coinMasterService;

    // Customer admin list
    @RequestMapping(value = "/admin/coin/list/", method = RequestMethod.GET)
    public String coinList(Model model,HttpServletRequest request) {
        return "admin/coinList";
    }

    @RequestMapping(value = "/admin/coin/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject coinList(@RequestBody CoinListForm form) {
        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("user.id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        // query data
        List<CoinHistory> dataList = coinHistoryService.getCoinHistoryList(form);
        int totalCountFiltered = coinHistoryService.getCoinHistoryListTotalCountFiltered(form);
        int totalCount = coinHistoryService.getCoinHistoryListCount();
        logger.debug("----4----data count=" + dataList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        logger.debug("----8----");
        return jsonparse;
    }


    // Customer admin list
    @RequestMapping(value = "/admin/coin/{id}/", method = RequestMethod.GET)
    public String coinHistoryList(Model model, @PathVariable Long id) {

        CoinDetailForm coinDetailForm = new CoinDetailForm();

        CoinHistory coinHistory = coinHistoryService.getCoinListDetail(id);
        CoinMaster coinMaster = coinMasterService.getCoinMasterList(coinHistory.getCoinId(), coinHistory.getCoinType());

        coinDetailForm.setCoinId(coinHistory.getCoinId());
        coinDetailForm.setAmount(coinHistory.getAmount());
        coinDetailForm.setCoinType(coinHistory.getCoinType());
        coinDetailForm.setCreateDatetime(DateUtil.dateToStringyyyy_MM_dd(coinHistory.getCreateDatetime()));
        coinDetailForm.setDesc(coinMaster.getDesc());
        coinDetailForm.setItemID(coinHistory.getItemId());
        coinDetailForm.setSerialNumber(coinHistory.getSerialNumber());
        coinDetailForm.setCoinChanged(coinHistory.getCoinChanged());
        coinDetailForm.setUserId(coinHistory.getUserId());

        model.addAttribute("coin_detail", coinDetailForm);

        return "admin/coinEdit";
    }
}
