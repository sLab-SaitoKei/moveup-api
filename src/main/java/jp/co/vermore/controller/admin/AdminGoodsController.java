//package jp.co.vermore.controller.admin;
//
//import jp.co.vermore.common.Constant;
//import jp.co.vermore.common.DatatablesJsonObject;
//import jp.co.vermore.common.JsonStatus;
//import jp.co.vermore.common.mvc.APIException;
//import jp.co.vermore.common.mvc.BaseController;
//import jp.co.vermore.common.util.DateUtil;
//import jp.co.vermore.entity.*;
//import jp.co.vermore.form.*;
//import jp.co.vermore.form.admin.GoodsListForm;
//import jp.co.vermore.form.admin.GoodsPurchaseHistoryListForm;
//import jp.co.vermore.form.admin.GoodsPurchaseHistotyDetailForm;
//import jp.co.vermore.form.admin.GoodsPurchaseListForm;
//import jp.co.vermore.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * AdminGoodsController
// * Created by Wangyan.
// * <p>
// * DateTime: 2018/04/09 15:40
// * Copyright: sLab, Corp
// */
//@Controller
//public class AdminGoodsController extends BaseController {
//
//    @Autowired
//    private GoodsService goodsService;
//
//    @Autowired
//    private AWSService awsService;
//
//    @Autowired
//    PlatformTransactionManager txManager;
//
//    @Autowired
//    private PersonService personService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private AuthService authService;
//
//
//    // Goods admin list
//    @RequestMapping(value = "/admin/goods/list/", method = RequestMethod.GET)
//    public String goodsList(Model model,HttpServletRequest request) {
//        int errorCode = 0;
//        if(!request.getSession().isNew()){
//            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
//                errorCode = (int)request.getSession().getAttribute("error");
//                request.getSession().setAttribute("error",0);
//            }
//        }
//        model.addAttribute("errorCode", errorCode);
//        return "admin/goodsList";
//    }
//
//    @RequestMapping(value = "/admin/goods/list/", method = RequestMethod.POST)
//    @ResponseBody
//    public DatatablesJsonObject goodsList(@RequestBody GoodsListForm form){
//        logger.debug("----1----");
//        // set order statement
//        if(form.getOrder().size() > 0
//                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
//                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0){
//            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
//            logger.debug("----2----order statement="+form.getOrderStatement());
//        }else{
//            form.setOrderStatement("id");
//            logger.debug("----2----order statement="+form.getOrderStatement());
//        }
//        logger.debug("----3----");
//
//        // query data
//        List<GoodsAmount> dataList = goodsService.getGoodsAllByCondition(form);
//
//        int totalCountFiltered = goodsService.getGoodsCountByCondition(form);
//        int totalCount = goodsService.getGoodsCount();
//        logger.debug("----4----data count="+dataList.size());
//        logger.debug("----5----total filtered="+totalCountFiltered);
//        logger.debug("----6----total count="+totalCount);
//        logger.debug("----7----page="+form.getDraw());
//
//        // return json data
//        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
//        jsonparse.setDraw(form.getDraw());
//        jsonparse.setRecordsFiltered(totalCountFiltered);
//        jsonparse.setRecordsTotal(totalCount);
//        jsonparse.setData(dataList);
//        logger.debug("----8----");
//        return jsonparse;
//    }
//
//
//
//
//    //Goods Regist
//    @RequestMapping(value = "/admin/goods/regist/", method = RequestMethod.GET)
//    public String goodsInsert(Model model) {
//        GoodsRegistForm goodsRegistForm = new GoodsRegistForm();
//        model.addAttribute("goodsRegistForm", goodsRegistForm);
//        return "admin/goodsRegist";
//    }
//
//    //Goods Regist
//    @RequestMapping(value = "/admin/goods/regist/", method = RequestMethod.POST)
//    public String goodsRegist(@ModelAttribute GoodsRegistForm goodsRegistForm,HttpServletRequest request) throws APIException{
//        HttpSession session = request.getSession();
//        // upload files
//        try {
//            if (!goodsRegistForm.getPicFile1().isEmpty()) {
//                goodsRegistForm.setPicUrl1(awsService.postFile(goodsRegistForm.getPicFile1()));
//            }
//            if (!goodsRegistForm.getPicFile2().isEmpty()) {
//                goodsRegistForm.setPicUrl2(awsService.postFile(goodsRegistForm.getPicFile2()));
//            }
//            if (!goodsRegistForm.getPicFile3().isEmpty()) {
//                goodsRegistForm.setPicUrl3(awsService.postFile(goodsRegistForm.getPicFile3()));
//            }
//            if (!goodsRegistForm.getPicFile4().isEmpty()) {
//                goodsRegistForm.setPicUrl4(awsService.postFile(goodsRegistForm.getPicFile4()));
//            }
//            if (!goodsRegistForm.getPicFile5().isEmpty()) {
//                goodsRegistForm.setPicUrl5(awsService.postFile(goodsRegistForm.getPicFile5()));
//            }
//            if (!goodsRegistForm.getPicFile6().isEmpty()) {
//                goodsRegistForm.setPicUrl6(awsService.postFile(goodsRegistForm.getPicFile6()));
//            }
//            if (!goodsRegistForm.getPicFile7().isEmpty()) {
//                goodsRegistForm.setPicUrl7(awsService.postFile(goodsRegistForm.getPicFile7()));
//            }
//            if (!goodsRegistForm.getPicFile8().isEmpty()) {
//                goodsRegistForm.setPicUrl8(awsService.postFile(goodsRegistForm.getPicFile8()));
//            }
//            if (!goodsRegistForm.getPicFile9().isEmpty()) {
//                goodsRegistForm.setPicUrl9(awsService.postFile(goodsRegistForm.getPicFile9()));
//            }
//            if (!goodsRegistForm.getPicFile10().isEmpty()) {
//                goodsRegistForm.setPicUrl10(awsService.postFile(goodsRegistForm.getPicFile10()));
//            }
//            session.setAttribute("error",0);
//        }catch (IOException e) {
//            session.setAttribute("error",1);
//            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
//        }
//
//        //goods insert
//        Long goodsId = goodsService.insertGoods(goodsRegistForm);
//        goodsService.insertGoodsDetail(goodsRegistForm,goodsId);
//
//        //stock insert
//        List<StockRegistForm> stockList= goodsRegistForm.getStock();
//        for(StockRegistForm srf : stockList){
//
//            //size xs insert
//            if(srf.getXs() > 0){
//                Stock xs = new Stock();
//                xs.setGoodsId(goodsId);
//                xs.setColors(srf.getColor());
//                xs.setStock(srf.getXs());
//                xs.setAmount(srf.getXs());
//                xs.setSize(Constant.GOODS_SIZE.XS);
//                xs.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(xs);
//            }
//
//            //size s insert
//            if(srf.getS() > 0){
//                Stock s = new Stock();
//                s.setGoodsId(goodsId);
//                s.setColors(srf.getColor());
//                s.setStock(srf.getS());
//                s.setAmount(srf.getS());
//                s.setSize(Constant.GOODS_SIZE.S);
//                s.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(s);
//            }
//
//            //size m insert
//            if(srf.getM() > 0){
//                Stock m = new Stock();
//                m.setGoodsId(goodsId);
//                m.setColors(srf.getColor());
//                m.setStock(srf.getM());
//                m.setAmount(srf.getM());
//                m.setSize(Constant.GOODS_SIZE.M);
//                m.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(m);
//            }
//
//            //size l insert
//            if(srf.getL() > 0){
//                Stock l = new Stock();
//                l.setGoodsId(goodsId);
//                l.setColors(srf.getColor());
//                l.setStock(srf.getL());
//                l.setAmount(srf.getL());
//                l.setSize(Constant.GOODS_SIZE.L);
//                l.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(l);
//            }
//
//            //size xl insert
//            if(srf.getXl() > 0){
//                Stock xl = new Stock();
//                xl.setGoodsId(goodsId);
//                xl.setColors(srf.getColor());
//                xl.setStock(srf.getXl());
//                xl.setAmount(srf.getXl());
//                xl.setSize(Constant.GOODS_SIZE.XL);
//                xl.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(xl);
//            }
//
//            //size FREESIZE insert
//            if(srf.getFreesize() > 0){
//                Stock FREESIZE = new Stock();
//                FREESIZE.setGoodsId(goodsId);
//                FREESIZE.setColors(srf.getColor());
//                FREESIZE.setStock(srf.getFreesize());
//                FREESIZE.setAmount(srf.getFreesize());
//                FREESIZE.setSize(Constant.GOODS_SIZE.FREESIZE);
//                FREESIZE.setStockType(Constant.STOCK_TYPE.INCREASE);
//                goodsService.insertStock(FREESIZE);
//            }
//        }
//        return "redirect:/admin/goods/list/";
//    }
//
//    //Goods Edit
//    @RequestMapping(value = "/admin/goods/edit/{id}/", method = RequestMethod.GET)
//    public String goodsUpdate(Model model,@PathVariable Long id) {
//        GoodsEditForm goodsEditForm = new GoodsEditForm();
//
//        Goods goods = goodsService.getGoodsById(id);
//        goodsEditForm.setSortScore(goods.getSortScore());
//        goodsEditForm.setPublishStart(DateUtil.dateToStringyyyy_MM_dd(goods.getPublishStart()));
//        goodsEditForm.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd(goods.getPublishEnd()));
//        if(goods.getGoodsType().equals("1")){
//            goodsEditForm.setGoodsType("Tシャツ");
//        }else if(goods.getGoodsType().equals("2")){
//            goodsEditForm.setGoodsType("フリーペーパー");
//        }else{
//            goodsEditForm.setGoodsType("TEST");
//        }
////        goodsEditForm.setGoodsType(goods.getGoodsType());
//        GoodsDetail goodsDetail = goodsService.getGoodsDetailByGoodsListId(id);
//        goodsEditForm.setId(Long.valueOf(id));
//        goodsEditForm.setTitle(goodsDetail.getTitle());
//        goodsEditForm.setBrand(goodsDetail.getBrand());
//        goodsEditForm.setPrice(goodsDetail.getPrice());
//        goodsEditForm.setDesc1(goodsDetail.getDesc1());
//        goodsEditForm.setDesc2(goodsDetail.getDesc2());
//        goodsEditForm.setDesc3(goodsDetail.getDesc3());
//        goodsEditForm.setPicUrl1(goodsDetail.getPicUrl1());
//        goodsEditForm.setPicUrl2(goodsDetail.getPicUrl2());
//        goodsEditForm.setPicUrl3(goodsDetail.getPicUrl3());
//        goodsEditForm.setPicUrl4(goodsDetail.getPicUrl4());
//        goodsEditForm.setPicUrl5(goodsDetail.getPicUrl5());
//        goodsEditForm.setPicUrl6(goodsDetail.getPicUrl6());
//        goodsEditForm.setPicUrl7(goodsDetail.getPicUrl7());
//        goodsEditForm.setPicUrl8(goodsDetail.getPicUrl8());
//        goodsEditForm.setPicUrl9(goodsDetail.getPicUrl9());
//        goodsEditForm.setPicUrl10(goodsDetail.getPicUrl10());
//        //stock
//        List<StockRegistForm> stocksFormList = new ArrayList<StockRegistForm>();
//        List<Stock> colorsList = goodsService.getColors(id);
//        List<Stock> sizeList = goodsService.getSize(id);
//        goodsEditForm.setNewAmountId(colorsList.size());
//        int amountId = 0;
//        for (Stock csd : colorsList) {
//            Byte colors = csd.getColors();
//            StockRegistForm srf = new StockRegistForm();
//            srf.setAmountId(amountId);
//            srf.setColor(colors);
//            if(colors == Constant.GOODS_COLOR.COLORIZED){
//                srf.setColorName("色なし");
//            }
////            if(colors == Constant.GOODS_COLOR.RED){
////                srf.setColorName("赤い");
////            }if(colors == Constant.GOODS_COLOR.ORANGE){
////                srf.setColorName("オレンジ");
////            }if(colors == Constant.GOODS_COLOR.YELLOW){
////                srf.setColorName("イエロー");
////            }if(colors == Constant.GOODS_COLOR.GREEN){
////                srf.setColorName("緑");
////            }if(colors == Constant.GOODS_COLOR.BLUE){
////                srf.setColorName("靑い");
////            }if(colors == Constant.GOODS_COLOR.PURPLE){
////                srf.setColorName("パープル");
////            }
//            if(colors == Constant.GOODS_COLOR.BLACK){
//                srf.setColorName("黒い");
//            }if(colors == Constant.GOODS_COLOR.WHITE){
//                srf.setColorName("白い");
//            }
////            if(colors == Constant.GOODS_COLOR.COFFEE){
////                srf.setColorName("コーヒー色");
////            }if(colors == Constant.GOODS_COLOR.PINK){
////                srf.setColorName("ピンク");
////            }
//
//            amountId++;
//            for (Stock ssd : sizeList) {
//                Stock stock = new Stock();
//                stock.setGoodsId(Long.valueOf(id));
//                stock.setColors(colors);
//                stock.setSize(ssd.getSize());
//                Stock resultStock = new Stock();
//                List<Stock> stockList = goodsService.getAmount(stock);
//                if(stockList.size()>0){
//                    resultStock = stockList.get(0);
//                }else{
//                    continue;
//                }
//                if(resultStock != null){
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.XS){
//                        srf.setXs(resultStock.getAmount());
//                    }
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.S){
//                        srf.setS(resultStock.getAmount());
//                    }
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.M){
//                        srf.setM(resultStock.getAmount());
//                    }
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.L){
//                        srf.setL(resultStock.getAmount());
//                    }
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.XL){
//                        srf.setXl(resultStock.getAmount());
//                    }
//                    if(resultStock.getSize() == Constant.GOODS_SIZE.FREESIZE){
//                        srf.setFreesize(resultStock.getAmount());
//                    }
//                }else{
//                    continue;
//                }
//
//            }
//            stocksFormList.add(srf);
//        }
//        goodsEditForm.setStock(stocksFormList);
//        goodsEditForm.setPastStock(stocksFormList);
//        model.addAttribute("goodsEditForm", goodsEditForm);
//        return "admin/goodsEdit";
//    }
//
//    //Goods Edit
//    @RequestMapping(value = "/admin/goods/edit/", method = RequestMethod.POST)
//    public String goodsEdit(@ModelAttribute GoodsEditForm goodsEditForm,HttpServletRequest request) throws APIException{
//        HttpSession session = request.getSession();
//        // upload files
//        try {
//            if (!goodsEditForm.getPicFile1().isEmpty()) {
//                goodsEditForm.setPicUrl1(awsService.postFile(goodsEditForm.getPicFile1()));
//            }
//            if (!goodsEditForm.getPicFile2().isEmpty()) {
//                goodsEditForm.setPicUrl2(awsService.postFile(goodsEditForm.getPicFile2()));
//            }
//            if (!goodsEditForm.getPicFile3().isEmpty()) {
//                goodsEditForm.setPicUrl3(awsService.postFile(goodsEditForm.getPicFile3()));
//            }
//            if (!goodsEditForm.getPicFile4().isEmpty()) {
//                goodsEditForm.setPicUrl4(awsService.postFile(goodsEditForm.getPicFile4()));
//            }
//            if (!goodsEditForm.getPicFile5().isEmpty()) {
//                goodsEditForm.setPicUrl5(awsService.postFile(goodsEditForm.getPicFile5()));
//            }
//            if (!goodsEditForm.getPicFile6().isEmpty()) {
//                goodsEditForm.setPicUrl6(awsService.postFile(goodsEditForm.getPicFile6()));
//            }
//            if (!goodsEditForm.getPicFile7().isEmpty()) {
//                goodsEditForm.setPicUrl7(awsService.postFile(goodsEditForm.getPicFile7()));
//            }
//            if (!goodsEditForm.getPicFile8().isEmpty()) {
//                goodsEditForm.setPicUrl8(awsService.postFile(goodsEditForm.getPicFile8()));
//            }
//            if (!goodsEditForm.getPicFile9().isEmpty()) {
//                goodsEditForm.setPicUrl9(awsService.postFile(goodsEditForm.getPicFile9()));
//            }
//            if (!goodsEditForm.getPicFile10().isEmpty()) {
//                goodsEditForm.setPicUrl10(awsService.postFile(goodsEditForm.getPicFile10()));
//            }
//            session.setAttribute("error",0);
//        }catch (IOException e) {
//
//            session.setAttribute("error",1);
//            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
//        }
//
//        //goods update
//        goodsService.updateGoods(goodsEditForm);
//        goodsService.updateGoodsDetail(goodsEditForm);
//
//        //stock update
//        List<StockRegistForm> stockList= goodsEditForm.getStock();
//        List<StockRegistForm> pastStockList = goodsEditForm.getPastStock();
//        for(StockRegistForm psrf : pastStockList){
//            for(StockRegistForm srf : stockList){
//                if(srf.getColor() == psrf.getColor()){
//                    //size xs update
//                    if(srf.getXs() != psrf.getXs()){
//                        Stock xs = new Stock();
//                        xs.setGoodsId(goodsEditForm.getId());
//                        xs.setColors(srf.getColor());
//                        xs.setSize(Constant.GOODS_SIZE.XS);
//                        if(srf.getXs()>psrf.getXs()){
//                            xs.setStock(srf.getXs()-psrf.getXs());
//                            xs.setAmount(srf.getXs());
//                            xs.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getXs()<psrf.getXs() || psrf.getXs()-srf.getXs()>0){
//                            xs.setStock(psrf.getXs()-srf.getXs());
//                            xs.setAmount(srf.getXs());
//                            xs.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(xs);
//                    }
//
//                    //size s update
//                    if(srf.getS() != psrf.getS()){
//                        Stock s = new Stock();
//                        s.setGoodsId(goodsEditForm.getId());
//                        s.setColors(srf.getColor());
//                        s.setSize(Constant.GOODS_SIZE.S);
//                        if(srf.getS()>psrf.getS()){
//                            s.setStock(srf.getS()-psrf.getS());
//                            s.setAmount(srf.getS());
//                            s.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getS()<psrf.getS() || psrf.getS()-srf.getS()>0){
//                            s.setStock(psrf.getS()-srf.getS());
//                            s.setAmount(srf.getS());
//                            s.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(s);
//                    }
//
//                    //size m update
//                    if(srf.getM() != psrf.getM()){
//                        Stock m = new Stock();
//                        m.setGoodsId(goodsEditForm.getId());
//                        m.setColors(srf.getColor());
//                        m.setSize(Constant.GOODS_SIZE.M);
//                        if(srf.getM()>psrf.getM()){
//                            m.setStock(srf.getM()-psrf.getM());
//                            m.setAmount(srf.getM());
//                            m.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getM()<srf.getM() || psrf.getM()-srf.getM()>0){
//                            m.setStock(psrf.getM()-srf.getM());
//                            m.setAmount(srf.getM());
//                            m.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(m);
//                    }
//
//                    //size l update
//                    if(srf.getL() != psrf.getL()){
//                        Stock l = new Stock();
//                        l.setGoodsId(goodsEditForm.getId());
//                        l.setColors(srf.getColor());
//                        l.setSize(Constant.GOODS_SIZE.L);
//                        if(srf.getL()>psrf.getL()){
//                            l.setStock(srf.getL()-psrf.getL());
//                            l.setAmount(srf.getL());
//                            l.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getL()<psrf.getL() || psrf.getL()-srf.getL()>0){
//                            l.setStock(psrf.getL()-srf.getL());
//                            l.setAmount(srf.getL());
//                            l.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(l);
//                    }
//
//                    //size xl update
//                    if(srf.getXl() != psrf.getXl()){
//                        Stock xl = new Stock();
//                        xl.setGoodsId(goodsEditForm.getId());
//                        xl.setColors(srf.getColor());
//                        xl.setSize(Constant.GOODS_SIZE.XL);
//                        if(srf.getXl()>psrf.getXl()){
//                            xl.setStock(srf.getXl()-psrf.getXl());
//                            xl.setAmount(srf.getXl());
//                            xl.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getXl()<psrf.getXl() || psrf.getXl()-srf.getXl()>0){
//                            xl.setStock(psrf.getXl()-srf.getXl());
//                            xl.setAmount(srf.getXl());
//                            xl.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(xl);
//                    }
//
//                    //size FREESIZE update
//                    if(srf.getFreesize() != psrf.getFreesize()){
//                        Stock FREESIZE = new Stock();
//                        FREESIZE.setGoodsId(goodsEditForm.getId());
//                        FREESIZE.setColors(srf.getColor());
//                        FREESIZE.setSize(Constant.GOODS_SIZE.FREESIZE);
//                        if(srf.getFreesize()>psrf.getFreesize()){
//                            FREESIZE.setStock(srf.getFreesize()-psrf.getFreesize());
//                            FREESIZE.setAmount(srf.getFreesize());
//                            FREESIZE.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        }else if(srf.getFreesize()<psrf.getFreesize() || psrf.getFreesize()-srf.getFreesize()>0){
//                            FREESIZE.setStock(psrf.getFreesize()-srf.getFreesize());
//                            FREESIZE.setAmount(srf.getFreesize());
//                            FREESIZE.setStockType(Constant.STOCK_TYPE.REDUCE);
//                        }else{
//                            throw new APIException(JsonStatus.STOCK_OPERATION_FAILED);
//                        }
//                        goodsService.insertStock(FREESIZE);
//                    }
//                    continue;
//                }else{
//
//                    //size xs insert
//                    if(srf.getXs() > 0){
//                        Stock xs = new Stock();
//                        xs.setGoodsId(goodsEditForm.getId());
//                        xs.setColors(srf.getColor());
//                        xs.setStock(srf.getXs());
//                        xs.setAmount(srf.getXs());
//                        xs.setSize(Constant.GOODS_SIZE.XS);
//                        xs.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(xs);
//                    }
//
//                    //size s insert
//                    if(srf.getS() > 0){
//                        Stock s = new Stock();
//                        s.setGoodsId(goodsEditForm.getId());
//                        s.setColors(srf.getColor());
//                        s.setStock(srf.getS());
//                        s.setAmount(srf.getS());
//                        s.setSize(Constant.GOODS_SIZE.S);
//                        s.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(s);
//                    }
//
//                    //size m insert
//                    if(srf.getM() > 0){
//                        Stock m = new Stock();
//                        m.setGoodsId(goodsEditForm.getId());
//                        m.setColors(srf.getColor());
//                        m.setStock(srf.getM());
//                        m.setAmount(srf.getM());
//                        m.setSize(Constant.GOODS_SIZE.M);
//                        m.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(m);
//                    }
//
//                    //size l insert
//                    if(srf.getL() > 0){
//                        Stock l = new Stock();
//                        l.setGoodsId(goodsEditForm.getId());
//                        l.setColors(srf.getColor());
//                        l.setStock(srf.getL());
//                        l.setAmount(srf.getL());
//                        l.setSize(Constant.GOODS_SIZE.L);
//                        l.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(l);
//                    }
//
//                    //size xl insert
//                    if(srf.getXl() > 0){
//                        Stock xl = new Stock();
//                        xl.setGoodsId(goodsEditForm.getId());
//                        xl.setColors(srf.getColor());
//                        xl.setStock(srf.getXl());
//                        xl.setAmount(srf.getXl());
//                        xl.setSize(Constant.GOODS_SIZE.XL);
//                        xl.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(xl);
//                    }
//
//                    //size FREESIZE insert
//                    if(srf.getFreesize() > 0){
//                        Stock FREESIZE = new Stock();
//                        FREESIZE.setGoodsId(goodsEditForm.getId());
//                        FREESIZE.setColors(srf.getColor());
//                        FREESIZE.setStock(srf.getFreesize());
//                        FREESIZE.setAmount(srf.getFreesize());
//                        FREESIZE.setSize(Constant.GOODS_SIZE.FREESIZE);
//                        FREESIZE.setStockType(Constant.STOCK_TYPE.INCREASE);
//                        goodsService.insertStock(FREESIZE);
//                    }
//                    continue;
//                }
//            }
//        }
//        return "redirect:/admin/goods/list/";
//    }
//
//    //Goods Delete
//    @RequestMapping(value = "/admin/goods/delete/{id}/", method = RequestMethod.GET)
//    public String goodsDelete(@PathVariable Long id) {
//        goodsService.deleteGoods(id);
//        goodsService.deleteGoodsDetail(id);
//        return "redirect:/admin/goods/list/";
//    }
//
//
//    // Goods admin delete
//    @RequestMapping(value = "/admin/goods/delete/", method = RequestMethod.POST)
//    public String goodsDetailDelete(HttpServletRequest hsr
//            ,HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Long goodsId = Long.valueOf(hsr.getParameterValues("goods_id")[0]);
//
//        // トランザクション管理の開始
//        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//        try {
//            goodsService.deleteGoods(goodsId);
//            goodsService.deleteGoodsDetail(goodsId);
//            txManager.commit(txStatus);
//            session.setAttribute("error",0);
//        } catch (Exception e) {
//            session.setAttribute("error",1);
//            txManager.rollback(txStatus);
//        }
//        return "redirect:/admin/goods/list/";
//    }
//
//    // Goods purchase admin list
//    @RequestMapping(value = "/admin/goods/purchase/list/", method = RequestMethod.GET)
//    public String goodsPurchaseList(Model model) {
// /*       List<GoodsPurchase> list = goodsService.getGoodsPurchaseHistoryAll();
//        List<GoodsPurchaseHistotyForm> gpjpList = new ArrayList<>();
//        for (GoodsPurchase gpd : list) {
//            GoodsDetail goodsDetail = goodsService.getGoodsDetailByGoodsListId(gpd.getGoodsId());
//            GoodsPurchaseHistotyForm gpjp = new GoodsPurchaseHistotyForm();
//            gpjp.setId(gpd.getId());
//            gpjp.setUuid("ao8N2A8Qdw");
//            gpjp.setGoodsName(goodsDetail.getName());
//            Byte colors =gpd.getColors();
//            if(colors == Constant.GOODS_COLOR.COLORIZED){
//                gpjp.setColor("カラー");
//            }if(colors == Constant.GOODS_COLOR.RED){
//                gpjp.setColor("赤い");
//            }if(colors == Constant.GOODS_COLOR.ORANGE){
//                gpjp.setColor("オレンジ");
//            }if(colors == Constant.GOODS_COLOR.YELLOW){
//                gpjp.setColor("イエロー");
//            }if(colors == Constant.GOODS_COLOR.GREEN){
//                gpjp.setColor("緑");
//            }if(colors == Constant.GOODS_COLOR.BLUE){
//                gpjp.setColor("靑い");
//            }if(colors == Constant.GOODS_COLOR.PURPLE){
//                gpjp.setColor("パープル");
//            }if(colors == Constant.GOODS_COLOR.BLACK){
//                gpjp.setColor("黒い");
//            }if(colors == Constant.GOODS_COLOR.WHITE){
//                gpjp.setColor("白い");
//            }if(colors == Constant.GOODS_COLOR.COFFEE){
//                gpjp.setColor("コーヒー色");
//            }if(colors == Constant.GOODS_COLOR.PINK){
//                gpjp.setColor("ピンク");
//            }
//            Byte size = gpd.getSize();
//            if(size == Constant.GOODS_SIZE.FREESIZE){
//                gpjp.setSize("不明");
//            }if(size == Constant.GOODS_SIZE.XS){
//                gpjp.setSize("XS");
//            }if(size == Constant.GOODS_SIZE.S){
//                gpjp.setSize("S");
//            }if(size == Constant.GOODS_SIZE.M){
//                gpjp.setSize("M");
//            }if(size == Constant.GOODS_SIZE.L){
//                gpjp.setSize("L");
//            }if(size == Constant.GOODS_SIZE.XL){
//                gpjp.setSize("XL");
//            }
//            gpjp.setQuantity(gpd.getQuantity());
//            gpjp.setPrice(gpd.getPrice());
//            gpjp.setCoin(gpd.getCoin());
//            if(gpd.getStatus() == Constant.PURCHASE_STATUS.PURCHASE_SUCCESS){
//                gpjp.setStatusContent("支払いが成功する");
//            }else if(gpd.getStatus() == Constant.PURCHASE_STATUS.PURCHASE_FAILURE){
//                gpjp.setStatusContent("支払いに失敗する");
//            }else if(gpd.getStatus() == Constant.PURCHASE_STATUS.RETURN_GOODS){
//                gpjp.setStatusContent("返品を申請する");
//            }else {
//                gpjp.setStatusContent("取引が完了する");
//            }
//            gpjp.setSerialNumber(gpd.getSerialNumber());
//            gpjpList.add(gpjp);
//        }
//        model.addAttribute("purchaseHistotyList", gpjpList);
//        */
//        return "admin/goodsPurchaseList";
//    }
//
//    @RequestMapping(value = "/admin/goods/purchase/list/", method = RequestMethod.POST)
//    @ResponseBody
//    public DatatablesJsonObject goodsPurchaseList(@RequestBody GoodsPurchaseHistoryListForm form){
////        logger.debug("----1----");
////        // set order statement
////        if(form.getOrder().size() > 0
////                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
////                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0){
////            form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
////            logger.debug("----2----order statement="+form.getOrderStatement());
////        }else{
////            form.setOrderStatement("id");
////            logger.debug("----2----order statement="+form.getOrderStatement());
////        }
////        logger.debug("----3----");
//
////        // query data
////        List<GoodsPurchase> resultList = goodsService.getGoodsPurchaseAllByCondition(form);
////        List<GoodsPurchaseListForm> dataList = new ArrayList<>();
////        for (GoodsPurchase result : resultList) {
////            GoodsDetail goodsDetail = goodsService.getGoodsDetailByGoodsListId(result.getGoodsId());
////            GoodsPurchaseListForm data = new GoodsPurchaseListForm();
////            data.setId(result.getId());
////            data.setUserId(result.getUserId());
////            data.setGoodsName(goodsDetail.getName());
////            data.setAmount(result.getPrice() * result.getQuantity());
////            data.setCoin(result.getCoin());
////            data.setQuantity(result.getQuantity());
////            data.setUpdateDatetime(result.getUpdateDatetime());
////            Byte colors =result.getColors();
////            if(colors == Constant.GOODS_COLOR.COLORIZED){
////                data.setColorsContent("色なし");
////            }
//////            if(colors == Constant.GOODS_COLOR.RED){
//////                data.setColorsContent("赤い");
//////            }if(colors == Constant.GOODS_COLOR.ORANGE){
//////                data.setColorsContent("オレンジ");
//////            }if(colors == Constant.GOODS_COLOR.YELLOW){
//////                data.setColorsContent("イエロー");
//////            }if(colors == Constant.GOODS_COLOR.GREEN){
//////                data.setColorsContent("緑");
//////            }if(colors == Constant.GOODS_COLOR.BLUE){
//////                data.setColorsContent("靑い");
//////            }if(colors == Constant.GOODS_COLOR.PURPLE){
//////                data.setColorsContent("パープル");
//////            }
////            if(colors == Constant.GOODS_COLOR.BLACK){
////                data.setColorsContent("黒い");
////            }if(colors == Constant.GOODS_COLOR.WHITE){
////                data.setColorsContent("白い");
////            }
//////            if(colors == Constant.GOODS_COLOR.COFFEE){
//////                data.setColorsContent("コーヒー色");
//////            }if(colors == Constant.GOODS_COLOR.PINK){
//////                data.setColorsContent("ピンク");
//////            }
////            Byte size = result.getSize();
////            if(size == Constant.GOODS_SIZE.FREESIZE){
////                data.setSizeContent("不明");
////            }if(size == Constant.GOODS_SIZE.XS){
////                data.setSizeContent("XS");
////            }if(size == Constant.GOODS_SIZE.S){
////                data.setSizeContent("S");
////            }if(size == Constant.GOODS_SIZE.M){
////                data.setSizeContent("M");
////            }if(size == Constant.GOODS_SIZE.L){
////                data.setSizeContent("L");
////            }if(size == Constant.GOODS_SIZE.XL){
////                data.setSizeContent("XL");
////            }
////            if(result.getStatus() == Constant.PURCHASE_STATUS.PURCHASE_SUCCESS){
////                data.setStatusContent("支払完了");
////            }else if(result.getStatus() == Constant.PURCHASE_STATUS.PURCHASE_FAILURE){
////                data.setStatusContent("未支払い");
////            }else if(result.getStatus() == Constant.PURCHASE_STATUS.RETURN_GOODS){
////                data.setStatusContent("返品申請");
////            }else if(result.getStatus() == Constant.PURCHASE_STATUS.DELIVER_GOODS){
////                data.setStatusContent("出荷が完了");
////            }else {
////                data.setStatusContent("取引完了");
////            }
////            data.setSerialNumber(result.getSerialNumber());
////            dataList.add(data);
////        }
////
////        int totalCountFiltered = goodsService.getGoodsPurchaseCountByCondition(form);
////        int totalCount = goodsService.getGoodsPurchaseCount();
//
//        if(form.getPaymentTypeStr() =="支払完了"){
//            form.setPaymentType(1);
//        }else if(form.getPaymentTypeStr() =="未支払い"){
//            form.setPaymentType(2);
//        }else if(form.getPaymentTypeStr() =="出荷が完了"){
//            form.setPaymentType(3);
//        }else if(form.getPaymentTypeStr() =="返品申請"){
//            form.setPaymentType(4);
//        }else if(form.getPaymentTypeStr() =="取引完了"){
//            form.setPaymentType(5);
//        }else if(form.getPaymentTypeStr() =="ALL"){
//            form.setPaymentType(99);
//        }
//        logger.debug("----4----PaymentType="+form.getPaymentType());
//
//
//        List<PaymentHistory> resultList = paymentService.getPaymentHistoryByCondition(form);
//        logger.debug("----5----resultList="+resultList.size());
//
//        List<GoodsPurchaseHistoryListForm> dataList = new ArrayList<>();
//        for (PaymentHistory ph : resultList){
//            GoodsPurchaseHistoryListForm gphlf = new GoodsPurchaseHistoryListForm();
//            gphlf.setId(ph.getId());
//            gphlf.setOrderId(ph.getOrderId());
//            gphlf.setAmount(ph.getAmount());
//            gphlf.setUpdateDatetime(ph.getUpdateDatetime());
//            if(ph.getPaymentType()==Constant.PAYMENT_TYPE.PURCHASE_SUCCESS){
//                gphlf.setPaymentTypeStr("支払完了");
//            }else if(ph.getPaymentType()==Constant.PAYMENT_TYPE.PURCHASE_FAILURE){
//                gphlf.setPaymentTypeStr("未支払い");
//            }else if(ph.getPaymentType()==Constant.PAYMENT_TYPE.DELIVER_GOODS){
//                gphlf.setPaymentTypeStr("出荷が完了");
//            }else if(ph.getPaymentType()==Constant.PAYMENT_TYPE.RETURN_GOODS){
//                gphlf.setPaymentTypeStr("返品申請");
//            }else{
//                gphlf.setPaymentTypeStr("取引完了");
//            }
//            dataList.add(gphlf);
//        }
//        logger.debug("----4----data count="+dataList.size());
//        int totalCountFiltered = paymentService.getPaymentHistoryCountByCondition(form);
//        logger.debug("----5----total filtered="+totalCountFiltered);
//        int totalCount = paymentService.getPaymentHistoryCount();
//
//        logger.debug("----6----total count="+totalCount);
//        logger.debug("----7----page="+form.getDraw());
//
//        // return json data
//        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
//        jsonparse.setDraw(form.getDraw());
//        jsonparse.setRecordsFiltered(totalCountFiltered);
//        jsonparse.setRecordsTotal(totalCount);
//        jsonparse.setData(dataList);
//        logger.debug("----8----");
//        return jsonparse;
//    }
//
//
//    //Goods Purchase Edit
//    @RequestMapping(value = "/admin/goods/purchase/edit/{id}/", method = RequestMethod.GET)
//    public String goodsPurchaseUpdate(Model model,@PathVariable Long id) {
//        PaymentHistory paymentHistory = paymentService.getPaymentHistoryByCondition(id);
//        GoodsPurchaseMailForm goodsPurchaseMailForm = new GoodsPurchaseMailForm();
//        goodsPurchaseMailForm.setSerialNumber(paymentHistory.getOrderId());
//        goodsPurchaseMailForm.setPurchaseType(Constant.PURCHASE_TYPE.CARD);
//
//        PurchaseInfo purchaseInfo = goodsService.selectBySerialNumber(paymentHistory.getOrderId());
//        goodsPurchaseMailForm.setName2(purchaseInfo.getFirstName1()+ " " + purchaseInfo.getLastName1());
//        goodsPurchaseMailForm.setZipcode2(purchaseInfo.getZipCode());
//        goodsPurchaseMailForm.setAddress2(purchaseInfo.getAddress1());
//        goodsPurchaseMailForm.setTel(purchaseInfo.getTel());
//        goodsPurchaseMailForm.setTel2(purchaseInfo.getTel());
//        goodsPurchaseMailForm.setDate(DateUtil.dateToStringyyyyMMddS(purchaseInfo.getCreateDatetime()));
//
//        Person person = personService.getPersonById(purchaseInfo.getUserId());
//        goodsPurchaseMailForm.setMail(person.getMail());
//        goodsPurchaseMailForm.setName(person.getFirstName()+ " " +person.getSecondName());
//        goodsPurchaseMailForm.setZipcode(person.getZipcode());
//        goodsPurchaseMailForm.setAddress(person.getAddress());
//
//        List<GoodsPurchase> purchaseList = goodsService.getGoodsPurchaseHistory(paymentHistory.getOrderId());
//        List<PurchaseGoodsInfoForm> goodsInfoForms = new ArrayList<>();
//        double totalPrice = 700;
//        for(GoodsPurchase gp: purchaseList){
//            PurchaseGoodsInfoForm pgif = new PurchaseGoodsInfoForm();
//            Goods goods = goodsService.getGoodsById(gp.getGoodsId());
//            if(goods != null){
//                String goodsName = goods.getTitle();
//                pgif.setGoodsName(goodsName);
//            }
//            String color;
//            if(gp.getColors() == Constant.GOODS_COLOR.BLACK){
//                color = "黒い";
//            }else if(gp.getColors() == Constant.GOODS_COLOR.WHITE){
//                color = "白い";
//            }else{
//                color = "色なし";
//            }
//            pgif.setColor(color);
//
//            String size;
//            if(gp.getSize() == Constant.GOODS_SIZE.XS){
//                size = "XS";
//            }else if(gp.getSize() == Constant.GOODS_SIZE.S){
//                size = "S";
//            }else if(gp.getSize() == Constant.GOODS_SIZE.M){
//                size = "M";
//            }else if(gp.getSize() == Constant.GOODS_SIZE.L){
//                size = "L";
//            }else if(gp.getSize() == Constant.GOODS_SIZE.XL){
//                size = "XL";
//            }else{
//                size = "フリーサイズ";
//            }
//            pgif.setSize(size);
//
//            String price = comdify(String.valueOf(gp.getPrice()));
//            pgif.setPrice(price);
//
//            String tax = comdify(String.valueOf((int)((gp.getPrice()/1.08)*0.08)));
//            pgif.setTax(tax);
//            pgif.setQuantity(gp.getQuantity());
//            goodsInfoForms.add(pgif);
//
//            totalPrice = totalPrice + gp.getPrice();
//        }
//
//        String totalPriceStr = comdify(String.valueOf((int)totalPrice));
//        goodsPurchaseMailForm.setTotalPrice(totalPriceStr);
//        goodsPurchaseMailForm.setDeliveryExpense(700);
//        goodsPurchaseMailForm.setGoodsInfoForms(goodsInfoForms);
//        goodsPurchaseMailForm.setStatus(paymentHistory.getPaymentType());
//        model.addAttribute("goodsPurchaseMailForm", goodsPurchaseMailForm);
//        return "admin/goodsPurchaseEdit";
//    }
//
//    //Goods Purchase Edit
//    @RequestMapping(value = "/admin/goods/purchase/edit/", method = RequestMethod.POST)
//    public String goodsPurchaseEdit(@ModelAttribute GoodsPurchaseMailForm gphf,HttpServletRequest request) throws APIException{
//        HttpSession session = request.getSession();
//        paymentService.updatePaymentHistory(gphf.getSerialNumber(),gphf.getStatus());
//        if(gphf.getStatus() == Constant.PAYMENT_TYPE.PURCHASE_SUCCESS){
//            goodsService.updateGoodsPurchase(gphf.getSerialNumber(),Constant.PURCHASE_STATUS.PURCHASE_SUCCESS);
//        }else if(gphf.getStatus() == Constant.PAYMENT_TYPE.PURCHASE_FAILURE){
//            goodsService.updateGoodsPurchase(gphf.getSerialNumber(),Constant.PURCHASE_STATUS.PURCHASE_FAILURE);
//        }else if(gphf.getStatus() == Constant.PAYMENT_TYPE.DELIVER_GOODS){
//            goodsService.updateGoodsPurchase(gphf.getSerialNumber(),Constant.PURCHASE_STATUS.DELIVER_GOODS);
//        }else if(gphf.getStatus() == Constant.PAYMENT_TYPE.RETURN_GOODS){
//            goodsService.updateGoodsPurchase(gphf.getSerialNumber(),Constant.PURCHASE_STATUS.RETURN_GOODS);
//        }else{
//            goodsService.updateGoodsPurchase(gphf.getSerialNumber(),Constant.PURCHASE_STATUS.TRANSACTION_COMPLETION);
//        }
//        if(gphf.getStatus() == Constant.PAYMENT_TYPE.DELIVER_GOODS){
//            GoodsPurchaseMailForm goodsPurchaseMailForm = new GoodsPurchaseMailForm();
//            goodsPurchaseMailForm.setSerialNumber(gphf.getSerialNumber());
//            goodsPurchaseMailForm.setPurchaseType(Constant.PURCHASE_TYPE.CARD);
//
//            PurchaseInfo purchaseInfo = goodsService.selectBySerialNumber(gphf.getSerialNumber());
//            goodsPurchaseMailForm.setName2(purchaseInfo.getFirstName1()+ " " + purchaseInfo.getLastName1());
//            goodsPurchaseMailForm.setZipcode2(purchaseInfo.getZipCode());
//            goodsPurchaseMailForm.setAddress2(purchaseInfo.getAddress1());
//            goodsPurchaseMailForm.setTel(purchaseInfo.getTel());
//            goodsPurchaseMailForm.setTel2(purchaseInfo.getTel());
//            goodsPurchaseMailForm.setDate(DateUtil.dateToStringyyyyMMddS(purchaseInfo.getCreateDatetime()));
//
//            Person person = personService.getPersonById(purchaseInfo.getUserId());
//            goodsPurchaseMailForm.setMail(person.getMail());
//            goodsPurchaseMailForm.setName(person.getFirstName()+ " " +person.getSecondName());
//            goodsPurchaseMailForm.setZipcode(person.getZipcode());
//            goodsPurchaseMailForm.setAddress(person.getAddress());
//
//            List<GoodsPurchase> purchaseList = goodsService.getGoodsPurchaseHistory(gphf.getSerialNumber());
//            List<PurchaseGoodsInfoForm> goodsInfoForms = new ArrayList<>();
//            double totalPrice = 700;
//            for(GoodsPurchase gp: purchaseList){
//                PurchaseGoodsInfoForm pgif = new PurchaseGoodsInfoForm();
//                Goods goods = goodsService.getGoodsById(gp.getGoodsId());
//                if(goods != null){
//                    String goodsName = goods.getTitle();
//                    pgif.setGoodsName(goodsName);
//                }
//                String color;
//                if(gp.getColors() == Constant.GOODS_COLOR.BLACK){
//                    color = "黒い";
//                }else if(gp.getColors() == Constant.GOODS_COLOR.WHITE){
//                    color = "白い";
//                }else{
//                    color = "色なし";
//                }
//                pgif.setColor(color);
//
//                String size;
//                if(gp.getSize() == Constant.GOODS_SIZE.XS){
//                    size = "XS";
//                }else if(gp.getSize() == Constant.GOODS_SIZE.S){
//                    size = "S";
//                }else if(gp.getSize() == Constant.GOODS_SIZE.M){
//                    size = "M";
//                }else if(gp.getSize() == Constant.GOODS_SIZE.L){
//                    size = "L";
//                }else if(gp.getSize() == Constant.GOODS_SIZE.XL){
//                    size = "XL";
//                }else{
//                    size = "フリーサイズ";
//                }
//                pgif.setSize(size);
//
//                String price = comdify(String.valueOf(gp.getPrice()));
//                pgif.setPrice(price);
//
//                String tax = comdify(String.valueOf((int)((gp.getPrice()/1.08)*0.08)));
//                pgif.setTax(tax);
//                pgif.setQuantity(gp.getQuantity());
//                goodsInfoForms.add(pgif);
//
//                totalPrice = totalPrice + gp.getPrice();
//            }
//
//            String totalPriceStr = comdify(String.valueOf((int)totalPrice));
//            goodsPurchaseMailForm.setTotalPrice(totalPriceStr);
//            goodsPurchaseMailForm.setDeliveryExpense(700);
//            goodsPurchaseMailForm.setUrl1("http://54.95.211.232/contact");
//            goodsPurchaseMailForm.setUrl2("http://54.95.211.232/purchaseHistory");
//            goodsPurchaseMailForm.setGoodsInfoForms(goodsInfoForms);
//
//            try {
//                awsService.sendGoodsPurchaseDeliverGoodsMail(purchaseInfo.getEmail(),goodsPurchaseMailForm);
//                logger.debug("GOODS sendMail success serialNumber="+ gphf.getSerialNumber());
//                session.setAttribute("error",0);
//            } catch (IOException e) {
//                session.setAttribute("error",1);
//                logger.error("GOODS sendMail fail serialNumber="+ gphf.getSerialNumber());
//            }
//
//        }
//        return "redirect:/admin/goods/purchase/list/";
//    }
//
//    private static String comdify(String value) {
//        DecimalFormat df = null;
//        if (value.indexOf(".") > 0) {
//            int i = value.length() - value.indexOf(".") - 1;
//            switch (i) {
//                case 0:
//                    df = new DecimalFormat("###,##0");
//                    break;
//                case 1:
//                    df = new DecimalFormat("###,##0.0");
//                    break;
//                case 2:
//                    df = new DecimalFormat("###,##0.00");
//                    break;
//                case 3:
//                    df = new DecimalFormat("###,##0.000");
//                    break;
//                case 4:
//                    df = new DecimalFormat("###,##0.0000");
//                    break;
//                default:
//                    df = new DecimalFormat("###,##0.00000");
//                    break;
//            }
//        } else {
//            df = new DecimalFormat("###,##0");
//        }
//        double number = 0.0;
//        try {
//            number = Double.parseDouble(value);
//        } catch (Exception e) {
//            number = 0.0;
//        }
//        return df.format(number);
//    }
//
//
//}
