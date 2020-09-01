package com.xiaozu.core.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Map<String, String> errorMap = new HashMap();
    private String msg;

    public ValidateException() {
    }

    public ValidateException(String parameter, String msg) {
        this.addError(parameter, msg);
    }

    public static void throwIt(String parameter, String msg) throws ValidateException {
        throw new ValidateException(parameter, msg);
    }

    public static void throwIfStateFalse(boolean expression, String parameter, String msg) throws ValidateException {
        if (!expression) {
            throwIt(parameter, msg);
        }

    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public Map<String, String> getErrorMap() {
        return this.errorMap;
    }

    public void addError(String parameter, String msg) {
        this.errorMap.put(parameter, msg);
        this.msg = null;
    }

    public String getMessage() {
        if (this.msg == null) {
            if (this.errorMap.isEmpty()) {
                this.msg = "";
            } else {
                StringBuilder sb = new StringBuilder(this.errorMap.size() * 15);
                Iterator i$ = this.errorMap.entrySet().iterator();

                while (i$.hasNext()) {
                    Map.Entry entry = (Map.Entry) i$.next();
                    sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                }

                sb.deleteCharAt(sb.length() - 1);
                this.msg = sb.toString();
            }
        }

        return this.msg;
    }

    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
