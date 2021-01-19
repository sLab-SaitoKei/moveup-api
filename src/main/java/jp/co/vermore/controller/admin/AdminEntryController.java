package jp.co.vermore.controller.admin;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.DatatablesJsonObject;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.RiseRegistForm;
import jp.co.vermore.form.admin.AdminEntryForm;
import jp.co.vermore.form.admin.EntryListForm;
import jp.co.vermore.form.admin.EntryMailForm;
import jp.co.vermore.form.admin.EntryMailListForm;
import jp.co.vermore.service.*;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * AdminEntryController
 * Created by wubin.
 * <p>
 * DateTime: 2018/05/07 16:17
 * Copyright: sLab, Corp
 */
@Controller
public class AdminEntryController extends BaseController {

    @Autowired
    private EntryService entryService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AuthService authService;

    @Autowired
    PlatformTransactionManager txManager;

    private String orderStatement = "";

    @RequestMapping(value = "/admin/entry/list/", method = RequestMethod.GET)
    public String entry(Model model) {
        return "admin/entryList";
    }

    @RequestMapping(value = "/admin/entry/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject entryList(@RequestBody EntryListForm form) {

        int sortflag = 0;

        logger.debug("----1----");
        // set order statement
        if (form.getOrder().size() > 0
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName() != null
                && form.getColumns().get(form.getOrder().get(0).getColumn()).getName().length() > 0) {
            if (form.getColumns().get(form.getOrder().get(0).getColumn()).getName().equals("age")) {
                form.setOrderStatement("id" + " " + form.getOrder().get(0).getDir());
                if (form.getOrder().get(0).getDir().equals("desc")) {
                    System.out.println("desc");
                    sortflag = 2;
                } else if (form.getOrder().get(0).getDir().equals("asc")) {
                    System.out.println("asc");
                    sortflag = 1;
                }
            } else {
                form.setOrderStatement(form.getColumns().get(form.getOrder().get(0).getColumn()).getName() + " " + form.getOrder().get(0).getDir());
            }

            logger.debug("----2----order statement=" + form.getOrderStatement());
        } else {
            form.setOrderStatement("id");
            logger.debug("----2----order statement=" + form.getOrderStatement());
        }
        logger.debug("----3----");
        orderStatement = form.getOrderStatement();
        // query data
        List<Entry> dataList = entryService.getEntryAllByCondition(form);
        List<EntryListForm> personList = new ArrayList<>();
        int totalCountFiltered = entryService.getEntryCountByCondition(form);
        int totalCount = entryService.getEntryCount();

//        List<Entry> nameLikeList = new ArrayList<Entry>();
        for (Entry entry : dataList) {

            String name = entry.getFirstName() + " " + entry.getSecondName();
            String name2 = entry.getFirstName()  + entry.getSecondName();
            int age = DateUtil.calAge(entry.getBirthday());
            String gender = widgetService.getGender(entry.getGender());
            String career = widgetService.getCareer(entry.getCareer());
            String address = entry.getAddress() + " " + entry.getAddress2();
            String date = DateUtil.dateToStringyyyy_MM_dd(entry.getCreateDatetime());

            entry.setName(name);
            entry.setAge(age);
            entry.setGenderStr(gender);
            entry.setCareerStr(career);
            entry.setAddress(address);
            entry.setDate(date);

        }
        logger.debug("----4----data count=" + personList.size());
        logger.debug("----5----total filtered=" + totalCountFiltered);
        logger.debug("----6----total count=" + totalCount);
        logger.debug("----7----page=" + form.getDraw());

        final int sortflg = sortflag;
        if (sortflag != 0) {
            Collections.sort(personList, new Comparator<EntryListForm>() {
                @Override
                public int compare(EntryListForm o1, EntryListForm o2) {
                    if (sortflg == 1) {
                        if (o1.getAge() > o2.getAge()) {
                            return 1;
                        } else if (o1.getAge() == o2.getAge()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    } else {
                        if (o1.getAge() > o2.getAge()) {
                            return -1;
                        } else if (o1.getAge() == o2.getAge()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                }
            });
        }

        // return json data
        DatatablesJsonObject jsonparse = new DatatablesJsonObject();
        jsonparse.setDraw(form.getDraw());
        jsonparse.setRecordsFiltered(totalCountFiltered);
        jsonparse.setRecordsTotal(totalCount);
        jsonparse.setData(dataList);
        jsonparse.setOrderStatement(form.getOrderStatement());
        logger.debug("----8----");

        return jsonparse;
    }

    @RequestMapping(value = "/admin/entry/detail/{id}/", method = RequestMethod.GET)
    public String entryDetail(Model model, @PathVariable Long id) {

        AdminEntryForm form = entryService.getAdminEntryFormById(id);
        form.setCareer(widgetService.getCareer(form.getPerson().getCareer()));
        form.setAge(DateUtil.calAge(form.getPerson().getBirthday()));
        model.addAttribute("form", form);
        return "admin/entryDetail";
    }

    @RequestMapping(value = "/admin/entry/delete/", method = RequestMethod.POST)
    public String entryDelete(@ModelAttribute AdminEntryForm form) {
        entryService.deleteEntry(form);
        return "redirect:/admin/entry/list/";
    }


    @RequestMapping(value = "/admin/entrymail/list/", method = RequestMethod.GET)
    public String entryMail(Model model,HttpServletRequest request) {

        int errorCode = 0;
        if(!request.getSession().isNew()){
            if(request.getSession().getAttribute("error") != null && request.getSession().getAttribute("error") != ""){
                errorCode = (int)request.getSession().getAttribute("error");
                request.getSession().setAttribute("error",0);
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "admin/entryMailList";
    }

    @RequestMapping(value = "/admin/entrymail/list/", method = RequestMethod.POST)
    @ResponseBody
    public DatatablesJsonObject entryMailList(@RequestBody EntryMailListForm form) {
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

        // query data
        List<EntryMail> dataList = entryService.getEntryMailAllByCondition(form);
        int totalCountFiltered = entryService.getEntryMailCountByCondition(form);
        int totalCount = entryService.getEntryMailCount();

        for (EntryMail list : dataList) {
            String expert = "";
            if (list.getDetail().length() > 30) {
                expert = list.getDetail().substring(0, 30) + "...";
            } else {
                expert = list.getDetail();
            }
        }
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

    @RequestMapping(value = "/admin/entrymail/regist/{id}/{type}/", method = RequestMethod.GET)
    public String entryMailInsert(Model model, @PathVariable Long id, @PathVariable int type) {
        EntryMailForm form = new EntryMailForm();
        form.setEntryId(id);
        if (type == Constant.NEWS_TYPE.EVENT) {
            News news = newsService.getNewsByIdAndType(id, type);
            if (news != null) {
                form.setType(Constant.ENTRY__MAIL_TYPE.NEWS_EVENT);
                form.setTypeStr(widgetService.getEntryType(Constant.ENTRY__MAIL_TYPE.NEWS_EVENT));
                form.setTitle(news.getTitle());
            }
        } else if (type == Constant.NEWS_TYPE.MOVEUP) {
            News news = newsService.getNewsByIdAndType(id, type);
            if (news != null) {
                form.setType(Constant.ENTRY__MAIL_TYPE.NEWS_MOVEUP);
                form.setTypeStr(widgetService.getEntryType(Constant.ENTRY__MAIL_TYPE.NEWS_MOVEUP));
                form.setTitle(news.getTitle());
            }
        } else if (type == Constant.ENTRY__MAIL_TYPE.EVENT) {
            Event event = eventService.getEventById(id);
            if (event != null) {
                form.setType(Constant.ENTRY__MAIL_TYPE.EVENT);
                form.setTypeStr(widgetService.getEntryType(Constant.ENTRY__MAIL_TYPE.EVENT));
                form.setTitle(event.getTitle());
            }
        }
        model.addAttribute("form", form);
        return "admin/entryMailRegist";
    }

    @RequestMapping(value = "/admin/mail/add/", method = RequestMethod.POST)
    public String newsInsert(@ModelAttribute EntryMailForm form,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            entryService.insertEntryMail(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("insert entryMail failed!, error=" + e.getMessage());
            logger.error("insert entryMail failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/entrymail/list/";
    }

    @RequestMapping(value = "/admin/entrymail/edit/{id}/", method = RequestMethod.GET)
    public String entryMaliUpdate(Model model, @PathVariable int id) {
        EntryMailForm form = new EntryMailForm();
        EntryMail entity = entryService.getEntryMailById(id);
        form.setId(entity.getId());
        form.setEntryId(entity.getEntryId());
        form.setTypeStr(widgetService.getEntryType(entity.getType()));
        form.setSubject(entity.getSubject());
        form.setTitle(entity.getTitle());
        form.setDetail(entity.getDetail());
        form.setPublishStart(DateUtil.dateToStringyyyy_MM_dd_HH_mm(entity.getPublishStart()));
        form.setPublishEnd(DateUtil.dateToStringyyyy_MM_dd_HH_mm(entity.getPublishEnd()));
        model.addAttribute("form", form);
        return "admin/entryMailEdit";
    }

    @RequestMapping(value = "/admin/entrymail/update/", method = RequestMethod.POST)
    public String newsUpdate1(@ModelAttribute EntryMailForm form
            ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            entryService.updateEntryMail(form);
            txManager.commit(txStatus);
            session.setAttribute("error",0);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            session.setAttribute("error",1);
            logger.error("update entryMail failed!, error=" + e.getMessage());
            logger.error("update entryMail failed!, error=" + e.toString());
            e.printStackTrace();
        }
        return "redirect:/admin/entrymail/list/";
    }

    @RequestMapping(value = "/admin/entrymail/delete/", method = RequestMethod.POST)
    public String entryMailDelete(@ModelAttribute EntryMailForm form) {
        entryService.deleteEntryMail(form);
        return "redirect:/admin/entrymail/list/";
    }


    @RequestMapping(value = "/admin/entry/csv/downLoad/", method = RequestMethod.POST)
    public void csvDownLoad( @ModelAttribute EntryListForm form, HttpServletResponse httpServletResponse) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "応募結果-";
        fileName += dateFormat.format(new Date());
        fileName += ".csv";

        form.setLength(-1);
        form.setOrderStatement(orderStatement);
        logger.debug("----2----order statement=" + form.getOrderStatement());

        List<Entry> dataList = entryService.getEntryAllByCondition(form);
        logger.debug("----4----data count=" + dataList.size());

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
        File file = new File(path);
        FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"Shift-JIS");
        BufferedWriter bw = new BufferedWriter(fwwe);

        bw.write("名前" + "," +"UUID" + ","+"日付" +","+"性別" +","+"生年月日"+","+"年齢" +","+"職業" +","+"メール" +","+"アドレス" +","+"タイトル"+"\r\n" );

        String name ="";
        String uuid ="";
        String date ="";
        String gender ="";
        String birthday ="";
        String age ="";
        String career ="";
        String mail ="";
        String address ="";
        String title ="";

        for (int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).getFirstName() != null && dataList.get(i).getSecondName() != null){
                 name = (dataList.get(i).getFirstName()+dataList.get(i).getSecondName()).replaceAll(",", "").replaceAll("，", "");
            }else if(dataList.get(i).getFirstName() != null && dataList.get(i).getSecondName() == null){
                 name = (dataList.get(i).getFirstName()+"").replaceAll(",", "").replaceAll("，", "");
            }else if(dataList.get(i).getFirstName() == null && dataList.get(i).getSecondName() != null){
                 name = (""+dataList.get(i).getSecondName()).replaceAll(",", "").replaceAll("，", "");
            }else{
                name = ("").replaceAll(",", "").replaceAll("，", "");
            }

            if(dataList.get(i).getUuid() !=null){
                uuid = dataList.get(i).getUuid().replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getCreateDatetime() != null){
                 date = DateUtil.dateToStringyyyy_MM_dd(dataList.get(i).getCreateDatetime()).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getGender() != null){
                 gender = widgetService.getGender(dataList.get(i).getGender()).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getBirthday() != null){
                birthday =DateUtil.dateToStringyyyy_MM_dd(dataList.get(i).getBirthday()).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getBirthday() != null){
                 age = String.valueOf(DateUtil.calAge(dataList.get(i).getBirthday())).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getCareer() != null){
                 career = widgetService.getCareer(dataList.get(i).getCareer()).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getMail() != null){
                 mail = dataList.get(i).getMail().replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getAddress() != null){
                 address = (dataList.get(i).getAddress()+" "+dataList.get(i).getAddress2()).replaceAll(",", "").replaceAll("，", "");
            }
            if(dataList.get(i).getTitle() != null){
                 title = dataList.get(i).getTitle().replaceAll(",", "").replaceAll("，", "");
            }
            bw.write(name + ","+ uuid +","+ date + ","+ gender + ","+birthday+","+ age + ","+ career + ","+ mail + ","+ address + ","+ title +"\r\n");
        }
        bw.close();
        fwwe.close();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(httpServletResponse.getOutputStream());
            byte[] buff = new byte[2048];
            while (true) {
                int bytesRead;
                if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
                    break;
                }
                out.write(buff, 0, bytesRead);
            }
            file.deleteOnExit();
        }
        catch (IOException e) {
            throw e;
        }
        finally{
            try {
                if(bis != null){
                    bis.close();
                }
                if(out != null){
                    out.flush();
                    out.close();
                }
            }
            catch (IOException e) {
                throw e;
            }
        }
    }
}
