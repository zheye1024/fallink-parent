package com.xiaozu.core.converter;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.xiaozu.core.utils.mark.MarkTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Author zheye
 * @Date 2020/3/16 0016 10:45
 * @Version 1.0
 */
public class DesensitizationConverter extends MessageConverter {

    private Logger logger = LoggerFactory.getLogger(DesensitizationConverter.class);

    @Override
    public String convert(ILoggingEvent event) {
        StringBuffer buffer = new StringBuffer(event.getMessage());
        boolean isMarkTable = false;
        try {
            Object[] objectArray = event.getArgumentArray();
            if (objectArray != null && objectArray.length > 0) {
                for (Object object : objectArray) {
                    // 对数据进行脱敏处理
                    String jsonStr = MarkTableUtil.markTableJson(object);
                    if (!StringUtils.isEmpty(jsonStr)) {
                        isMarkTable = true;
                        buffer.append(jsonStr);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("自定义日志脱敏解析异常：{}", e);
        } finally {
            if (isMarkTable) {
                return buffer.toString();
            } else {
                return event.getFormattedMessage();
            }
        }
    }
}
