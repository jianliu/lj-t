package util;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;


public class EntityUtil {
    public static final Charset GBK = Charset.forName("GBK");
    public static final Charset GB2312 = Charset.forName("GB2312");

    public static String toString(HttpEntity entity, Charset defaultCharset)
            throws IOException, ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null)
            return null;
        try {
            if (entity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int) entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            ContentType contentType = ContentType.get(entity);
            Charset charset = (contentType != null && contentType.getCharset() != null) ? contentType.getCharset() : defaultCharset;
            if (charset == null)
                charset = HTTP.DEF_CONTENT_CHARSET;
            else if (charset == GB2312)
                charset = GBK;
            Reader reader = new InputStreamReader(instream, charset);
            CharArrayBuffer buffer = new CharArrayBuffer(i);
            char[] tmp = new char[1024];

            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
            instream.close();
        }

    }

    public static String toString(HttpEntity entity, String defaultCharset)
            throws IOException, ParseException {
        return toString(entity, Charset.forName(defaultCharset));
    }

    public static String toString(HttpEntity entity)
            throws IOException, ParseException {
        return toString(entity, (Charset) null);
    }
}
