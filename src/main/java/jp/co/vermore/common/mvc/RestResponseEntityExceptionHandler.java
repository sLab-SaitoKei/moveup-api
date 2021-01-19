package jp.co.vermore.common.mvc;

import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * RestResponseEntityExceptionHandler
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/20 18:14
 * Copyright: sLab, Corp
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(APIException.class)
    @ResponseBody
    public JsonObject handleMyException(APIException ex, WebRequest request) {
        JsonObject jsonObject = new JsonObject(ex.getStatus());
        return jsonObject;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public JsonObject handleViolationException(ConstraintViolationException ex, WebRequest request) {
        String message = "";
        Iterator<ConstraintViolation<?>> violationsIterator = ex.getConstraintViolations().iterator();
        while (violationsIterator.hasNext()) {
            message += violationsIterator.next().getMessage();
            if(violationsIterator.hasNext()){
                message += ", ";
            }
        }
        JsonObject jsonObject = new JsonObject(JsonStatus.PARAMETER_ERROR);
        jsonObject.setMessage(message);
        return jsonObject;
    }

    // SpringBoot内で用意されている例外については、ResponseEntityExceptionHandlerクラスで例外ごとに
    // 専用のメソッドが用意されているのでそれをオーバーライドする
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, "MethodArgumentNotValidException", null, HttpStatus.INTERNAL_SERVER_ERROR, request); // todo param error
    }

    // すべての例外をキャッチする
    // どこにもキャッチされなかったらこれが呼ばれる
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonObject handleAllException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        JsonObject jsonObject = new JsonObject(JsonStatus.UNKNOWN_ERROR);
        return jsonObject;
    }

    // すべてのハンドリングに共通する処理を挟みたい場合はオーバーライドする
    // ex) ログを吐くなど
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 任意の処理
        return super.handleExceptionInternal(ex, body, headers, status, request); // todo
    }
}
