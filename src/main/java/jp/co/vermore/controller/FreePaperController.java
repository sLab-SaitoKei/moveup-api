package jp.co.vermore.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.FavDetail;
import jp.co.vermore.entity.FreePaper;
import jp.co.vermore.entity.FreePaperDetail;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.AuthService;
import jp.co.vermore.service.FavDetailService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.jsonparse.FreePaperDetailJsonParse;
import jp.co.vermore.jsonparse.FreePaperJsonParse;
import jp.co.vermore.service.FreePaperService;

import javax.servlet.http.HttpServletRequest;

/**
 * FreePaperController
 * Created by wubin.
 * <p>
 * DateTime: 2018/03/20 9:27
 * Copyright: sLab, Corp
 */
@Controller
public class FreePaperController extends BaseController {

    @Autowired
    private FreePaperService freePaperService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FavDetailService favDetailService;


    //freePaper list
    @RequestMapping(value = "/api/freePaper/list/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFreePaperList(@PathVariable int limit, @PathVariable int offset, HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        List<FreePaper> list = freePaperService.getFreePaperAll(limit, offset);
        List<FreePaper> list2 = freePaperService.getFreePaper();
        List<FreePaperJsonParse> fpjpList = new ArrayList<>();
        List<FavDetail> favDetail = favDetailService.getFavDetailAll(userId, Constant.FAV_TYPE.FREEPAPER);
        for (FreePaper fpd : list) {
            FreePaperJsonParse fpjp = new FreePaperJsonParse();
            fpjp.setId(fpd.getId());
            fpjp.setUuid(fpd.getUuid());
            fpjp.setRanking(fpd.getVolume());
            fpjp.setPicUrl(fpd.getPicUrl());
            fpjp.setTitle(fpd.getTitle());

            String YYYYMMDD = DateUtil.dateToStringyyyy_MM_dd(fpd.getDate());
            String[] yyyymmdd = YYYYMMDD.split("-");
            String mmdd = yyyymmdd[1] + "/" + yyyymmdd[2];

            fpjp.setYear(yyyymmdd[0]);
            fpjp.setDate(mmdd);
            fpjp.setWeekDay(DateUtil.getWeekOfDate(fpd.getDate()));
            if (favDetail.size() > 0) {
                for (FavDetail fd : favDetail) {
                    if (fpd.getId() == fd.getShopId()) {
                        fpjp.setFavorite(true);
                    }
                }
            } else {
                fpjp.setFavorite(false);
            }
            fpjpList.add(fpjp);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("fpjpList", fpjpList);
        map.put("count",list2.size());
//        map.put("count", 5);
        jsonObject.setResultList(map);
        return jsonObject;
    }

    //freePaper list
    @RequestMapping(value = "/api/freePaper/list/year/{year}/{limit}/{offset}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFreePaperByYear(@PathVariable String year, @PathVariable int limit, @PathVariable int offset) {
        List<FreePaper> list = freePaperService.getFreePaperByYear(year, limit, offset);
        List<FreePaperJsonParse> fpjpList = new ArrayList<>();
        for (FreePaper fpd : list) {
            FreePaperJsonParse fpjp = new FreePaperJsonParse();
            fpjp.setId(fpd.getId());
            fpjp.setUuid(fpd.getUuid());
            fpjp.setRanking(fpd.getVolume());
            fpjp.setPicUrl(fpd.getPicUrl());
            fpjp.setTitle(fpd.getTitle());

            String YYYYMMDD = DateUtil.dateToStringyyyy_MM_dd(fpd.getDate());
            String[] yyyymmdd = YYYYMMDD.split("-");
            String mmdd = yyyymmdd[1] + "/" + yyyymmdd[2];

            fpjp.setYear(yyyymmdd[0]);
            fpjp.setDate(mmdd);
            fpjp.setWeekDay(DateUtil.getWeekOfDate(fpd.getDate()));

            fpjpList.add(fpjp);
        }
        jsonObject.setResultList(fpjpList);
        return jsonObject;
    }

    //freePaper detail
    @RequestMapping(value = "/api/freePaper/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFreePaperDetailList(@PathVariable String uuid) {
        List<FreePaperDetailJsonParse> ejpList = new ArrayList<>();
        FreePaper freePaper = freePaperService.getFreePaperByUuid(uuid);
        List<FreePaperDetail> list = freePaperService.getFreePaperDetailAll(freePaper.getId(), Constant.FREEPAPER_PIC_TYPE.IMG);
        for (FreePaperDetail ed : list) {
            FreePaperDetailJsonParse ejp = new FreePaperDetailJsonParse();
            ejp.setFreePaperId(ed.getFreePaperId());
            ejp.setPicUrl(ed.getPicUrl());
            ejpList.add(ejp);
        }
        jsonObject.setResultList(ejpList);
        return jsonObject;
    }

    //freePaper detail
    @RequestMapping(value = "/api/app/freePaper/detail/{uuid}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFreePaperDetailListForApp(@PathVariable String uuid) {
        List<FreePaperDetailJsonParse> ejpList = new ArrayList<>();
        FreePaper freePaper = freePaperService.getFreePaperByUuid(uuid);
        List<FreePaperDetail> list = freePaperService.getFreePaperDetailAll(freePaper.getId(), Constant.FREEPAPER_PIC_TYPE.PDF);
        for (FreePaperDetail ed : list) {
            FreePaperDetailJsonParse ejp = new FreePaperDetailJsonParse();
            ejp.setFreePaperId(ed.getFreePaperId());
            ejp.setPicUrl(ed.getPicUrl());
            ejpList.add(ejp);
        }
        jsonObject.setResultList(ejpList);
        return jsonObject;
    }

    //freePaper detail
    @RequestMapping(value = "/batch/freePaper/s3pdftopic/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject S3pdftopic() throws Exception {

        List<FreePaperDetail> freePaperDetailList = freePaperService.getFreePaperDetailAll();

        for (FreePaperDetail ed : freePaperDetailList) {


            URL url = new URL(ed.getPicUrl());

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setAllowUserInteraction(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.connect();
            int httpStatusCode = conn.getResponseCode();
            if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception();
            }
            // Input Stream
            DataInputStream dataInStream = new DataInputStream(conn.getInputStream());

            // Output Stream
            DataOutputStream dataOutStream = new DataOutputStream(
                    new FileOutputStream("/tmp/" + ed.getPicUrl().substring(60) + ".pdf"));

            // Read Data
            byte[] b = new byte[4096];
            int readByte = 0;
            while (-1 != (readByte = dataInStream.read(b))) {
                dataOutStream.write(b, 0, readByte);
            }

            // Close Stream
            dataInStream.close();
            dataOutStream.close();

            File f = new File("/tmp/" + ed.getPicUrl().substring(60) + ".pdf");
            // png to s3

            File pngF = pdfToPng(f);

            String s3PngFilePath = awsService.postFile(pngF);

            // insert to db with new s3
            FreePaperDetail freePaperDetail = new FreePaperDetail();
            freePaperDetail.setCreateDatetime(DateUtil.getNowDate());
            freePaperDetail.setDelFlg(false);
            freePaperDetail.setFreePaperId(ed.getFreePaperId());
            freePaperDetail.setNote("");
            freePaperDetail.setPicUrl(s3PngFilePath);
            freePaperDetail.setUpdateDatetime(DateUtil.getDefaultDate());
            freePaperDetail.setPublishStart(ed.getPublishStart());
            freePaperDetail.setPublishEnd(ed.getPublishEnd());
            freePaperDetail.setPicType(Constant.FREEPAPER_PIC_TYPE.IMG);
            freePaperService.insertFreePaperDetail(freePaperDetail);
        }
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    private static File pdfToPng(File pdf) {
        File tempFile = null;
        try {
            // load PDF document
            PDDocument document = PDDocument.load(pdf);

            // create renderer
            PDFRenderer renderer = new PDFRenderer(document);

            // write images to files to disk as PNG
            if (document.getPages().getCount() > 0) {
                tempFile = File.createTempFile(pdf.getName(), ".png");
                BufferedImage bim = renderer.renderImageWithDPI(0, 120, ImageType.RGB);
                Thumbnails.of(bim).scale(0.8).outputQuality(0.8).toFile(tempFile);
            }
            document.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            logger.error("---------------e" + e.getStackTrace());
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            logger.error("---------------e" + stack.toString());
        }
        return tempFile;
    }

}
