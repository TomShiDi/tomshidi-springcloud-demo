package com.tomshidi.mybatis.interceptors;

import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Statement;

/**
 * 拦截mybatis的sql语句构造器
 * @author tomshidi
 * @since 2023/3/27 9:50
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class})
})
public class ModifyParamInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();

        if (SecurityHelper.needEncrypt(parameterObject.getClass())) {
            SecurityHelper.entityFieldEncryptDecrypt(parameterObject, true);
            // 将修改后的 BoundSql 重新设置回 StatementHandler 中
//            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//            metaStatementHandler.setValue("delegate.boundSql", boundSql);
        }

        // 执行目标方法并返回结果
        return invocation.proceed();
    }
}






