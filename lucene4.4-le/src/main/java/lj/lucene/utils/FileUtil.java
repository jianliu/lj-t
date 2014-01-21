package lj.lucene.utils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-5
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {
    public static final int BUFFER_SIZE = 4096;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "/n");
    public static String path = System.getProperty("user.dir");
    private static final String LINUX_CHARSET = "utf-8";
    private static final String WINDOWS_CHARSET = "GBK";

    public static String getFileContents(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(filename);
        return getFileContents(f);
    }

    public static String getFileContents(File f) throws FileNotFoundException, UnsupportedEncodingException {

        if (!f.exists()) return "";
        InputStream in = new BufferedInputStream(new FileInputStream(f));
        InputStreamReader is = null;
        String content = "";

        try {
            is = new InputStreamReader(in, WINDOWS_CHARSET);
            char[] chars = new char[1024];
            while (is.read(chars) != -1) {
//                System.out.println(chars);
                content += String.valueOf(chars);
            }
            is.close();
        } catch (Exception e) {
            is = new InputStreamReader(in, LINUX_CHARSET);
            char[] chars = new char[1024];
            try {
                while (is.read(chars) != -1) {
                    //                System.out.println(chars);
                    content += String.valueOf(chars);
                }
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
        }
        return content.trim();
    }

    public void readFile(File f) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void writeFileContent(String filename, String content) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, WINDOWS_CHARSET);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(outputStreamWriter);
            writer.write(content + System.getProperty("line.separator", "/n"));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        System.out.println("---" + getFileContents(FileUtil.path + "/users.txt").trim() + "---");
//       String[] arr= getFileContents("F:\\mo2\\todone\\呵.txt").trim().split("\r\n");
//        for(String s:arr){
//            DailyLog dailyLog=DailyLog.wrapDailyLog(s);
//            int i=1;
//        }

    }

}
