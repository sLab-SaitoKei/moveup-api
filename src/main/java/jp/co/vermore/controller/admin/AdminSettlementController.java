package jp.co.vermore.controller.admin;

import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.ShopForList;
import jp.co.vermore.form.admin.SettlementEditForm;
import jp.co.vermore.form.admin.SettlementListForm;
import jp.co.vermore.form.admin.ShopListForm;
import jp.co.vermore.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AdminSettlementController extends BaseController {

    @Autowired
    private SettlementService settlementService;

    public void setSettlementService(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @RequestMapping(value = "/admin/settlement/list/",method = RequestMethod.GET)
    public String settlement(){
        return "admin/settlement";
    }


    @RequestMapping(value = "/admin/settlement/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject settlementList(@RequestBody SettlementListForm form) {
        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");

        boolean b=false;

        if(form.getMon() == null){
            form.setMonth(null);
        }else {
            String reg="\\d{4}\\-\\d{2}";
            Pattern pat=Pattern.compile(reg);
            Matcher matcher=pat.matcher(form.getMon());
            b=matcher.find();
        }

        if(b){
            form.setMonth(Integer.valueOf((form.getMon().replace("-",""))));
        }else {
            form.setMonth(null);
        }

        // query data
        List<SettlementListForm> dataList = settlementService.getSettlementAllByCondition(form);
        int totalCountFiltered= settlementService.getSettlementCountByCondition(form);
        int totalCount = settlementService.getSettlementCount();

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


    @RequestMapping(value = "/admin/settlement/edit/{uuid}/{month}/",method = RequestMethod.GET)
    public String settlementEdit(Model model,@PathVariable String uuid,@PathVariable Integer month){
        StringBuilder sb=new StringBuilder();
        sb.append(month.toString()).insert(4,"-");
        model.addAttribute("shopUUID",uuid.trim());
        model.addAttribute("mon",sb.toString());
        return "admin/settlementEdit";
    }


    @RequestMapping(value = "/admin/settlement/edit/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject settlementListEdit(@RequestBody SettlementEditForm form) {
        logger.debug("----1----");
        // query data
        List<SettlementEditForm> dataList = settlementService.getSettlementEditByMonth(form);
        int totalCountFiltered= 0;
        int totalCount = 0;
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
}
