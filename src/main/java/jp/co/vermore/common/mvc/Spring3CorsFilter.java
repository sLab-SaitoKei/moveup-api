package jp.co.vermore.common.mvc;

/**
 * Spring3CorsFilter
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/12 23:10
 * Copyright: sLab, Corp
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Spring3CorsFilter {}