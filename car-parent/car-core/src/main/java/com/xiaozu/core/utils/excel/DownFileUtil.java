package com.xiaozu.core.utils.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Log4j2
public class DownFileUtil {

    /**
     * 得到下载文件流信息
     *
     * @param fileName
     * @return
     */
    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 下载项目根目录下doc下的文件
     *
     * @param parentPath 父目录
     * @param response   response
     * @param fileName   文件名
     * @return 返回结果 成功或者文件不存在
     */
    public static String downloadXlsxFile(HttpServletResponse response, String parentPath, String fileName) {
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            log.info("下载模板信息路径：{}", parentPath + File.separator + fileName);
            inputStream = getResourcesFileInputStream(parentPath + File.separator + fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            response.setContentType("application/binary;charset=ISO8859-1");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            out = response.getOutputStream();
            workbook.write(out);
        } catch (FileNotFoundException e1) {
            log.error("系统找不到指定文件：{}", e1);
            return "系统找不到指定的文件";
        } catch (IOException e) {
            log.error("读取文件流异常：{}", e);
            return "读取文件流异常";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}
