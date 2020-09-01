package com.xiaozu.web.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;

/**
 * HTML filtering utility for protecting against XSS (Cross Site Scripting).
 * <p>
 * This code is licensed LGPLv3
 * <p>
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http://code.iamcal.com/php/lib_filter/
 * <p>
 * The trickiest part of the translation was handling the differences in regex handling
 * between PHP and Java.  These resources were helpful in the process:
 * <p>
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http://us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http://www.regular-expressions.info/modifiers.html
 * <p>
 * A note on naming conventions: instance variables are prefixed with a "v"; global
 * constants are in all caps.
 * <p>
 * Sample use:
 * String input = ...
 * String clean = new HTMLFilter().filter( input );
 * <p>
 * The class is not thread safe. Create a new instance if in doubt.
 * <p>
 * If you find bugs or have suggestions on improvement (especially regarding
 * performance), please contact us.  The latest version of this
 * source, and our contact details, can be found at http://xss-html-filter.sf.net
 *
 * @author Joseph O'Connell
 * @author Cal Hendersen
 * @author Michael Semb Wever
 */
public final class HtmlFilter {

    /**
     * 默认使用relaxed()
     * 允许的标签: a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    private Whitelist whiteList;


    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private Document.OutputSettings outputSettings;


    private HtmlFilter() {
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @param pretty    是否格式化
     * @return HtmlFilter
     */
    public static HtmlFilter create(Whitelist whiteList, boolean pretty) {
        HtmlFilter filter = new HtmlFilter();
        if (whiteList == null) {
            filter.whiteList = Whitelist.relaxed();
        }
        filter.outputSettings = new Document.OutputSettings().prettyPrint(pretty);
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @return HtmlFilter
     */
    public static HtmlFilter create() {
        return create(null, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @return HtmlFilter
     */
    public static HtmlFilter create(Whitelist whiteList) {
        return create(whiteList, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @param pretty      是否格式化
     * @return HtmlFilter
     */
    public static HtmlFilter create(List<String> excludeTags, List<String> includeTags, boolean pretty) {
        HtmlFilter filter = create(null, pretty);
        //要过滤的标签
        if (includeTags != null && !includeTags.isEmpty()) {
            String[] tags = (String[]) includeTags.toArray(new String[0]);
            filter.whiteList.removeTags(tags);
        }
        //例外标签
        if (excludeTags != null && !excludeTags.isEmpty()) {
            String[] tags = (String[]) excludeTags.toArray(new String[0]);
            filter.whiteList.addTags(tags);
        }
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @return HtmlFilter
     */
    public static HtmlFilter create(List<String> excludeTags, List<String> includeTags) {
        return create(excludeTags, includeTags, false);
    }

    /**
     * @param content 需要过滤内容
     * @return 过滤后的String
     */
    public String clean(String content) {
        return Jsoup.clean(content, "", this.whiteList, this.outputSettings);

    }
}
