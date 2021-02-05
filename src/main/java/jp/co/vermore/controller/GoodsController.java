//package jp.co.vermore.controller;
//
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gson.JsonArray;
//import jp.co.vermore.common.Constant;
//import jp.co.vermore.common.JsonStatus;
//import jp.co.vermore.common.mvc.APIException;
//import jp.co.vermore.common.util.DateUtil;
//import jp.co.vermore.common.util.StringUtil;
//import jp.co.vermore.entity.*;
//import jp.co.vermore.form.GoodsPurchaseMailForm;
//import jp.co.vermore.form.PurchaseEditForm;
//import jp.co.vermore.form.PurchaseGoodsInfoForm;
//import jp.co.vermore.jsonparse.*;
//import jp.co.vermore.service.*;
//
//import net.sf.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import jp.co.vermore.common.JsonObject;
//import jp.co.vermore.common.mvc.BaseController;
//
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * RiseController
// * Created by Wangyan.
// * <p>
// * DateTime: 2018/02/28 17:44
// * Copyright: sLab, Corp
// */
//@Controller
//public class GoodsController extends BaseController {
//
//    @Value(value = "${hosturl}")
//    private String hosturl;
//
//    @Value(value = "${payment.accept}")
//    private String accept;
//
//    @Value(value = "${payment.user.agent}")
//    private String userAgent;
//
//    @Value(value = "${payment.type.success}")
//    private Byte typeSuccess;
//
//    @Value(value = "${payment.type.return.goods}")
//    private Byte typeReturnGoods;
//
//    @Autowired
//    private GoodsService goodsService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private PersonService personService;
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private AWSService awsService;
//
//    //Goods list
//    @RequestMapping(value = "/api/goods/list/{limit}/{offset}/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject getGoodsList(@PathVariable int limit, @PathVariable int offset) {
//        List<Goods> list = goodsService.getGoodsAll(limit, offset);
//        List<Goods> list2 = goodsService.getGoodsTotal();
//        List<GoodsJsonParse> gjpList = new ArrayList<>();
//        for (Goods gd : list) {
//            GoodsJsonParse gjp = new GoodsJsonParse();
//            gjp.setUuid(gd.getUuid());
//            gjp.setTitle(gd.getTitle());
//            gjp.setGoodsType(gd.getGoodsType());
//            gjp.setBrand(gd.getBrand());
//            gjp.setPrice(gd.getPrice());
//            gjp.setPicUrl(gd.getPicUrl());
//            gjpList.add(gjp);
//        }
//        Map<String,Object> map = new HashMap<>();
//        map.put("goodsList",gjpList);
//        map.put("count",list2.size());
//        jsonObject.setResultList(map);
//        return jsonObject;
//    }
//
//    //Goods list search
//    @RequestMapping(value = "/api/goods/list/search/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject searchGoodsList(@RequestBody MultiValueMap<String, String> formData) {
//        String title = formData.getFirst("title");
//        String goodsType = formData.getFirst("goodsType");
//        int goodsTypeInt = 0;
//        if(goodsType!=null &&  !goodsType.equals("")){
//            goodsTypeInt = Integer.parseInt(goodsType);
//        }
//        String price = formData.getFirst("price");
//        int minPrice = 0;
//        int maxPrice = 0;
//        if(price!=null &&  !price.equals("")){
//            String[] prices = price.split("-");
//            minPrice = Integer.parseInt(prices[0]);
//            maxPrice = Integer.parseInt(prices[1]);
//        }
//        List<Goods> list = goodsService.searchGoodsList(title, goodsTypeInt, minPrice, maxPrice);
//        List  <GoodsJsonParse> gjpList = new ArrayList<>();
//        for (Goods gd : list) {
//            GoodsJsonParse gjp = new GoodsJsonParse();
//            gjp.setUuid(gd.getUuid());
//            gjp.setTitle(gd.getTitle());
//            gjp.setGoodsType(gd.getGoodsType());
//            gjp.setBrand(gd.getBrand()  );
//            gjp.setPrice(gd.getPrice());
//            gjp.setPicUrl(gd.getPicUrl());
//            gjpList.add(gjp);
//        }
//        Map<String,Object> map = new HashMap<>();
//        map.put("goodsList",gjpList);
//        map.put("count",gjpList.size());
//        jsonObject.setResultList(map);
//        return jsonObject;
//    }
//
//    //Goods detail
//    @RequestMapping(value = "/api/goods/{uuid}/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject getGoodsDetailByGoodsListId(@PathVariable String uuid) {
//        Goods goods = goodsService.getGoodsByUuid(uuid);
//        GoodsDetail list = goodsService.getGoodsDetailByGoodsListId(goods.getId());
//        List<GoodsDetailJsonParse> gdjpList = new ArrayList<>();
//        List<Stock> colorsList = goodsService.getColors(list.getId());
//        List<Stock> sizeList = goodsService.getSize(list.getId());
//        List stockList = new ArrayList();
//        for (Stock csd : colorsList) {
//            Byte colors = csd.getColors();
//            for (Stock ssd : sizeList) {
//                Stock stock = new Stock();
//                StockJsonParse sjp = new StockJsonParse();
//                stock.setGoodsId(list.getId());
//                stock.setColors(colors);
//                stock.setSize(ssd.getSize());
//                List<Stock> resultStock = goodsService.getAmount(stock);
//                if(resultStock.size()>0){
//                    sjp.setAmount(resultStock.get(0).getAmount());
//                    sjp.setColors(resultStock.get(0).getColors());
//                    sjp.setSize(resultStock.get(0).getSize());
//                    stockList.add(sjp);
//                }else{
//                    continue;
//                }
//
//            }
//        }
//
//        GoodsDetailJsonParse gdjp = new GoodsDetailJsonParse();
//        gdjp.setId(list.getGoodsId());
//        gdjp.setBrand(list.getBrand());
//        gdjp.setPrice(list.getPrice());
//        gdjp.setTitle(list.getTitle());
//        gdjp.setDesc1(list.getDesc1());
//        gdjp.setDesc2(list.getDesc2());
//        gdjp.setDesc3(list.getDesc3());
//        List<String> colorPicUrl = new ArrayList<String>();
//        List<String> descPicUrl = new ArrayList<String>();
//        if(list.getPicUrl1() != null && list.getPicUrl1() != ""){
//            colorPicUrl.add(list.getPicUrl1());
//        }
//        if(list.getPicUrl2() != null && list.getPicUrl2() != ""){
//            colorPicUrl.add(list.getPicUrl2());
//        }
//        if(list.getPicUrl3() != null && list.getPicUrl3() != ""){
//            colorPicUrl.add(list.getPicUrl3());
//        }
//        if(list.getPicUrl4() != null && list.getPicUrl4() != ""){
//            colorPicUrl.add(list.getPicUrl4());
//        }
//        if(list.getPicUrl5() != null && list.getPicUrl5() != ""){
//            colorPicUrl.add(list.getPicUrl5());
//        }
//        if(list.getPicUrl6() != null && list.getPicUrl6() != ""){
//            descPicUrl.add(list.getPicUrl6());
//        }
//        if(list.getPicUrl7() != null && list.getPicUrl7() != ""){
//            descPicUrl.add(list.getPicUrl7());
//        }
//        if(list.getPicUrl8() != null && list.getPicUrl8() != ""){
//            descPicUrl.add(list.getPicUrl8());
//        }
//        if(list.getPicUrl9() != null && list.getPicUrl9() != ""){
//            descPicUrl.add(list.getPicUrl9());
//        }
//        if(list.getPicUrl10() != null && list.getPicUrl10() != ""){
//            descPicUrl.add(list.getPicUrl10());
//        }
//        gdjp.setColorPicUrl(colorPicUrl);
//        gdjp.setDescPicUrl(descPicUrl);
//        gdjp.setStockList(stockList);
//        gdjpList.add(gdjp);
//
//        jsonObject.setResultList(gdjpList);
//        return jsonObject;
//    }
//
//    //Goods purchase cart list
//    @RequestMapping(value = "/api/goods/purchase/cart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject getGoodsPurchaseByUserId(HttpServletRequest req) throws APIException{
//        Long userId = authService.getUserId(req);
//        List<GoodsPurchase> list = goodsService.getGoodsPurchaseCart(userId,Constant.PURCHASE_STATUS.CART);
//        List<GoodsPurchaseJsonParse> gpjpList = new ArrayList<>();
//        int number = 1;
//        int number2 = 0;
//        int totalPrice = 0;
//        for (GoodsPurchase gpd : list) {
//            GoodsDetail goodsDetail = goodsService.getGoodsDetailByGoodsListId(gpd.getGoodsId());
//            GoodsPurchaseJsonParse gpjp = new GoodsPurchaseJsonParse();
//            gpjp.setNumber(number);
//            gpjp.setNumber2(number2);
//            number++;
//            number2++;
//            gpjp.setId(gpd.getId());
//            gpjp.setGoodsName(goodsDetail.getName());
//            gpjp.setSinglePrice(gpd.getPrice());
//            gpjp.setPrice(gpd.getPrice());
//            gpjp.setCoin(gpd.getCoin());
//            gpjp.setSerialNumber(gpd.getSerialNumber());
//            gpjp.setBrand(goodsDetail.getBrand());
//            gpjp.setQuantity(gpd.getQuantity());
//            gpjp.setPicUrl(goodsDetail.getPicUrl1());
//            gpjpList.add(gpjp);
//            totalPrice = totalPrice + gpd.getPrice();
//        }
//        int count = 0;
//        if(gpjpList != null){
//            count = gpjpList.size();
//        }
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("goodsList",gpjpList);
//        map.put("count",count);
//        if(list.size()>0){
//            map.put("serialNumber",list.get(0).getSerialNumber());
//        }else{
//            map.put("serialNumber","");
//        }
//        map.put("totalPrice",totalPrice);
//        jsonObject.setResultList(map);
//        return jsonObject;
//    }
//
//    //Goods purchase  add to cart
//    @RequestMapping(value = "/api/goods/purchase/addCart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseAdCart(@RequestBody MultiValueMap<String, String> formData ,HttpServletRequest req) throws APIException{
//        GoodsPurchase gpd = new GoodsPurchase();
//        Long userId = authService.getUserId(req);
//        gpd.setUserId(userId);
//        List<GoodsPurchase> list = goodsService.getGoodsPurchaseCart(userId,Constant.PURCHASE_STATUS.CART);
//        if(list.size()>0){
//            int stutas = 0;
//
//            gpd.setSerialNumber(list.get(0).getSerialNumber());
//            for(GoodsPurchase gp : list){
//                if(gp.getGoodsId() == Long.parseLong(formData.getFirst("goodsId")) &&
//                   gp.getColors() == Byte.valueOf(formData.getFirst("color")) &&
//                   gp.getSize() == Byte.valueOf(formData.getFirst("size"))
//                 ){
//                    int quantity = gp.getQuantity();
//                    int price = gp.getPrice()/gp.getQuantity();
//                    gp.setQuantity(quantity+1);
//                    gp.setPrice(price*gp.getQuantity());
//                    goodsService.updateGoodsPurchase(gp);
//                    gpd.setGoodsId(gp.getGoodsId());
//                    gpd.setPrice(gp.getPrice());
//                    gpd.setQuantity(gp.getQuantity());
//                    gpd.setColors(gp.getColors());
//                    gpd.setSize(gp.getSize());
//                    stutas++;
//                }
//            }
//            if(stutas == 0){
//                gpd.setGoodsId(Long.parseLong(formData.getFirst("goodsId")));
//                gpd.setPrice(Integer.parseInt(formData.getFirst("price")));
//                gpd.setCoin(0);
//                gpd.setQuantity(Integer.parseInt(formData.getFirst("quantity")));
//                gpd.setStatus(Constant.PURCHASE_STATUS.CART);
//                gpd.setColors(Byte.valueOf(formData.getFirst("color")));
//                gpd.setSize(Byte.valueOf(formData.getFirst("size")));
//                goodsService.insertGoodsPurchase(gpd);
//            }
//        }else {
//            gpd.setSerialNumber(StringUtil.getSerialNumber());
//            gpd.setGoodsId(Long.parseLong(formData.getFirst("goodsId")));
//            gpd.setPrice(Integer.parseInt(formData.getFirst("price")));
//            gpd.setCoin(0);
//            gpd.setQuantity(Integer.parseInt(formData.getFirst("quantity")));
//            gpd.setStatus(Constant.PURCHASE_STATUS.CART);
//            gpd.setColors(Byte.valueOf(formData.getFirst("color")));
//            gpd.setSize(Byte.valueOf(formData.getFirst("size")));
//            goodsService.insertGoodsPurchase(gpd);
//        }
//        jsonObject.setResultList(gpd);
//        return jsonObject;
//    }
//
//    //Goods purchase  add to cart
//    @RequestMapping(value = "/api/goods/purchase/end/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseEnd(@RequestBody MultiValueMap<String, String> formData ,HttpServletRequest req) throws APIException{
//        Long userId = authService.getUserId(req);
//        Person person = personService.getPersonById(userId);
//        GoodsPurchaseMailForm goodsPurchaseMailForm = new GoodsPurchaseMailForm();
//        goodsPurchaseMailForm.setMail(person.getMail());
//        goodsPurchaseMailForm.setName(person.getFirstName()+ " " +person.getSecondName());
//        goodsPurchaseMailForm.setZipcode(person.getZipcode());
//        goodsPurchaseMailForm.setAddress(person.getAddress());
//        goodsPurchaseMailForm.setSerialNumber(formData.getFirst("orderID"));
//        goodsPurchaseMailForm.setPurchaseType(Constant.PURCHASE_TYPE.CARD);
//
//        PurchaseInfo purchaseInfo = goodsService.selectBySerialNumber(formData.getFirst("orderID"));
//        goodsPurchaseMailForm.setName2(purchaseInfo.getFirstName1()+ " " + purchaseInfo.getLastName1());
//        goodsPurchaseMailForm.setZipcode2(purchaseInfo.getZipCode());
//        goodsPurchaseMailForm.setAddress2(purchaseInfo.getAddress1());
//        goodsPurchaseMailForm.setTel(purchaseInfo.getTel());
//        goodsPurchaseMailForm.setTel2(purchaseInfo.getTel());
//        goodsPurchaseMailForm.setDate(DateUtil.dateToStringyyyyMMddS(purchaseInfo.getCreateDatetime()));
//
//        List<GoodsPurchase> purchaseList = goodsService.getGoodsPurchaseHistory(formData.getFirst("orderID"));
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
//            String price = comdify(String.valueOf((int)(gp.getPrice()*1.08)));
//            pgif.setPrice(price);
//
//            String tax = comdify(String.valueOf((int)(gp.getPrice()*0.08)));
//            pgif.setTax(tax);
//            pgif.setQuantity(gp.getQuantity());
//            goodsInfoForms.add(pgif);
//
//            totalPrice = totalPrice + (int)(gp.getPrice()*1.08);
//        }
//
//        String totalPriceStr = comdify(String.valueOf((int)totalPrice));
//        goodsPurchaseMailForm.setTotalPrice(totalPriceStr);
//        goodsPurchaseMailForm.setDeliveryExpense(700);
//        goodsPurchaseMailForm.setUrl1(hosturl + "/contact");
//        goodsPurchaseMailForm.setUrl2(hosturl + "/purchaseHistory");
//        goodsPurchaseMailForm.setGoodsInfoForms(goodsInfoForms);
//
//        try {
//            awsService.sendGoodsPurchaseMail(person.getMail(),goodsPurchaseMailForm);
//            logger.debug("GOODS sendMail success serialNumber="+ formData.getFirst("orderID") + "userId="+ userId);
//        } catch (IOException e) {
//            logger.error("GOODS sendMail fail serialNumber="+ formData.getFirst("orderID") + "userId="+ userId);
//        }
//        return jsonObject;
//    }
//
//    //Goods purchase  out to cart
//    @RequestMapping(value = "/api/goods/purchase/outCart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseOutCart(@RequestBody MultiValueMap<String, String> formData) throws APIException{
//        GoodsPurchase gpd = new GoodsPurchase();
//        gpd.setId(Long.valueOf(formData.getFirst("id")));
//        goodsService.outGoodsPurchase(gpd);
//        jsonObject.setResultList(gpd);
//        return jsonObject;
//    }
//
//    //Goods purchase  edit cart
//    @RequestMapping(value = "/api/goods/purchase/editCart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseEditCart(@ModelAttribute PurchaseEditForm formData) throws APIException{
//        for(GoodsPurchaseCartJsonParse gp : formData.getCartList()){
//            logger.info("formData cartList id = "+String.valueOf(gp.getId()));
//            logger.info("formData cartList price = "+String.valueOf(gp.getPrice()));
//            logger.info("formData cartList quantity = "+String.valueOf(gp.getQuantity()));
//        }
//        List<GoodsPurchaseCartJsonParse> list = formData.getCartList();
//        String serialNumber = StringUtil.getSerialNumber();
//        for(GoodsPurchaseCartJsonParse cart : list){
//            GoodsPurchase gpd = new GoodsPurchase();
//            gpd.setSerialNumber(serialNumber);
//            gpd.setId(cart.getId());
//            int price = (int) (cart.getPrice()/1.08);
//            gpd.setPrice(price);
//            gpd.setQuantity(cart.getQuantity());
//            goodsService.updateGoodsPurchase(gpd);
//        }
//
//        jsonObject.setResultList(null);
//        return jsonObject.withStatus(JsonStatus.SUCCESS);
//    }
//
//    //Goods purchase  edit cart
//    @RequestMapping(value = "/api/app/goods/purchase/editCart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseEditCartForApp(@RequestBody PurchaseEditForm formData) throws APIException{
//        for(GoodsPurchaseCartJsonParse gp : formData.getCartList()){
//            logger.info("formData cartList id = "+String.valueOf(gp.getId()));
//            logger.info("formData cartList price = "+String.valueOf(gp.getPrice()));
//            logger.info("formData cartList quantity = "+String.valueOf(gp.getQuantity()));
//        }
//        List<GoodsPurchaseCartJsonParse> list = formData.getCartList();
//        String serialNumber = StringUtil.getSerialNumber();
//        for(GoodsPurchaseCartJsonParse cart : list){
//            GoodsPurchase gpd = new GoodsPurchase();
//            gpd.setSerialNumber(serialNumber);
//            gpd.setId(cart.getId());
//            gpd.setPrice(cart.getPrice());
//            gpd.setQuantity(cart.getQuantity());
//            goodsService.updateGoodsPurchase(gpd);
//        }
//
//        jsonObject.setResultList(null);
//        return jsonObject.withStatus(JsonStatus.SUCCESS);
//    }
//
//    //Goods purchase  edit cart
//    @RequestMapping(value = "/api/android/goods/purchase/editCart/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject goodsPurchaseEditCartForAndroid(@RequestParam("cartList") String cartList) throws APIException{
//            Map<String, Class<GoodsPurchaseCartJsonParse>> map = new HashMap<String, Class<GoodsPurchaseCartJsonParse>>();
//            map.put("cartList",GoodsPurchaseCartJsonParse.class);
//            JSONObject jsObject = JSONObject.fromObject(cartList);
//            PurchaseEditForm formData = (PurchaseEditForm)JSONObject.toBean(jsObject, PurchaseEditForm.class, map);
////            for(GoodsPurchaseCartJsonParse gp : formData.getCartList()){
////                logger.info("formData cartList id = "+String.valueOf(gp.getId()));
////                logger.info("formData cartList price = "+String.valueOf(gp.getPrice()));
////                logger.info("formData cartList quantity = "+String.valueOf(gp.getQuantity()));
////            }
//            List<GoodsPurchaseCartJsonParse> list = formData.getCartList();
//            String serialNumber = StringUtil.getSerialNumber();
//            for(GoodsPurchaseCartJsonParse cart : list){
//                GoodsPurchase gpd = new GoodsPurchase();
//                gpd.setSerialNumber(serialNumber);
//                gpd.setId(cart.getId());
//                gpd.setPrice(cart.getPrice());
//                gpd.setQuantity(cart.getQuantity());
//                goodsService.updateGoodsPurchase(gpd);
//            }
//
//        jsonObject.setResultList(null);
//        return jsonObject.withStatus(JsonStatus.SUCCESS);
//    }
//
//    //Purchase info confirm
//    @RequestMapping(value = "/api/goods/purchase/infoConfirm/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject PurchaseInfoInsert(@RequestBody MultiValueMap<String, String> formData) {
//        PurchaseInfo purchaseInfo = new PurchaseInfo();
//        long userId = Long.parseLong(formData.getFirst("userId"));
//        purchaseInfo.setUserId(userId);
//        purchaseInfo.setSerialNumber(formData.getFirst("serialNumber"));
//        purchaseInfo.setFirstName1(formData.getFirst("firstName1"));
//        purchaseInfo.setLastName1(formData.getFirst("lastName1"));
//        if(formData.getFirst("firstName2") != null && formData.getFirst("firstName2") != ""){
//            purchaseInfo.setFirstName2(formData.getFirst("firstName2"));
//        }else {
//            purchaseInfo.setFirstName2("");
//        }
//        if(formData.getFirst("lastName2") != null && formData.getFirst("lastName2") != ""){
//            purchaseInfo.setLastName2(formData.getFirst("lastName2"));
//        }else {
//            purchaseInfo.setLastName2("");
//        }
//        purchaseInfo.setEmail(formData.getFirst("email"));
//        purchaseInfo.setZipCode(formData.getFirst("zipCode"));
//        if(formData.getFirst("address1") != null && formData.getFirst("address1") != ""){
//            purchaseInfo.setAddress1(formData.getFirst("address1"));
//        }else {
//            purchaseInfo.setAddress1("");
//        }
//        if(formData.getFirst("address2") != null && formData.getFirst("address2") != ""){
//            purchaseInfo.setAddress2(formData.getFirst("address2"));
//        }else {
//            purchaseInfo.setAddress2("");
//        }
//        if(formData.getFirst("tel") != null && formData.getFirst("tel") != ""){
//            purchaseInfo.setTel(formData.getFirst("tel"));
//        }else {
//            purchaseInfo.setTel("");
//        }
//        goodsService.insertPurchaseInfo(purchaseInfo);
//        jsonObject.setResultList(purchaseInfo);
//        return jsonObject;
//    }
//
//    //Purchase info
//    @RequestMapping(value = "/api/goods/purchase/info/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject PurchaseInfo(@RequestBody MultiValueMap<String, String> formData,HttpServletRequest req) throws APIException{
//        PurchaseInfoJsonParse pijp = new PurchaseInfoJsonParse();
//        Long userId = authService.getUserId(req);
//        pijp.setUserId(userId);
//        pijp.setFirstName1(formData.getFirst("firstName1"));
//        pijp.setLastName1(formData.getFirst("lastName1"));
//        pijp.setFirstName2(formData.getFirst("firstName2"));
//        pijp.setLastName2(formData.getFirst("lastName2"));
//        pijp.setEmail(formData.getFirst("email"));
//        pijp.setZipCode(formData.getFirst("zipCode"));
//        pijp.setAddress1(formData.getFirst("address1"));
//        pijp.setAddress2(formData.getFirst("address2"));
//        pijp.setTel(formData.getFirst("tel"));
//        jsonObject.setResultList(pijp);
//        return jsonObject;
//    }
//
//    //Goods purchase history list
//    @RequestMapping(value = "/api/goods/purchase/history/", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonObject getGoodsPurchaseHistoryByUserId(HttpServletRequest req) throws APIException{
//        Long userId = authService.getUserId(req);
//        List<PaymentHistory> paymentHistory = paymentService.getPaymentHistoryByUserId(userId);
//        List<Map<String, Object>> resultMap = new ArrayList<>();
//        PaymentHistoryJsonParse pgjp = new PaymentHistoryJsonParse();
//        int countTotal = 0;
//        for(PaymentHistory ph :paymentHistory){
//            List<GoodsPurchase> list = goodsService.getGoodsPurchaseHistory(ph.getOrderId());
//            List<GoodsPurchaseJsonParse> gpjpList = new ArrayList<>();
//            int number = 1;
//            int number2 = 0;
//            int totalPrice = 0;
//            for (GoodsPurchase gpd : list) {
//                GoodsDetail goodsDetail = goodsService.getGoodsDetailByGoodsListId(gpd.getGoodsId());
//                GoodsPurchaseJsonParse gpjp = new GoodsPurchaseJsonParse();
//                gpjp.setNumber(number);
//                gpjp.setNumber2(number2);
//                number++;
//                number2++;
//                gpjp.setId(gpd.getId());
//                gpjp.setGoodsName(goodsDetail.getName());
//                gpjp.setPrice(gpd.getPrice());
//                gpjp.setCoin(gpd.getCoin());
//                gpjp.setSerialNumber(gpd.getSerialNumber());
//                gpjp.setBrand(goodsDetail.getBrand());
//                gpjp.setQuantity(gpd.getQuantity());
//                gpjp.setPicUrl(goodsDetail.getPicUrl1());
//                gpjpList.add(gpjp);
//                totalPrice = totalPrice + gpd.getPrice();
//            }
//
//            int count = 0;
//            if(gpjpList != null){
//                count = gpjpList.size();
//            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("goodsList",gpjpList);
//            map.put("count",count);
//            if(list.size()>0){
//                map.put("serialNumber",list.get(0).getSerialNumber());
//            }else{
//                map.put("serialNumber","");
//            }
//            if(paymentHistory.size()>0){
//                map.put("date", DateUtil.dateToStringyyyyMMddS(paymentHistory.get(0).getCreateDatetime()));
//            }
//
//            map.put("totalPrice",totalPrice);
//            if(gpjpList.size()>0){
//                resultMap.add(map);
//                countTotal++;
//            }else{
//                continue;
//            }
//        }
//        pgjp.setHistoryList(resultMap);
//        pgjp.setCounts(countTotal);
//        jsonObject.setResultList(pgjp);
//        return jsonObject;
//    }
//
//    //Payment
//    @RequestMapping(value = "/api/goods/payment/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject payment(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest req)throws APIException{
//        Long userId = authService.getUserId(req);
//        PaymentEntryTran entryTran = new PaymentEntryTran();
//        PaymentExecTran execTran = new PaymentExecTran();
//        PaymentReceipt receipt;
//        PaymentReceipt searchReceipt;
//
//        searchReceipt = paymentService.SearchTrade(formData.getFirst("orderID"));
//        if(searchReceipt.getErrList().size()>0){
//            entryTran.setAmount(formData.getFirst("amount"));
//            entryTran.setTax(formData.getFirst("tax"));
//            entryTran.setOrderID(formData.getFirst("orderID"));
//            execTran = paymentService.doPaymentEntryTran(entryTran);
//        }else{
//            execTran.setAccessID(searchReceipt.getAccessID());
//            execTran.setAccessPass(searchReceipt.getAccessPass());
//            execTran.setOrderID(formData.getFirst("orderID"));
//        }
//
//        execTran.setHttpAccept(req.getHeader(accept));
//        execTran.setHttpUserAgent(req.getHeader(userAgent));
//        execTran.setCardNo(formData.getFirst("cardNo"));
//        execTran.setExpire(formData.getFirst("expire"));
//        execTran.setSecurityCode(formData.getFirst("securityCode"));
//        execTran.setName(formData.getFirst("name"));
//        execTran.setGoodsID(formData.getFirst("goodsID"));
//        receipt = paymentService.doPaymentExecTran(execTran);
//        if(receipt.getErrList().size() == 0){
//            receipt.setUserID(String.valueOf(userId));
//            receipt.setGoodsID(formData.getFirst("goodsID"));
//            receipt.setAmount(Integer.parseInt(formData.getFirst("amount")));
//            receipt.setPaymentType(typeSuccess);
//            Long id = paymentService.insertPaymentHistory(receipt);
//
//            GoodsPurchase goodsPurchase = new GoodsPurchase();
//            goodsPurchase.setSerialNumber(formData.getFirst("orderID"));
//            goodsPurchase.setStatus(Constant.PURCHASE_STATUS.PURCHASE_SUCCESS);
//            goodsService.updateBySerialNumberSelective(goodsPurchase);
//        }
//
//        jsonObject.setResultList(receipt);
//        return jsonObject;
//    }
//
//    //AlterTran
//    @RequestMapping(value = "/api/goods/alterTran/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject alterTran(@RequestBody MultiValueMap<String, String> formData){
//        PaymentReceipt receipt = new PaymentReceipt();
//        PaymentHistory paymentHistory = paymentService.doAlterTran(formData.getFirst("orderID"));
//        receipt.setPaymentType(typeReturnGoods);
//        receipt.setUserID(String.valueOf(paymentHistory.getUserId()));
//        receipt.setGoodsID(String.valueOf(paymentHistory.getGoodsId()));
//        receipt.setAccessID(paymentHistory.getAccessID());
//        receipt.setAccessPass(paymentHistory.getAccessPass());
//        receipt.setApprove(paymentHistory.getApprove());
//        receipt.setForward(paymentHistory.getForward());
//        receipt.setMethod(paymentHistory.getMethod());
//        receipt.setTranID(paymentHistory.getTranId());
//        receipt.setOrderID(paymentHistory.getOrderId());
//        receipt.setCheckString(paymentHistory.getCheckString());
//        receipt.setAmount(paymentHistory.getAmount());
//        paymentHistory.setPaymentType(typeReturnGoods);
//        Long id = paymentService.insertPaymentHistory(receipt);
//        jsonObject.setResultList(receipt);
//        return jsonObject;
//    }
//
//    //Payment Cvs
//    @RequestMapping(value = "/api/goods/payment/cvs/", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonObject paymentCvs(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest req) throws APIException{
//        Long userId = authService.getUserId(req);
//        PaymentEntryTran entryTran = new PaymentEntryTran();
//        PaymentExecTranCvs execTranCvs;
//        PaymentReceiptCvs receipt;
//
//        entryTran.setAmount(formData.getFirst("amount"));
//        entryTran.setTax(formData.getFirst("tax"));
//        entryTran.setOrderID(formData.getFirst("orderID"));
//
//        execTranCvs = paymentService.doPaymentEntryTranCvs(entryTran);
//        execTranCvs.setCustomerName(formData.getFirst("customerName"));
//        execTranCvs.setCustomerKana(formData.getFirst("customerKana"));
//        execTranCvs.setTelNo(formData.getFirst("telNo"));
//        execTranCvs.setGoodsID("1"); //todo
//        execTranCvs.setMail(formData.getFirst("mail"));
//
//        receipt = paymentService.doPaymentExecTranCvs(execTranCvs);
//
//        if(receipt.getErrList().size() == 0) {
//            receipt.setUserID(String.valueOf(userId));
//            receipt.setAmount(Integer.parseInt(entryTran.getAmount()));
//            receipt.setPaymentStatus(Constant.PURCHASE_STATUS.CONVENIENCE_STORE_TO_BE_PAID);
//            paymentService.insertPaymentHistoryCvs(receipt);
//
//            GoodsPurchase goodsPurchase = new GoodsPurchase();
//            goodsPurchase.setSerialNumber(formData.getFirst("orderID"));
//            goodsPurchase.setStatus(Constant.PURCHASE_STATUS.CONVENIENCE_STORE_TO_BE_PAID);
//            goodsService.updateBySerialNumberSelective(goodsPurchase);
//        }
//
//        jsonObject.setResultList(receipt);
//        return jsonObject;
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
//}
//
