package crawler;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.EntityUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 14-1-20
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private  static final Logger LOGGER = LoggerFactory.getLogger(Client.class.getName());


    public static void main(String[] args) throws Exception {
        LOGGER.info("debug");
        String host = "115.239.210.199";
        int port = 80;
        String url = "http://www.qq.com";
        String url2 = "http://www.yshjava.cn";
        String html = "";
//        HttpClient httpclient = new DefaultHttpClient();
//        httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
//        httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//        httpclient.getParams().setBooleanParameter(org.apache.http.client.params.ClientPNames.HANDLE_REDIRECTS, true);
//        httpclient.getParams().setLongParameter(org.apache.http.client.params.ClientPNames.CONN_MANAGER_TIMEOUT, 5000l);
//        httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
//        httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)");

        long timeIn = System.currentTimeMillis();
        long timeOut = System.currentTimeMillis();
        HttpClient httpclient = Client.getDefaultClient(true);

        try {
            HttpGet httpget = new HttpGet(url);
            HttpHost hcProxyHost = new HttpHost(host, port);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, hcProxyHost);
            httpclient.getParams().setParameter("X-Forward-For", host);
            System.out.println("executing request " + httpget.getURI());

            // 执行HTTP请求 
            HttpResponse response = httpclient.execute(httpget);

            Charset charset = Charset.forName("GBK");
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println("----------------------------------------");
            // 得到响应的实体 
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                try {
//                    inputStream.read();
                    Reader reader = new InputStreamReader(inputStream, charset);
                    int i = (int) entity.getContentLength();
//                    //期望的操作
//                    CharArrayBuffer buffer = new CharArrayBuffer(i);
//                    char[] tmp = new char[1024];
//
//                    int l;
//                    while ((l = reader.read(tmp)) != -1) {
//                        buffer.append(tmp, 0, l);
//                    }
//                    html = buffer.toString();
                    html = EntityUtil.toString(entity, charset);
                } catch (RuntimeException ex) {
                    httpget.abort();
                    throw ex;
                } finally {
                    try {
                        inputStream.close();
                    } catch (Exception ignore) {
                    }

                }
            }
        } catch (ConnectTimeoutException ex) {
            System.out.println("ConnectTimeoutException happened");
        } catch (SocketTimeoutException ex) {
            System.out.println("SocketTimeoutException happened");
        } catch (IOException ex) {
            if (ex instanceof ConnectTimeoutException) {
                System.out.println("time out happened");
            } else
                throw ex;
        } finally {
            httpclient.getConnectionManager().shutdown();
            timeOut = System.currentTimeMillis();
        }
        System.out.println("----------######-----------time:" + (timeOut - timeIn) + "ms");
//        System.out.println(html);

    }

    public static DefaultHttpClient getDefaultClient(Boolean supportGzip) {

        HttpParams params = new BasicHttpParams();
        HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
        paramsBean.setVersion(HttpVersion.HTTP_1_1);
        paramsBean.setContentCharset("UTF-8");
        paramsBean.setUseExpectContinue(false);
        params.setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)");
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 12000);
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 18000);
        params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
        DefaultHttpClient httpClient = new DefaultHttpClient(params);

        if (supportGzip) {
            httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
                public void process(org.apache.http.HttpRequest request, HttpContext context) {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }
                }

            });
            httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
                public void process(HttpResponse response, HttpContext context) {
                    HttpEntity entity = response.getEntity();
                    Header contentEncoding = entity.getContentEncoding();
                    if (contentEncoding != null) {
                        HeaderElement[] codes = contentEncoding.getElements();
                        for (HeaderElement code : codes) {
                            if (code.getName().equalsIgnoreCase("gzip")) {
                                // println(response.getEntity.getContent.available())
                                response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                                return;
                            }
                        }
                    }
                }
            });
        }

        return httpClient;
    }


}
