package jp.co.vermore.controller;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.entity.Entry;
import jp.co.vermore.entity.EntryMail;
import jp.co.vermore.entity.UserForeign;
import jp.co.vermore.service.AWSService;
import jp.co.vermore.service.AuthService;
import jp.co.vermore.service.EntryService;
import jp.co.vermore.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieyoujun on 2018/02/03.
 */
@Controller
public class EntryController extends BaseController {

    @Autowired
    private EntryService entryService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PersonService personService;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private AWSService awsService;


    @Value(value = "${hosturl}")
    private String hosturl;

//    @RequestMapping(value = "/withdraw/", method = RequestMethod.GET)
//    public String applyWithdraw(@ModelAttribute WithdrawForm form, Model model) {
//
//        logger.info("--------applyWithdraw" );
//
//        model.addAttribute("withdrawForm", form);
//
//        return "entry/withdrawMail";
//    }
//
//    @RequestMapping(value = "/withdraw/", method = RequestMethod.POST, params = "confirm")
//    public String applyWithdrawConfirm(@Validated(GroupOrder.class) @ModelAttribute WithdrawForm form, BindingResult result, Model model) {
//
//        logger.info("--------applyWithdrawConfirm" );
//
//        if(result.hasErrors()) {
//            model.addAttribute("mailForm", form);
//            return "entry/withdrawMail";
//        }
//
//        UserForeign userForeign = entryService.getUserForeignByMail(form.getMail());
//
//        User user;
//
//        if (userForeign != null) {
//            user = entryService.getUserById(userForeign.getUserId());
//        } else user = new User();
//
//        if ( userForeign == null || (user.getStatus() != null && user.getStatus() == Constant.USER_STATUS.WITHDRAWAL)) {
//            form.setErrorMessage("・該当メールアドレス登録されていません。");
//            model.addAttribute("mailForm", form);
//            return "entry/withdrawMail";
//        }
//
//        model.addAttribute("withdrawForm", form);
//
//        return "entry/withdrawConfirm";
//    }
//
//    @RequestMapping(value = "/withdraw/", method = RequestMethod.POST, params = "no")
//    public String applyWithdrawNotOK(@ModelAttribute WithdrawForm form, Model model) {
//
//        form.setMessage("今後ともＭＯＶＥＵＰＷＥＳＴをよろしくお願いします。");
//
//        model.addAttribute("withdrawForm", form);
//
//        return "entry/withdrawFinish";
//
//    }
//
//    @RequestMapping(value = "/withdraw/", method = RequestMethod.POST, params = "yes")
//    public String applyWithdrawOK(@ModelAttribute WithdrawForm form, Model model) {
//
//        logger.info("--------applyWithdrawOK" );
//
//        UserForeign userForeignDto = entryService.getUserForeignByMail(form.getMail());
//
//        User user;
//
//        if (userForeignDto != null) {
//             user = entryService.getUserById(userForeignDto.getUserId());
//        } else user = new User();
//
//        if ( userForeignDto == null || (user.getStatus() != null && user.getStatus() == Constant.USER_STATUS.WITHDRAWAL)) {
//            form.setErrorMessage("・該当メールアドレス登録されていません。");
//            model.addAttribute("mailForm", form);
//            return "entry/withdrawMail";
//        } else {
//            form.setMessage("退会完了しました。");
//        }
//
//        entryService.updateUserWithdraw(userForeignDto.getUserId());
//
//        model.addAttribute("withdrawForm", form);
//
//        return "entry/withdrawFinish";
//    }
//
//    @RequestMapping(value = "/mail/", method = RequestMethod.GET)
//    public String applyMail(@ModelAttribute MailForm form, Model model) {
//
//        logger.info("--------applyMail" );
//
//        model.addAttribute("mailForm", form);
//
//        return "entry/entryMail";
//    }
//
//    @RequestMapping(value = "/mail/", method = RequestMethod.POST)
//    public String applyMailOK(@Validated(GroupOrder.class) @ModelAttribute MailForm form, BindingResult result, Model model) {
//
//        if(result.hasErrors()) {
//            model.addAttribute("mailForm", form);
//            return "entry/entryMail";
//        }
//
//        UserForeign userForeign = entryService.getUserForeignByMail(form.getMail());
//
//        User user;
//
//        if (userForeign != null) {
//            user = entryService.getUserById(userForeign.getUserId());
//        } else user = new User();
//
//        if (userForeign != null && (user != null && user.getStatus() == Constant.USER_STATUS.EFFECT)) {
//            form.setDup("・該当メールアドレスすでに登録済みです。");
//        } else {
//            form.setDup("");
//        }
//
//        if (!form.getDup().isEmpty()) {
//            model.addAttribute("mailForm", form);
//            return "entry/entryMail";
//        }
//
//        logger.info("--------applyMailOK" );
//
//        EntryForm entryForm = new EntryForm();
//        entryForm.setMail(form.getMail());
//
//        model.addAttribute("occupationList", createOccupationList());
//        model.addAttribute("birthyearList", createBirthyearList());
//        model.addAttribute("birthmonthList", createBirthmonthList());
//        model.addAttribute("birthdayList", createBirthdayList());
//        model.addAttribute("entryForm", entryForm);
//        model.addAttribute("mailForm", form);
//
//        return "entry/entryCreate";
//    }
//
//    @RequestMapping(value = "/entry/", method = RequestMethod.GET)
//    public String applyCreate(@ModelAttribute EntryForm form, Model model) {
//
//        logger.info("--------applyCreate" );
//
//        model.addAttribute("occupationList", createOccupationList());
//        model.addAttribute("birthyearList", createBirthyearList());
//        model.addAttribute("birthmonthList", createBirthmonthList());
//        model.addAttribute("birthdayList", createBirthdayList());
//        model.addAttribute("entryForm", form);
//        return "entry/entryCreate";
//    }
//
//    @RequestMapping(value = "/entry/", method = RequestMethod.POST, params = "confirm")
//    public String applyConfirm(@Validated(GroupOrder.class) @ModelAttribute EntryForm form, BindingResult result, Model model) {
//
//        if(result.hasErrors()) {
//            model.addAttribute("occupationList", createOccupationList());
//            model.addAttribute("birthyearList", createBirthyearList());
//            model.addAttribute("birthmonthList", createBirthmonthList());
//            model.addAttribute("birthdayList", createBirthdayList());
//            model.addAttribute("entryForm", form);
//            return "entry/entryCreate";
//        }
//
//        UserForeign userForeignDto = entryService.getUserForeignByMail(form.getMail());
//
//        User userDto;
//
//        if (userForeignDto != null) {
//            userDto = entryService.getUserById(userForeignDto.getUserId());
//        } else userDto = new User();
//
//        if (userForeignDto != null && (userDto != null && userDto.getStatus() == Constant.USER_STATUS.EFFECT)) {
//            form.setDup("・該当メールアドレスすでに登録済みです。");
//        } else {
//            form.setDup("");
//        }
//
//
//        model.addAttribute("birthyearList", createBirthyearList());
//        model.addAttribute("birthmonthList", createBirthmonthList());
//        model.addAttribute("birthdayList", createBirthdayList());
//
//        if (!form.getDup().isEmpty()) {
//            model.addAttribute("occupationList", createOccupationList());
//            model.addAttribute("entryForm", form);
//            return "entry/entryCreate";
//        }
//
//        List<selectModel> occupationList = createOccupationList();
//        if (form.getGender() == 1) {form.setGenderText("男性");} else {form.setGenderText("女性");}
//        if (form.getIsEntry() != null && form.getIsEntry()) {form.setIsEntryText("JAPAN MOVE UP WEST the 5th anniversary special event同時応募");} else {form.setIsEntryText("");}
//        for (int cnt = 0; cnt < occupationList.size(); cnt++) {
//            if (Integer.parseInt(occupationList.get(cnt).id) == form.getOccupation()) form.setOccupationText(occupationList.get(cnt).name);
//        }
//
//        model.addAttribute("occupationList", occupationList);
//        model.addAttribute("entryForm", form);
//        return "entry/entryConfirm";
//    }
//
//    @Autowired
//    PlatformTransactionManager txManager;
//
//    @RequestMapping(value = "/entry/", method = RequestMethod.POST)
//    public String applyInsert(@ModelAttribute EntryForm form, Model model) {
//
//
//        // トランザクション管理の開始
//        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
//        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
//
//        try {
//            long userId = entryService.insertUser(form);
//            entryService.insertUserForeign(form, userId);
//            entryService.insertPerson(form, userId);
//            if (form.getIsEntry() != null && form.getIsEntry()) entryService.insertEntry(form, userId);
//            txManager.commit(txStatus);
//            logger.info("New user is registed. User=" + userId);
//        } catch (Exception e) {
//            txManager.rollback(txStatus);
//        }
//
//        return "entry/entryFinish";
//    }
//
//    @RequestMapping(value = "/privacy/", method = RequestMethod.GET)
//    public String privacy(Locale locale, Model model) {
//        return "entry/entryPrivacy";
//    }
//
//    @RequestMapping(value = "/terms/", method = RequestMethod.GET)
//    public String terms(Locale locale, Model model) {
//        return "entry/entryTerms";
//    }
//
//    private class selectModel {
//        private String id;
//        private String name;
//
//        public selectModel(String id, String name) {
//            this.id = id;
//            this.name = name;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//
//
//    private List<selectModel> createOccupationList() {
//        List<selectModel> occupationList = new ArrayList<selectModel>();
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.CAREER), "職業を選択"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.JUNIOR_STUDENT), "中学生"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.SENIOR_STUDENT), "高校生"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.COLLEGE_STUDENT), "専門学生"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.UNIVERSITY_STUDENT), "大学生"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.CIVIL_SERVANT), "公務員"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.SELF_EMPLOYED), "自営業"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.EMPLOYEE), "会社役員"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.TEMPORARY), "会社員"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.DISPATCHED), "派遣社員"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.CONTRACT), "契約社員"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.HOUSE_WIFE), "専業主婦"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.HOUSE_HUSBAND), "専業主夫"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.ARBEIT), "パート・アルバイト"));
//        occupationList.add(new selectModel(String.valueOf(Constant.CAREER.OTHER), "その他"));
//        return occupationList;
//    }
//
//    private List<selectModel> createBirthyearList() {
//        List<selectModel> birthyearList = new ArrayList<selectModel>();
//        for (int countyear = 2018; countyear >= 1920; countyear--){
//            birthyearList.add(new selectModel(String.valueOf(countyear), String.valueOf(countyear)));
//        }
//        return birthyearList;
//    }
//
//    private List<selectModel> createBirthmonthList() {
//        List<selectModel> birthmonthList = new ArrayList<selectModel>();
//        for (int countmonth = 1; countmonth <= 12; countmonth++){
//            birthmonthList.add(new selectModel(String.valueOf(countmonth), String.valueOf(countmonth)));
//        }
//        return birthmonthList;
//    }
//
//    private List<selectModel> createBirthdayList() {
//        List<selectModel> birthdayList = new ArrayList<selectModel>();
//        for (int countday = 1; countday <= 31; countday++){
//            birthdayList.add(new selectModel(String.valueOf(countday), String.valueOf(countday)));
//        }
//        return birthdayList;
//    }

    @RequestMapping(value = "/api/insert/entry/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject insertEntry(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest hsr) throws APIException, IOException {

        String entryId = formData.getFirst("entryId");
        String type = formData.getFirst("type");
        String title = formData.getFirst("title");
        String contents = formData.getFirst("contents");

        Long userId = authService.getUserId(hsr);
        Entry entryCheck = entryService.getEntryByUserId(userId,Long.valueOf(entryId),Integer.valueOf(type));
        if(entryCheck != null ){
            logger.warn("Duplication entry, UserId=" + userId + ", EntryId=" + entryCheck.getId());
            return jsonObject.withStatus(JsonStatus.USER_ID_INVALID);
        }else {
            // トランザクション管理の開始
            DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
            txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = txManager.getTransaction(txDefinition);

            String mail = "";
            try {
                mail = personService.getPersonById(userId).getMail();

                Entry entry = new Entry();
                entry.setUserId(userId);
                entry.setContent("");
                entry.setTitle(title);
                if(mail.equals("") || mail==null){
                    UserForeign uf = authService.getUserForeignByUserIdAndForeignType(userId,3);
                    if(uf != null){
                        mail = uf.getForeignId();
                    }
                }
                entry.setMail(mail);
                entry.setEntryType(Integer.valueOf(type));
                entry.setEntryId(Integer.valueOf(entryId));
                entryService.insertentry(entry);
                txManager.commit(txStatus);
            } catch (Exception e) {
                txManager.rollback(txStatus);
                logger.error("Insert entry failed!, error=" + e.getMessage());
                logger.error("Insert entry failed!, error=" + e.toString());
                e.printStackTrace();
                throw new APIException(JsonStatus.DATA_SAVE_FAILED);
            }

            logger.info("Send confirm mail=" + mail);
            EntryMail entryMail = entryService.getEntryMailByEntryIdAndType(Integer.valueOf(entryId),Integer.valueOf(type));
            if(entryMail!=null){
                String subject = entryMail.getSubject();
                String content = entryMail.getDetail();
                if(mail != null && mail !=""){
                    awsService.sendEntryMail(mail,subject,content);
                }
                logger.info("Send confirm mail=" + mail);
                logger.info("Regist mail send to mail=" + mail);
            }

            return jsonObject.withStatus(JsonStatus.SUCCESS);
        }
    }

    @RequestMapping(value = "/api/entry/{entryId}/{type}/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getFavorite(@PathVariable long entryId, @PathVariable int type , HttpServletRequest hsr) throws APIException {

        long userId = authService.getUserId(hsr);
        Entry entryCheck = entryService.getEntryByUserId(userId,entryId,type);
        Map<String, Object> m = new HashMap<>();
        if(entryCheck != null ){
            logger.warn("entry  existed , UserId=" + userId + ", entryId=" + entryCheck.getId());
            m.put("status", 1);
        }else {
            m.put("status", 0);
        }
        jsonObject.setResultList(m);
        return jsonObject;
    }
}



