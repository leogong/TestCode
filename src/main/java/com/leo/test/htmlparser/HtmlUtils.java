package com.leo.test.htmlparser;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.util.NodeList;

public class HtmlUtils {
    /**
     * 根据URL请求返回HTML
     * 
     * @param urlAdd
     * @param srcCharset
     * @return
     * @throws Exception
     */
    public static String getHtmlByUrl(String urlAdd, String srcCharset) throws Exception {
        HttpClient httpClient = new HttpClient();
        /*
         * 请求超时时间
         */
        // httpClient.getParams().setParameter(
        // CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
        // httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
        // 6000);
        GetMethod getMethod = new GetMethod(urlAdd);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, true));
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("请求状态错误，状态：" + statusCode);
            }
            String responseBody = getMethod.getResponseBodyAsString();
            if (StringUtils.isNotEmpty(srcCharset))
                responseBody = new String(responseBody.getBytes("ISO-8859-1"), srcCharset);
            return responseBody;
        } finally {
            getMethod.releaseConnection();
        }
    }

    /**
     * 根据请求的URL和过滤条件返回合法节点
     * 
     * @param url
     * @param filter
     * @param srcCharset
     * @return
     * @throws Exception
     */
    public static NodeList getNodeListByFilter(String url, NodeFilter filter, String srcCharset) throws Exception {
        //
        Parser parser = new Parser();
        parser.setEncoding("ISO-8859-1");
        parser.setInputHTML(HtmlUtils.getHtmlByUrl(url, srcCharset));
        //
        PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
        parser.setNodeFactory(factory);
        factory.registerTag(new EmTag());
        NodeList nList = parser.extractAllNodesThatMatch(filter);
        return nList;
    }

    /**
     * 传入HTML和过滤条件，进行过滤
     * 
     * @param filter
     * @param html
     * @return
     * @throws Exception
     */
    public static NodeList getNodeListByFilter(NodeFilter filter, String html) throws Exception {
        Parser parser = new Parser();
        parser.setEncoding("8859_1");
        parser.setInputHTML(html);
        PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
        factory.registerTag(new EmTag());
        parser.setNodeFactory(factory);
        NodeList nList = parser.extractAllNodesThatMatch(filter);
        return nList;
    }
}
