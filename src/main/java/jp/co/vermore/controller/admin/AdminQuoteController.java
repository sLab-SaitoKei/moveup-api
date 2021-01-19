package jp.co.vermore.controller.admin;


import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.Shop;
import jp.co.vermore.form.PdfForm;
import jp.co.vermore.form.admin.SettlementEditForm;
import jp.co.vermore.kit.test.java.pdf.kit.ReportKit360;
import jp.co.vermore.kit.test.java.pdf.kit.TemplateBO;
import jp.co.vermore.service.SettlementService;
import jp.co.vermore.service.ShopService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * NewsController
 * Created by jiahongwei.
 * <p>
 * DateTime: 2018/03/30 11:13
 * Copyright: sLab, Corp
 */
@Controller
public class AdminQuoteController extends BaseController {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/admin/quote/list/", method = RequestMethod.GET)
    public String quoteAll(Model model) {
        return "admin/quoteList";
    }


    //    @ResponseBody
    @RequestMapping(value = "/admin/quote/pdf/{shopUUID}/{month}/", method = RequestMethod.GET)
    public ResponseEntity<byte[]> recruitInsert(HttpServletRequest request, Model model, @PathVariable String shopUUID, @PathVariable int month) throws IOException {

        List<SettlementEditForm> sett = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String time = df.format(new Date());
        Shop shop = new Shop();
        shop = shopService.getShopByUuid(shopUUID);
        Long shopId = shop.getId();
        String shopName = shopService.getShopNameByShopIdShop(shopId);
        List<SettlementEditForm> dataList = settlementService.getSettlementEditByMonth2(shopId, month);
        String yyyy = Integer.toString(month).substring(0, 4);
        String mm = Integer.toString(month).substring(4);
        String box = null;
        int totlePrice = 0;
        String totlePriceStr ="";
        List<PdfForm> scores = new ArrayList<PdfForm>();
        for (SettlementEditForm se :
                dataList) {
            PdfForm pdfForm = new PdfForm();
            pdfForm.setTitle(se.getTitle());
            pdfForm.setMonth(String.valueOf(month));
            if (se.getType() == 1) {
                pdfForm.setType("日");
            } else {
                pdfForm.setType("月");
            }
            box = se.getShopName();
            pdfForm.setAmount("￥"+StringUtil.getPrice(se.getAmount()));
            scores.add(pdfForm);
            totlePrice = totlePrice + se.getAmount();
        }
        String name = box + "_" + month;
        totlePriceStr = "￥"+String.valueOf(totlePrice);
        int tax = (int)(totlePrice * 0.08);
        String taxStr = "￥"+StringUtil.getPrice(tax);
        String subtotal = "￥"+StringUtil.getPrice((totlePrice + tax));

        ReportKit360 kit = new ReportKit360();
        TemplateBO templateBO = new TemplateBO();
        templateBO.setTime(time);
        templateBO.setShopName(shopName);
        templateBO.setYyyy(yyyy);
        templateBO.setMm(mm);
        templateBO.setTotlePrice(totlePriceStr);
        templateBO.setTax(taxStr);
        templateBO.setSubtotal(subtotal);
        templateBO.setScores(scores);

        String path2 = request.getSession().getServletContext().getRealPath("/");
//                String htmlPath = path2 + "static/pdf/template";
        String templatePath = "/Users/fgm/workspaces/fix/pdf-kit/src/test/resources/templates";
//            String htmlPath = "C:/apache-tomcat-8.0.50/webapps/ROOT/static/pdf/template";

        String path = kit.createPDF(path2, name, templatePath, templateBO, "hello.pdf");

        String pdfUrl = path2 + "static/pdf/pdf/" + name + ".pdf";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        headers.setContentDispositionFormData("attachment", new String((name + ".pdf").getBytes("UTF-8"), "iso-8859-1"));

        return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(pdfUrl)), headers, HttpStatus.CREATED));

    }

}