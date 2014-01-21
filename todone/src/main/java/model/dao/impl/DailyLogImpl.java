package model.dao.impl;

import model.DailyLog;
import model.dao.DailyLogService;
import org.springframework.stereotype.Service;
import utils.PageInfo;
import utils.files.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DailyLogImpl implements DailyLogService {


    public void saveDailyLog(DailyLog dailyLog, String username) throws IOException {
        FileUtils.writeFileContent(FileUtils.path + "/" + username + ".txt", dailyLog.toString());
    }

    public List<DailyLog> getDailyLogs(String user, Integer start) {
        java.util.List<DailyLog> list = new ArrayList<DailyLog>();
        try {
            String[] arr = FileUtils.getFileContents(FileUtils.path + "/" + user + ".txt").trim().split(FileUtils.LINE_SEPARATOR, -1);
            for (String s : arr) {
                DailyLog dailyLog = DailyLog.wrapDailyLog(s);
                if (dailyLog != null)
                    list.add(dailyLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(list);
        list = list.subList(start, Math.min(start + PageInfo.DEFAULT_PAGESIZE, list.size()));
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
