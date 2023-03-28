package com.tomshidi.mybatis.interceptors;

import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Properties;

/**
 * 拦截mybatis的sql执行器
 * @author tomshidi
 * @since 2023/3/24 16:57
 */
@Component
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class ReturnValueInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        if (returnValue instanceof Collection) {
            Collection<Object> collection = (Collection<Object>) returnValue;
            if (!ObjectUtils.isEmpty(collection)) {
                Object item = collection.iterator().next();
                if (SecurityHelper.needEncrypt(item.getClass())) {
                    SecurityHelper.entityFieldEncryptDecrypt(collection, "", false);
                }
            }
        } else {
            SecurityHelper.entityFieldEncryptDecrypt(returnValue, false);
        }
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
