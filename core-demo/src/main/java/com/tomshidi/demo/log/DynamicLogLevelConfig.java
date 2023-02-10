package com.tomshidi.demo.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动态日志等级配置类
 * @author tomshidi
 * @date 2022/12/29 13:51
 */
@Component
@ConfigurationProperties(prefix = "tomshidi.log", ignoreInvalidFields = false)
public class DynamicLogLevelConfig {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DynamicLogLevelConfig.class);

    private static final String ROOT_LOGGER_NAME = "ROOT";

    /**
     * ROOT日志携带的appender列表，动态配置项中的logger会添加该appender列表
     */
    private Map<String, Appender<ILoggingEvent>> appenderMap;

    /**
     * 使用xml配置的日志等级
     */
    private Map<String, String> xmlLoggerMap;

    /**
     * 日志动态配置项，例如：
     * agency:
     *   log:
     *     level-map:
     *       ROOT: INFO
     *       cn.com.tomshidi: WARN
     *       org:
     *         springframework: WARN
     */
    private Map<String, String> levelMap;

    /**
     * 保存上一次配置项agency.log.level-map的值
     */
    private Map<String, String> preLevelMap = new HashMap<>(4);


    public void setLevelMap(Map<String, String> levelMap) {
        this.levelMap = levelMap;
        refreshLogLevel(levelMap);
        LOGGER.info("动态日志等级已刷新");
    }

    private void refreshLogLevel(Map<String, String> levelMap) {
        LoggerContext loggerContext = ContextSelectorStaticBinder.getSingleton().getContextSelector().getLoggerContext();
        Logger rootLogger = loggerContext.getLogger(ROOT_LOGGER_NAME);
        appenderMap = new HashMap<>(4);
        Iterator<Appender<ILoggingEvent>> appenderIterator = rootLogger.iteratorForAppenders();
        // 全局使用root配置的appender，由于每次nacos配置刷新会导致appender重置，所以需要重新从root获取
        while (appenderIterator.hasNext()) {
            Appender<ILoggingEvent> appender = appenderIterator.next();
            appenderMap.put(appender.getName(), appender);
        }
        List<Logger> loggerList = loggerContext.getLoggerList();
        // 保存系统启动时xml文件中配置的日志等级
        if (xmlLoggerMap == null) {
            xmlLoggerMap = new HashMap<>(4);
            if (!ObjectUtils.isEmpty(loggerList)) {
                xmlLoggerMap = loggerList.stream()
                        .collect(Collectors.toMap(Logger::getName,
                                item -> item.getLevel() == null ? "" : item.getLevel().toString(),
                                (k1, k2) -> k2));
            }
        }
        String logName;
        String levelStr;
        Level level;
        Logger logger;
        // 每次nacos配置刷新，会导致自定义动态日志等级被重置，所以这里需要重新设置
        for (Map.Entry<String, String> entry : levelMap.entrySet()) {
            logName = entry.getKey();
            level = Level.toLevel(entry.getValue());
            logger = loggerContext.getLogger(logName);
            enabledLog(logger, level);
        }
        for (Map.Entry<String, String> entry : preLevelMap.entrySet()) {
            logName = entry.getKey();
            logger = loggerContext.getLogger(logName);
            // 判断动态配置项中是否删除了某个日志配置
            if (!levelMap.containsKey(logName)) {
                levelStr = xmlLoggerMap.get(logName);
                if (ObjectUtils.isEmpty(levelStr)) {
                    disabledLog(logger);
                } else {
                    enabledLog(logger, Level.toLevel(levelStr));
                }
            }
        }
    }

    /**
     * 启用logger，并关闭其向父logger传播的能力
     *
     * @param logger 待启用的logger
     * @param level  日志等级
     */
    public void enabledLog(Logger logger, Level level) {
        logger.setLevel(level);
        logger.setAdditive(false);
        if (ROOT_LOGGER_NAME.equalsIgnoreCase(logger.getName())) {
            return;
        }
        for (Appender<ILoggingEvent> appender : appenderMap.values()) {
            while (logger.getAppender(appender.getName()) != null) {
                logger.detachAppender(appender.getName());
            }
            if (logger.getAppender(appender.getName()) == null) {
                logger.addAppender(appender);
                LOGGER.info("{} 等级 {} 添加 {}", logger.getName(), level, appender.toString());
            }
        }
    }

    /**
     * 禁用logger，并开启其向父logger传播的能力
     * 即：假设从level-map配置中移除["cn.com.do1":"INFO"]，此时会将消息传播至ROOT打印
     *
     * @param logger 待禁用的logger
     */
    public void disabledLog(Logger logger) {
        logger.setLevel(null);
        logger.setAdditive(true);
        for (Appender<ILoggingEvent> appender : appenderMap.values()) {
            while (logger.getAppender(appender.getName()) != null) {
                logger.detachAppender(appender.getName());
                LOGGER.info("{} 移除 {}", logger.getName(), appender.toString());
            }
        }
    }

    /**
     * 配置动态刷新map类型的变量时，如果旧配置变量存在，
     * 使用的是Map#putAll方法，会导致配置删除无法生效。
     * 因此在配置刷新前调用此destroy方法，能保证将旧变量置空。
     */
    @PreDestroy
    public void destroy() {
        preLevelMap = levelMap;
        levelMap = null;
    }
}
