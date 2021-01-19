package jp.co.vermore.common.mvc;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ApplicationInitializer
        implements WebApplicationInitializer
{
    public static final String mFirebaseKey = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"moveupwest-1521532586985\",\n" +
            "  \"private_key_id\": \"3ebed1a38b55df224ab3769c4749bd8723bf3685\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzPC7us8yCoGRy\\nh60isdWQt98+Ok4zrHEHNAoSRn4/beowkW0A942JOKUiChT39tBd5T+0q5qqpYQB\\n6AEIjAFdGIIGxNAm5W2G1kuOkT3O+Fo/vLRqRdjunw4rjQeuybNgsSNWsjqmkKgw\\nM2/AJYMlBvjpLNucan5yAvpzyBktPWPli0g603C5Anxi6/rpVzB8BJqStTmHec9a\\n4d5PIAChDfE8ePdk3O92uJS8ppGQ5/VhowZn0kcmd7w/d69PCXQu5EYAOMCnc2ow\\nUTTWlrn5+yQ4jB9PUif2czumI9M3oOpszDsKpx/SIvPRd7NVSRyMjO8AS8KBeqlx\\nSb7w7rLJAgMBAAECggEAFVEMMmTFJwiBJctr3J9am2V46i9YR2ZEA5Otenayk2Pk\\nyU20cXvftBEd96ZMD+ElbzNhkCDkKlU41dq0S8JN87bjWP99k5CwBew07IHq4FOS\\ndTfh261Os3OMhYK2hhPUTwTGQ4x7M/3qmN/3M5qqhiP2BNJqVIgoXXIr/Arxbpsl\\n5fOMZXyuYRpt0mXRAkzU1g3q0lfTt5Iv09p6TDsQxkS8sDenOCRbMZxd70IEMSMA\\nkRSqnO+3+K2ns+cdvqdmZoNyO72Ln5q4ciAtuPIkbhbWF+hC9EsQIxjDcSgkvEus\\nEK8wkWwfUPDQ6pk/SSwcTuxZTIRRh2O1w4xGONORMQKBgQD4LDrfpjjil9pr1xjP\\nAf3jb7GkeD4uW/KUbZ5myjahI58qeWXuvZLbf+yjzSO0C6pncH3Ha3cge0OyZ0r4\\nLtKvxX4qFAESYzWOsmZrbRoJ71XVIBdDo2Go+Z7MwpnXrmDKJPmmYbD8aGpV81jZ\\n7bvVmUS4NbcusCDk6GBwfYpImQKBgQC441gXQdAywX3jlHh+7yJQt9cowcsJXfBk\\ndgmLlp5/C9+fTiiHPu/PpBfMJV+I6bNLUrBv/400XCa+m9mU8lm4SnsogAHagtCg\\n1UQsUoeAZ+G1ImvWtM19kv0RgMk3HQgqBgkV6A2TwBjYvmIvAu3JTs40+ylLYI5K\\n+VxEyYIpsQKBgHVc7WaxBDvclnXjXh8fkQLK4pamKrnfz+Nzx7JStO0VnIMIsDTs\\nQBQ/9hgsvlK6JZcPctwpLUgWIxiukpy1//sSCa06kpswgbZ/FcJ98Q97uPq2V2os\\nTNYNXpT0/q4qECnovIt7rahgdNE66Oz+6ZKFDiOTe5K/xn6aimkIZaoZAoGBAIH7\\nu4PmTjRQ2mu0hx5eixaKmSbbUB5Ov+IxJjbP9rBFLbt3HIExGzI1IFHy+H5uJxJO\\nmSK0XJayLr8VHNxdofwxyPJJRIRWMczRAvPSNddtsT5Ca+RwCoQ9YdP7YftwWrwI\\nsDyJf3oftJg1CRwaCAjecZTH+1k2EfPd6iOmM++RAoGBANu6v6lG4pT2LURDCJio\\nZxKZJXKVUPa2xyrPzl5+Qdvi47Uj72nyOZ0ATuKaL0LP16UyWf21L718BY0+kjBl\\nTY2ECfY0WD7gr46yfeyZ0xtYZZspY743kIs+kdTf+dAXvmW9skfYUBVQSnNeoMco\\neROzzLIuPwLoSXf1MToJt0kl\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-4oz24@moveupwest-1521532586985.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"100940827624041802849\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-4oz24%40moveupwest-1521532586985.iam.gserviceaccount.com\"\n" +
            "}";

    @Override
    public void onStartup(ServletContext context) {
        String defaultProfile = context.getInitParameter("spring.profiles.default");
        String activeProfile = System.getProperty("spring.profiles.active");
        if (activeProfile == null) {
            activeProfile = defaultProfile;
        }

        System.setProperty("env", activeProfile);

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(mFirebaseKey.getBytes("utf-8"))))
                    .setDatabaseUrl("https://moveupwest-1521532586985.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }
}