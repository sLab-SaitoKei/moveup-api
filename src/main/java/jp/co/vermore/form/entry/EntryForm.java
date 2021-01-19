package jp.co.vermore.form.entry;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;

import jp.co.vermore.common.mvc.BaseForm;
import org.hibernate.validator.constraints.NotEmpty;
import jp.co.vermore.validation.GroupOrder1;
import jp.co.vermore.validation.GroupOrder2;


/**
 * Created by xieyoujun on 2017/11/28.
 */
public class EntryForm extends BaseForm {

    @NotEmpty(groups={GroupOrder1.class}, message="・氏名（漢字）姓を入力してください。")
    @Size(max=8,groups={GroupOrder2.class},message="・氏名（漢字）姓{max}文字以下を入力してください。")
//    @Pattern(regexp="^[一-龥]+$",groups={GroupOrder2.class},message="・氏名（漢字）姓漢字を入力してください。")
    private String firstName;

    @NotEmpty(groups={GroupOrder1.class}, message="・氏名（漢字）名を入力してください。")
    @Size(max=8,groups={GroupOrder2.class},message="・氏名（漢字）名{max}文字以下を入力してください。")
//    @Pattern(regexp="^[一-龥]+$",groups={GroupOrder2.class},message="・氏名（漢字）名漢字を入力してください。")
    private String secondName;

    @NotEmpty(groups={GroupOrder1.class}, message="・氏名（カナ）姓を入力してください。")
    @Size(max=8,groups={GroupOrder2.class},message="・氏名（カナ）姓{max}文字以下を入力してください。")
    @Pattern(regexp="^[ァ-ン]+$",groups={GroupOrder2.class},message="・氏名（カナ）全角カナを入力してください。")
    private String firstNameKana;

    @NotEmpty(groups={GroupOrder1.class}, message="・氏名（カナ）名を入力してください。")
    @Size(max=8,groups={GroupOrder2.class},message="・氏名（カナ）名{max}文字以下を入力してください。")
    @Pattern(regexp="^[ァ-ン]+$",groups={GroupOrder2.class},message="・氏名（カナ）名全角カナを入力してください。")
    private String secondNameKana;

//    @NotEmpty(groups={GroupOrder1.class}, message="必須項目を入力してください。")
    private Integer gender;

    private String genderText;

//    @Min(value = 1,groups = {GroupOrder1.class}, message = "必須項目を入力してください。")
    private Integer Birthyear;

//    @Min(value = 1,groups = {GroupOrder1.class}, message = "必須項目を入力してください。")
    private Integer Birthmonth;

//    @Min(value = 1,groups = {GroupOrder1.class}, message = "必須項目を入力してください。")
    private Integer Birthday;

    @Min(value = 1,groups = {GroupOrder1.class}, message = "・職業を入力してください。")
    private Integer occupation;

    private String occupationText;

    @NotEmpty(groups={GroupOrder1.class}, message="・郵便番号前半を入力してください。")
    @Size(min=7,max=7,groups={GroupOrder2.class},message="・郵便番号{max}文字を入力してください。")
    @Pattern(regexp="^[0-9]*$",groups={GroupOrder2.class},message="・郵便番号数字を入力してください。")
    private String zipcode1;

    @NotEmpty(groups={GroupOrder1.class}, message="・住所を入力してください。")
    private String address;

    @NotEmpty(groups={GroupOrder1.class},message="・メールを入力してください。")
    @Size(max=64,groups={GroupOrder2.class},message="・メールアドレス{max}文字までを入力してください。")
    @Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",groups={GroupOrder2.class},message="・正しいメールアドレスを入力してください。")
    private String mail;

    @NotEmpty(groups={GroupOrder1.class},message="・パースワードを入力してください。")
    @Size(min=4,max=16,groups={GroupOrder2.class},message="・パスワードは{min}文字以上{max}文字以下です。")
    @Pattern(regexp="[a-zA-Z0-9]*",groups={GroupOrder2.class},message="・パスワードは英数である必要があります。")
    private String password;

    @Size(max=16,groups={GroupOrder2.class},message="・ニックネームは{max}文字以下です。")
    @Pattern(regexp="[a-zA-Z0-9]*",groups={GroupOrder2.class},message="・ニックネームは英数である必要があります。")
    private String nickname;

    private Boolean isEntry;

    private String isEntryText;

    @AssertTrue(groups={GroupOrder1.class},message="・利用規約を承認してください。")
    private Boolean isTerms;

    private String dup;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstNameKana() {
        return firstNameKana;
    }

    public void setFirstNameKana(String firstNameKana) {
        this.firstNameKana = firstNameKana;
    }

    public String getSecondNameKana() {
        return secondNameKana;
    }

    public void setSecondNameKana(String secondNameKana) {
        this.secondNameKana = secondNameKana;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getBirthyear() {
        return Birthyear;
    }

    public void setBirthyear(Integer birthyear) {
        Birthyear = birthyear;
    }

    public Integer getBirthmonth() {
        return Birthmonth;
    }

    public void setBirthmonth(Integer birthmonth) {
        Birthmonth = birthmonth;
    }

    public Integer getBirthday() {
        return Birthday;
    }

    public void setBirthday(Integer birthday) {
        Birthday = birthday;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public String getZipcode1() {
        return zipcode1;
    }

    public void setZipcode1(String zipcode1) {
        this.zipcode1 = zipcode1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGenderText() {
        return genderText;
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }

    public String getOccupationText() {
        return occupationText;
    }

    public void setOccupationText(String occupationText) {
        this.occupationText = occupationText;
    }

    public Boolean getIsEntry() {
        return isEntry;
    }

    public void setIsEntry(Boolean entry) {
        isEntry = entry;
    }

    public Boolean getIsTerms() {
        return isTerms;
    }

    public void setIsTerms(Boolean terms) {
        isTerms = terms;
    }

    public Boolean getEntry() {
        return isEntry;
    }

    public void setEntry(Boolean entry) {
        isEntry = entry;
    }

    public String getIsEntryText() {
        return isEntryText;
    }

    public void setIsEntryText(String isEntryText) {
        this.isEntryText = isEntryText;
    }

    public Boolean getTerms() {
        return isTerms;
    }

    public void setTerms(Boolean terms) {
        isTerms = terms;
    }

    public String getDup() {
        return dup;
    }

    public void setDup(String dup) {
        this.dup = dup;
    }
}
