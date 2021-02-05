
package jp.co.vermore.service;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jp.co.vermore.common.mvc.BaseService;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.form.GoodsPurchaseMailForm;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Position;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by jiahongwei on 2018/03/28.
 */
@Service
public class AWSService extends BaseService {

    @Value(value = "${aws.file.name.length}")
    private int fileNameLength;

    @Value(value = "${aws.access.key.id}")
    private String AccessKeyId;

    @Value(value = "${aws.secret.access.key}")
    private String secretAccessKey;

    @Value(value = "${aws.region}")
    private String region;

    @Value(value = "${aws.bucket}")
    private String bucket;

    @Value(value = "${hosturl}")
    private String hosturl;

    public String postFile(File file) throws Exception {
        String fileName = StringUtil.getAWSFileName(fileNameLength);
        AWSCredentials credentials = new BasicAWSCredentials(AccessKeyId, secretAccessKey);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        s3.putObject(new PutObjectRequest(bucket, fileName, file));
        return StringUtil.getAWSUri(region, bucket, fileName);
    }

    public String postFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtil.getAWSFileName(fileNameLength);
        AWSCredentials credentials = new BasicAWSCredentials(AccessKeyId, secretAccessKey);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        s3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata));
        return StringUtil.getAWSUri(region, bucket, fileName);
    }

    public String postMiddleThumbnailFile(MultipartFile multipartFile) throws IOException {
        return postThumbnailFile(multipartFile, 0.5f);
    }

    public String postSmallThumbnailFile(MultipartFile multipartFile) throws IOException {
        return postThumbnailFile(multipartFile, 0.25f);
    }

    private String postThumbnailFile(MultipartFile multipartFile, float scale) throws IOException {
        String fileName = StringUtil.getAWSFileName(fileNameLength);

        // scale image to temp file
        File tempFile = File.createTempFile(fileName, ".jpg");
        Thumbnails.of(multipartFile.getInputStream()).scale(scale).outputQuality(0.95).toFile(tempFile);

        AWSCredentials credentials = new BasicAWSCredentials(AccessKeyId, secretAccessKey);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(tempFile.length());

        s3.putObject(new PutObjectRequest(bucket, fileName, new FileInputStream(tempFile), metadata));

        tempFile.deleteOnExit();
        return StringUtil.getAWSUri(region, bucket, fileName);
    }

    public String postWatermarkFile(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtil.getAWSFileName(fileNameLength);

        // scale image to temp file
        File tempFile = File.createTempFile(fileName, ".jpg");
        String path = request.getSession().getServletContext().getRealPath("/") + "static/img/logo-mark.png";
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        String filePath = ((DiskFileItem) ((CommonsMultipartFile) multipartFile).getFileItem()).getStoreLocation().getPath();
        Thumbnails.Builder builder = Thumbnails.of(filePath).scale(1.0);
        builder.watermark(new Position() {
            @Override
            public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft, int insetRight, int insetTop, int insetBottom) {
                return new Point(width - 70, enclosingHeight - 68);
            }
        }, bufferedImage, 0.9f);
        builder.outputQuality(0.95).toFile(tempFile);

        AWSCredentials credentials = new BasicAWSCredentials(AccessKeyId, secretAccessKey);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(tempFile.length());

        s3.putObject(new PutObjectRequest(bucket, fileName, new FileInputStream(tempFile), metadata));

        tempFile.deleteOnExit();
        return StringUtil.getAWSUri(region, bucket, fileName);
    }


    // Replace sender@example.com with your "From" address.
    // This address must be verified with Amazon SES.
    static final String FROM = "JAPAN MOVE UP WEST<support@japanmoveupwest.com>";

    static final String ENTRY = "JAPAN MOVE UP WEST<info@japanmoveupwest.com>";

    // Replace recipient@example.com with a "To" address. If your account
    // is still in the sandbox, this address must be verified.
    static final String TO = "";

    // The configuration set to use for this email. If you do not want to use a
    // configuration set, comment the following variable and the
    // .withConfigurationSetName(CONFIGSET); argument below.
    static final String CONFIGSET = "ConfigSet";

    // The subject line for the email.
    static final String REGIST_SUBJECT = "JAPAN MOVE UP WEST本会員登録のご案内";

    static final String ENTRY_SUBJECT = "【ご応募完了のお知らせ】〈プレミアムMOVE UP11〉今市隆二さん直筆サイン入り色紙を抽選で３名様にプレゼント！";

    // The subject line for the email.
    static final String FORGET_SUBJECT = "JAPAN MOVE UP WESTパスワード再設定のご案内";

    static final String CHANGE_SUBJECT = "JAPAN MOVE UP WESTメールアドレス再設定";

    // The subject line for the email.
    static final String RETREAT_CLUB = "JAPAN MOVE UP WEST退会処理が完了しました";

    static final String CARRY_SUBJECT = "JAPAN MOVE UP WEST掲載依頼受付完了のご案内";

    static final String INQUIRE_SUBJECT = "JAPAN MOVE UP WESTお問い合わせ受付完了";

    static final String GOODS_SUBJECT = "JAPAN MOVE UP WESTのご注文ありがとうございます";

    static final String GOODS_PAYMENT_CVS_SUBJECT = "JAPAN MOVE UP WESTのご注文商品のご入金を確認いたしました";

    static final String DELIVER_GOODS_SUBJECT = "JAPAN MOVE UP WESTのご注文商品の出荷が完了しました";

    private String[] CARRY_HTMLBODY = new String[]{"貴店名:"};

    // The HTML body for the email.
    private String[] REGIST_HTMLBODY = new String[]{
            //<h2>JAPAN MOVE UP WEST本会員登録のご案内</h2>
            "<div style='padding:20px;'>" +
                    "<p>この度は、JAPAN MOVE UP WEST会員にご登録をいただきまして、誠にありがとうございます。<br>" +
                    "お客様の会員登録はまだ完了しておりません。<br>" +
                    "以下のURLへアクセスし、本登録を完了させてください。<br>" +
                    "URL：<a href='", "'>", "</a></p>" +
            "<p>※ 上記のURLは、24時間を超過しますと、セキュリティ保持のため有効期限切れとなります。その場合は再度、最初からお手続きをお願い致します。</p>" +
            "<p>※ 本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。</p>" +
            "<p>※ 当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきますよう、よろしくお願い致します。</p>" +
            "<div>"
    };

    // The HTML body for the email.<h2>JAPAN MOVE UP WESTパスワード再設定</h2>
    private String[] FORGET_HTMLBODY = new String[]{
            "<div style='padding:20px;'>" +
                    "<p>この度は、JAPAN MOVE UP WEST会員にご登録いただき、誠にありがとうございます。<br>" +
                    "このメールは、JAPAN MOVE UP WEST会員の《パスワード再設定手続》を行われたお客様に送信させていただいております。<br>" +
                    "以下のページアドレス（URL）へアクセスし、画面の指示に従ってお手続きください。<br>" +
                    "URL：<a href='", "'>", "</a></p>" +
            "<p>※ セキュリティ保持のため有効期限が設けられております。申請後一定時間を経過すると期限切れとなりますのでご注意ください。</p>" +
            "<p>※ 本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。</p>" +
            "<p>※ 当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきますよう、よろしくお願い致します。</p>" +
            "<div>"
    };

    // The HTML body for the email.
    private String[] CHANGE_HTMLBODY = new String[]{
            "<div style='padding:20px;'>" +
                    "<h2>JAPAN MOVE UP WESTメールアドレス再設定</h2><p>JAPAN MOVE UP WESTをご利用いただき、誠にありがとうございます。<br>" +
                    "このメールは、JAPAN MOVE UP WEST会員の《メールアドレス再設定手続》を行われたお客様に送信させていただいております。<br>" +
                    "以下のページアドレス（URL）へアクセスし、画面の指示に従ってお手続きください。<br>" +
                    "URL：<a href='", "'>", "</a></p>" +
            "<p>※ セキュリティ保持のため有効期限が設けられております。申請後一定時間を経過すると期限切れとなりますのでご注意ください。</p>" +
            "<p>※ 本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。</p>" +
            "<p>※ 当メールに心当たりの無い場合は、誠に恐れ入りますが破棄して頂けますよう、よろしくお願い致します。</p>" +
            "<div>"
    };


    // The HTML body for the email.
    private String[] ENTRY_HTMLBODY = new String[]{
            "<div style='padding:20px;'>" +
//            "<h2>《JAPAN MOVE UP WESTより》</h2><br>" +
                    "本メールは「〈プレミアムMOVE UP11〉今市隆二さん直筆サイン入り色紙を抽選で３名様にプレゼント！」にご応募いただいたお客様に自動で配信しております。<br>" +
                    "=======================================<br><br>" +
                    "この度は本キャンペーンへご応募いただき、誠にありがとうございます。本メールにて、ご応募が完了しましたことをお知らせいたします。<br><br>" +
                    "<p>当選発表につきましては、応募期間終了後に厳正な抽選を行い、ご当選されたお客様へ賞品を発送いたします。</p>" +
                    "<p>ご当選につきましては、賞品の発送をもって代えさせていただきます。なお、抽選およびご当選内容に関するお問い合わせは受け付けておりませんので、ご了承ください</p><br>" +
                    "<p>今後もJAPAN MOVE UP WESTをよろしくお願いいたします。</p><br>" +
                    "<p>JAPAN MOVE UP WEST</p>" +
                    "<div>"
    };

    // The HTML body for the email.
    private String[] RETREAT_HTMLBODY = new String[]{
            "<div style='padding:20px;'>" +
                    "<p>JAPAN MOVE UP WEST会員退会処理が完了いたしました。</p><br>" +
                    "<p>JAPAN MOVE UP WESTは、今後もより良いサービスを提供してまいります。<br>" +
                    "またのご利用、心よりお待ちしております。<br><br>" +
                    "=============================================<br>" +
                    "<p>※本メールはご登録のメールアドレス宛に自動的に送信されています。ご返\n" +
                    "信をいただいてもお答えできないことを予めご了承ください。</p>" +
                    "<p>※当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきま\n" +
                    "すよう、よろしくお願い致します。</p>" +
                    "<div>"
    };

    // The email body for recipients with non-HTML email clients.
    static final String TEXTBODY = ""; // TODO:fix text mailbody

    // The HTML body for the email.<h2>JAPAN MOVE UP WESTお問い合わせ受付完了</h2>
    private String[] INQUIRE_HTMLBODY = new String[]{
            "<div style='padding:20px;'>" +
                    "<p>この度は、JAPAN MOVE UP WESTへお問い合わせをいただき、ありがとうございます。<br>" +
                    "以下内容のお問い合わせの受付を完了しました。<br><br>" +
                    "以下のページアドレス（URL）へアクセスし、画面の指示に従ってお手続きください。<br>" +
                    "=======================================<br><br>" +
                    "<p>※ セキュリティ保持のため有効期限が設けられております。申請後一定時間を経過すると期限切れとなりますのでご注意ください。</p>" +
                    "<p>※ 本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。</p>" +
                    "<p>※ 当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきますよう、よろしくお願い致します。</p>" +
                    "<div>"
    };

    public void sendInquireMail(String mail, String content) throws IOException {

        String[] INQUIRE_HTMLBODY = new String[]{
                //<h2>JAPAN MOVE UP WESTお問い合わせ受付完了</h2>
                "<div style='padding:20px;'>" +
                        "<p>この度は、JAPAN MOVE UP WESTへお問い合わせをいただき、ありがとうございます。<br>" +
                        "以下内容のお問い合わせの受付を完了しました。<br><br>" +
                        content + "<br><br>" +
                        "=======================================<br><br>" +
                        "<p>※本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。</p>" +
                        "<p>※営業時間外に送信されたお問い合わせは、翌営業日以降の対応になりますのでご了承下さい。</p>" +
                        "<p>（営業時間　午前10時から～午後5時半/土・日・祝日・年末年始・夏季休暇を除く）</p>" +
                        "<p>※返信は3営業日ほど頂いておりますが、内容により回答に日数を要する場合があります。</p>" +
                        "<p>※お問い合わせ後、3営業日たっても返信がない場合はメールアドレスと受信拒否設定をご確認の上、再度お問い合わせ下さい。</p>" +
                        "<p>※原則として入力いただいたメールアドレスへ返信させていただきます。</p>" +
                        "<p>※当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきますよう、よろしくお願い致します。</p>" +
                        "<div>"
        };
        sendMail(mail, "", INQUIRE_SUBJECT, INQUIRE_HTMLBODY);
    }

    public void sendCarryMail(MultiValueMap<String, String> formData) throws IOException {
        String shopName = formData.getFirst("shopName");
        String companyName = formData.getFirst("companyName");
        String shopType = formData.getFirst("shopType");
        String zipcode = formData.getFirst("zipcode");
        String address = formData.getFirst("address");
        String tel = formData.getFirst("tel");
        String mail = formData.getFirst("mail");
        String url = formData.getFirst("url");
        String content = formData.getFirst("content");

        String[] CARRY_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
//                "<h2>《掲載依頼受付メール》</h2><br>"+
                        "この度は、JAPAN MOVE UP WESTへお問い合わせをいただき、ありがとうございます。<br>" +
                        "以下内容の掲載依頼受付を完了しました。<br><br>" +
                        "貴店名:" + shopName + "<br>" +
                        "ご担当者名:" + companyName + "<br>" +
                        "貴店の業種:" + shopType + "<br>" +
                        "貴店郵便番号:" + zipcode + "<br>" +
                        "貴店ご住所:" + address + "<br>" +
                        "連絡先電話番号:" + tel + "<br>" +
                        "メールアドレス:" + mail + "<br>" +
                        "貴店のURL:" + url + "<br>" +
                        "お問い合わせ内容:" + content + "<br><br>" +
                        "===========================================================<br><br>" +
                        "※本メールはご登録のメールアドレス宛に自動的に送信されています。ご返信をいただいてもお答えできないことを予めご了承ください。<br>" +
                        "※営業時間外に送信されたお問い合わせは、翌営業日以降の対応になりますのでご了承下さい。<br>" +
                        "（営業時間 午前10時から～午後5時30分/土・日・祝日・年末年始・夏季休暇・GWを除く）<br>" +
                        "※返信は3営業日ほど頂いておりますが、内容により回答に日数を要する場合があります。<br>" +
                        "※お問い合わせ後、3営業日たっても返信がない場合はメールアドレスと受信拒否設定をご確認の上、再度お問い合わせ下さい。<br>" +
                        "※原則として入力いただいたメールアドレスへ返信させていただきます。<br>" +
                        "※当メールに心当たりの無い場合は、誠に恐れ入りますが、破棄していただきますよう、よろしくお願い致します。<br>" +
                        "<div>"
        };
        sendMail(mail, "", CARRY_SUBJECT, CARRY_HTMLBODY);

        String[] ADMIN_CARRY_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
//                "<h2>《掲載依頼受付メール》</h2><br>"+
                        "以下内容の掲載依頼受付を完了しました。<br><br>" +
                        "店名:" + shopName + "<br>" +
                        "ご担当者名:" + companyName + "<br>" +
                        "業種:" + shopType + "<br>" +
                        "郵便番号:" + zipcode + "<br>" +
                        "住所:" + address + "<br>" +
                        "電話番号:" + tel + "<br>" +
                        "メールアドレス:" + mail + "<br>" +
                        "URL:" + url + "<br>" +
                        "お問い合わせ内容:" + content + "<br><br>" +
                        "<div>"
        };
        sendMail("request@japanmoveupwest.com", "", CARRY_SUBJECT, ADMIN_CARRY_HTMLBODY);
    }

    public void sendGOODSMail(String mailTo, String serialNumber) throws IOException {


        // The HTML body for the email.
        String[] GOODS_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
                        "<h2>JAPAN MOVE UP WESTの商品を購入頂きありがとございます。</h2><br>" +
                        "<p>取引番号 : " + serialNumber + "</p><br>" +
                        "<p>ただいま、ご注文の確認メールをお送りさせていただきました。</p>" +
                        "<p>万一、ご確認メールが届かない場合は、トラブルの可能性もありますので大変お手数ではございますが。</p>" +
                        "<p>もう一度お問い合わせいただくか、お電話にてお問い合わせくださいませ。</p>" +
                        " <p>万一、ご確認メールが届かない場合は、トラブルの可能性もありますので大変お手数ではございますがもう一度お問い合わせいただくか、" +
                        "お電話にてお問い合わせくださいませ</p>" +
                        "  <p>今後ともご愛顧宜りますようよろしくお願い申し上げます。</p>" +
                        "<div>"
        };
        sendMail(mailTo, "", GOODS_SUBJECT, GOODS_HTMLBODY);
    }

    public void sendGoodsPurchaseMail(String mailTo, GoodsPurchaseMailForm goods) throws IOException {
        String goodsinfo;
        if (goods.getGoodsInfoForms().size() == 1) {
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                        "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                        "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                        "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                        "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                        "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
        }else{
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                        "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                        "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                        "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                        "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                        "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
            for (int i = 1; i < goods.getGoodsInfoForms().size(); i++) {
                goodsinfo += "<p>■注文数：" + goods.getGoodsInfoForms().get(i).getQuantity() + " 点</p>" +
                             "<p>■商品名：" + goods.getGoodsInfoForms().get(i).getGoodsName() + "</p>" +
                            "<p>・カラー：" + goods.getGoodsInfoForms().get(i).getColor() + "</p>"+
                            "<p>・サイズ："+ goods.getGoodsInfoForms().get(i).getSize() +"</p>"+
                             "<p>■小計：" + goods.getGoodsInfoForms().get(i).getPrice() + "円</p>" +
                             "<p>■消費税：" + goods.getGoodsInfoForms().get(i).getTax() + "円</p><br>"  ;
            }
        }

        // The HTML body for the email.
        String[] GOODS_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
                        "<h2>JAPAN MOVE UP WESTのご注文ありがとうございます。</h2><br>" +
                        "<p>" + goods.getName() + "様</p><br>" +
                        "<p>この度は、MOVE UP GOODSをご利用いただき、誠にありがとうございます。</p>" +
                        "<p>下記内容にて、ご注文を承りました。</p><br>" +
                        "<p>ご注文番号：" + goods.getSerialNumber() + "</p>" +
                        "<p>ご注文日時：" + goods.getDate() + "</p><br>" +
                        "<p>商品出荷時に、発送完了メールをお送りさせていただきます。</p>" +
                        "<p>商品の出荷はご注文日より、通常7営業日(土日祝除く)となります。</p>" +
                        "<p>※ご注文が集中した場合や連休の前後は、お届けが遅れる場合もございますので予めご了承ください。</p><br>" +
                        "<hr><br>" +

                        "<p>【ご注文内容】</p><br>" +
                        goodsinfo +
                        "<hr><br>" +
                        "<p>送料：" + goods.getDeliveryExpense() + "円</p>" +
                        "<p>決算方法：" + goods.getPurchaseType() + "払い</p>" +
                        "<p>合計金額：" + goods.getTotalPrice() + "円</p><br>" +
                        "<hr><br>" +
                        "<p>【お客様ご住所】</p><br>" +
                        "<p>" + goods.getName() + "様</p>" +
                        "<p>■ご住所</p>" +
                        "<p>〒 "+ goods.getZipcode() +"</p>" +
                        "<p>"+ goods.getAddress() +"</p>" +
                        "<p>■電話番号</p>" +
                        "<p>"+ goods.getTel() +"</p>" +
                        "<p>■メールアドレス</p>" +
                        "<p>"+ goods.getMail() +"</p><br>" +
                        "<hr><br>" +
                        "<p>【配送先ご住所】</p><br>" +
                        "<p>" + goods.getName2() + "様</p>" +
                        "<p>■ご住所</p>" +
                        "<p>〒 "+ goods.getZipcode2() +"</p>" +
                        "<p>"+ goods.getAddress2() +"</p>" +
                        "<p>■電話番号</p>" +
                        "<p>"+ goods.getTel2() +"</p>" +
                        "<hr><br>" +
                        "<p>【お支払い方法】</p><br>" +
                        "<p>" + goods.getPurchaseType() + "払い</p>" +
                        "<p>※</p><br>" +
                        "<hr><br>" +
                        "<p>本メールにお心当たりがない場合は、お手数ですが下記フォームよりご連絡をお願いいたします。</p>" +
                        "URL：<a href='" + goods.getUrl1() + "'>" + goods.getUrl1() + "</a></p>" +
                        "<p>本メールは、送信専用となっております。返信いただいてもご連絡できませんのでご了承ください。ご不明な点や質問等がございましたら、下記「お客様お問い合わせ窓口」よりお問い合わせください。</p><br>" +
                        "<hr><br>" +
                        "<p>【お客様お問い合わせ窓口】</p>" +
                        "<p>086-250-8089 (平日13:00〜18:00)</p>" +
                        "<p>※土日祝、年末年始、夏季休暇、GWを除く</p><br>" +
                        "<p>■購入履歴の確認方法</p>" +
                        "<p>JAPAN MOVE UP WEST WEBよりログインし、マイページの購入履歴よりご確認ください。</p>" +
                        "URL：<a href='" + goods.getUrl2() + "'>" + goods.getUrl2() + "</a></p><br>" +
                        "<hr><br>" +
                        "<p>JAPAN MOVE UP WEST</p>" +
                        "<div>"
        };
        sendMail(mailTo, "", GOODS_SUBJECT, GOODS_HTMLBODY);
    }

    public void sendGoodsPurchaseCvsMail(String mailTo, GoodsPurchaseMailForm goods) throws IOException {
        String goodsinfo;
        if (goods.getGoodsInfoForms().size() == 1) {
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                    "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                    "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                    "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                    "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                    "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
        }else{
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                    "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                    "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                    "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                    "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                    "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
            for (int i = 1; i < goods.getGoodsInfoForms().size(); i++) {
                goodsinfo += "<p>■注文数：" + goods.getGoodsInfoForms().get(i).getQuantity() + " 点</p>" +
                        "<p>■商品名：" + goods.getGoodsInfoForms().get(i).getGoodsName() + "</p>" +
                        "<p>・カラー：" + goods.getGoodsInfoForms().get(i).getColor() + "</p>"+
                        "<p>・サイズ："+ goods.getGoodsInfoForms().get(i).getSize() +"</p>"+
                        "<p>■小計：" + goods.getGoodsInfoForms().get(i).getPrice() + "円</p>" +
                        "<p>■消費税：" + goods.getGoodsInfoForms().get(i).getTax() + "円</p><br>"  ;
            }
        }

        // The HTML body for the email.
        String[] GOODS_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
                        "<h2>JAPAN MOVE UP WESTのご注文ありがとうございます。</h2><br>" +
                        "<p>" + goods.getName() + "様</p><br>" +
                        "<p>この度は、MOVE UP GOODSをご利用いただき、誠にありがとうございます。</p>" +
                        "<p>下記内容にて、ご注文を承りました。</p><br>" +
                        "<p>ご注文番号：" + goods.getSerialNumber() + "</p>" +
                        "<p>ご注文日時：" + goods.getDate() + "</p><br>" +
                        "<p>商品出荷時に、発送完了メールをお送りさせていただきます。</p>" +
                        "<p>商品の出荷はご注文日より、通常7営業日(土日祝除く)となります。</p>" +
                        "<p>※ご注文が集中した場合や連休の前後は、お届けが遅れる場合もございますので予めご了承ください。</p><br>" +
                        "<hr><br>" +

                        "<p>【ご注文内容】</p><br>" +
                        goodsinfo +
                        "<hr><br>" +
                        "<p>送料：" + goods.getDeliveryExpense() + "円</p>" +
                        "<p>決算方法：" + goods.getPurchaseType() + "払い</p>" +
                        "<p>合計金額：" + goods.getTotalPrice() + "円</p><br>" +
                        "<hr><br>" +
                        "<p>【お客様ご住所】</p><br>" +
                        "<p>" + goods.getName() + "様</p>" +
                        "<p>■ご住所</p>" +
                        "<p>〒 "+ goods.getZipcode() +"</p>" +
                        "<p>"+ goods.getAddress() +"</p>" +
                        "<p>■電話番号</p>" +
                        "<p>"+ goods.getTel() +"</p>" +
                        "<p>■メールアドレス</p>" +
                        "<p>"+ goods.getMail() +"</p><br>" +
                        "<hr><br>" +
                        "<p>【配送先ご住所】</p><br>" +
                        "<p>" + goods.getName2() + "様</p>" +
                        "<p>■ご住所</p>" +
                        "<p>〒 "+ goods.getZipcode2() +"</p>" +
                        "<p>"+ goods.getAddress2() +"</p>" +
                        "<p>■電話番号</p>" +
                        "<p>"+ goods.getTel2() +"</p>" +
                        "<hr><br>" +
                        "<p>【お支払い方法】</p><br>" +
                        "<p>" + goods.getPurchaseType() + "払い</p>" +
                        "<p>※</p><br>" +
                        "<hr><br>" +
                        "<p>本メールにお心当たりがない場合は、お手数ですが下記フォームよりご連絡をお願いいたします。</p>" +
                        "URL：<a href='" + goods.getUrl1() + "'>" + goods.getUrl1() + "</a></p>" +
                        "<p>本メールは、送信専用となっております。返信いただいてもご連絡できませんのでご了承ください。ご不明な点や質問等がございましたら、下記「お客様お問い合わせ窓口」よりお問い合わせください。</p><br>" +
                        "<hr><br>" +
                        "<p>【お客様お問い合わせ窓口】</p>" +
                        "<p>086-250-8089 (平日13:00〜18:00)</p>" +
                        "<p>※土日祝、年末年始、夏季休暇、GWを除く</p><br>" +
                        "<p>■購入履歴の確認方法</p>" +
                        "<p>JAPAN MOVE UP WEST WEBよりログインし、マイページの購入履歴よりご確認ください。</p>" +
                        "URL：<a href='" + goods.getUrl2() + "'>" + goods.getUrl2() + "</a></p><br>" +
                        "<hr><br>" +
                        "<p>JAPAN MOVE UP WEST</p>" +
                        "<div>"
        };
        sendMail(mailTo, "", GOODS_PAYMENT_CVS_SUBJECT, GOODS_HTMLBODY);
    }

    public void sendGoodsPurchaseDeliverGoodsMail(String mailTo, GoodsPurchaseMailForm goods) throws IOException {
        String goodsinfo;
        if (goods.getGoodsInfoForms().size() == 1) {
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                    "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                    "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                    "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                    "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                    "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
        }else{
            goodsinfo = "<p>■注文数：" + goods.getGoodsInfoForms().get(0).getQuantity() + " 点</p>" +
                    "<p>■商品名：" + goods.getGoodsInfoForms().get(0).getGoodsName() + "</p>" +
                    "<p>・カラー：" + goods.getGoodsInfoForms().get(0).getColor() + "</p>"+
                    "<p>・サイズ："+ goods.getGoodsInfoForms().get(0).getSize() +"</p>"+
                    "<p>■小計：" + goods.getGoodsInfoForms().get(0).getPrice() + "円</p>" +
                    "<p>■消費税：" + goods.getGoodsInfoForms().get(0).getTax() + "円</p><br>"  ;
            for (int i = 1; i < goods.getGoodsInfoForms().size(); i++) {
                goodsinfo += "<p>■注文数：" + goods.getGoodsInfoForms().get(i).getQuantity() + " 点</p>" +
                        "<p>■商品名：" + goods.getGoodsInfoForms().get(i).getGoodsName() + "</p>" +
                        "<p>・カラー：" + goods.getGoodsInfoForms().get(i).getColor() + "</p>"+
                        "<p>・サイズ："+ goods.getGoodsInfoForms().get(i).getSize() +"</p>"+
                        "<p>■小計：" + goods.getGoodsInfoForms().get(i).getPrice() + "円</p>" +
                        "<p>■消費税：" + goods.getGoodsInfoForms().get(i).getTax() + "円</p><br>"  ;
            }
        }

        // The HTML body for the email.
        String[] GOODS_HTMLBODY = new String[]{
                "<div style='padding:20px;'>" +
                        "<h2>JAPAN MOVE UP WESTのご注文商品の出荷が完了しました。</h2><br>" +
                        "<p>" + goods.getName() + "様</p><br>" +
                        "<p>この度は、MOVE UP GOODSをご利用いただき、誠にありがとうございます。</p>" +
                        "<p>ご注文いただきました商品につきまして、本日「日本郵便」にて出荷が完了いたしました。</p><br>" +
                        "<p>お荷物配送状況のご確認は、下記よりご確認くださいませ。。</p>" +
                        "<hr><br>" +

                        "<p>【商品配送状況】</p><br>" +
                        goodsinfo +
                        "<hr><br>" +
                        "<p>送料：" + goods.getDeliveryExpense() + "円</p>" +
                        "<p>決算方法：" + goods.getPurchaseType() + "払い</p>" +
                        "<p>合計金額：" + goods.getTotalPrice() + "円</p><br>" +
                        "<hr><br>" +
                        "<p>【配送先ご住所】</p><br>" +
                        "<p>" + goods.getName2() + "様</p>" +
                        "<p>■ご住所</p>" +
                        "<p>〒 "+ goods.getZipcode2() +"</p>" +
                        "<p>"+ goods.getAddress2() +"</p>" +
                        "<p>■電話番号</p>" +
                        "<p>"+ goods.getTel2() +"</p>" +
                        "<hr><br>" +
                        "<p>本メールにお心当たりがない場合は、お手数ですが下記フォームよりご連絡をお願いいたします。</p>" +
                        "URL：<a href='" + goods.getUrl1() + "'>" + goods.getUrl1() + "</a></p>" +
                        "<p>本メールは、送信専用となっております。返信いただいてもご連絡できませんのでご了承ください。ご不明な点や質問等がございましたら、下記「お客様お問い合わせ窓口」よりお問い合わせください。</p><br>" +
                        "<hr><br>" +
                        "<p>【お客様お問い合わせ窓口】</p>" +
                        "<p>086-250-8089 (平日13:00〜18:00)</p>" +
                        "<p>※土日祝、年末年始、夏季休暇、GWを除く</p><br>" +
                        "<p>■購入履歴の確認方法</p>" +
                        "<p>JAPAN MOVE UP WEST WEBよりログインし、マイページの購入履歴よりご確認ください。</p>" +
                        "URL：<a href='" + goods.getUrl2() + "'>" + goods.getUrl2() + "</a></p><br>" +
                        "<hr><br>" +
                        "<p>JAPAN MOVE UP WEST</p>" +
                        "<div>"
        };
        sendMail(mailTo, "", DELIVER_GOODS_SUBJECT, GOODS_HTMLBODY);
    }

    public void sendEntryMail(String mailTo, String subject, String content) throws IOException {
        sendEntryMail(mailTo, "", subject, content);
    }

    public void sendRetreattMail(String mailTo) throws IOException {
        sendMail(mailTo, "", RETREAT_CLUB, RETREAT_HTMLBODY);
    }

    public void sendRegistMail(String mailTo, String tokenUrl) throws IOException {
        sendMail(mailTo, tokenUrl, REGIST_SUBJECT, REGIST_HTMLBODY);
    }

    public void sendForgetMail(String mailTo, String tokenUrl) throws IOException {
        sendMail(mailTo, tokenUrl, FORGET_SUBJECT, FORGET_HTMLBODY);
    }

    public void sendChangeMail(String mailTo, String tokenUrl) throws IOException {
        sendMail(mailTo, tokenUrl, CHANGE_SUBJECT, CHANGE_HTMLBODY);
    }


    private void sendMail(String mailTo, String tokenUrl, String subject, String[] textList) throws IOException {
        String mailContent = "";
        for (int i = 0; i < textList.length; i++) {
            mailContent += textList[i];
            if (i < textList.length - 1) {
                mailContent += tokenUrl;
            }
        }

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(mailTo))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(mailContent))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(mailContent)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(FROM);
            // Comment or remove the next line if you are not using a
            // configuration set
//                    .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);

            logger.info("Email sent! to subject=" + subject + " mail=" + mailTo);

        } catch (Exception ex) {
            logger.warn("mail address=" + mailTo);
            logger.warn("Email sent failed! to subject=" + subject + " mail=" + mailTo);
            logger.warn("Email sent failed! to mail=" + ex.getMessage());
            logger.warn("Email sent failed! to mail=" + ex.toString());
            ex.printStackTrace();
        }
    }

    private void sendEntryMail(String mailTo, String tokenUrl, String subject, String content) throws IOException {
        String mailContent = "";
//        for(int i=0;i<textList.length;i++){
//            mailContent += textList[i];
//            if(i < textList.length - 1) {
//                mailContent += tokenUrl;
//            }
//        }
        try {

            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(mailTo))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(content))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(content)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(ENTRY);
            // Comment or remove the next line if you are not using a
            // configuration set
//                    .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);

            logger.info("Email sent! to subject=" + subject + " mail=" + mailTo);

        } catch (Exception ex) {
            logger.warn("mail address=" + mailTo);
            logger.warn("Email sent failed! to subject=" + subject + " mail=" + mailTo);
            logger.warn("Email sent failed! to mail=" + ex.getMessage());
            logger.warn("Email sent failed! to mail=" + ex.toString());
            ex.printStackTrace();
        }
    }


}
