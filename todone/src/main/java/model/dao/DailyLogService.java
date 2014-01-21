package model.dao;

import model.DailyLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public interface DailyLogService {

    public void saveDailyLog(DailyLog dailyLog,String username) throws IOException;

    public  java.util.List<DailyLog> getDailyLogs(String user,Integer start);

}
