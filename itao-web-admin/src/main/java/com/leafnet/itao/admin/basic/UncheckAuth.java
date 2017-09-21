package com.leafnet.itao.admin.basic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解表示当前方法无须校验用户是否登录即可调用
 * @author Wangd
 * @version 1.0.0
 */
@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface UncheckAuth {

}
