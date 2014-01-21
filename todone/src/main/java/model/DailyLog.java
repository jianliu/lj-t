package model;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-5
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class DailyLog {
    private int id = 1;
    private String sendDay;
    private String leader;
    private String task;
    private String sender;
    private int taskCosts;
    private int taskTotalCosts;
    private String taskStartDay;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendDay() {
        return sendDay;
    }

    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getTaskCosts() {
        return taskCosts;
    }

    public void setTaskCosts(int taskCosts) {
        this.taskCosts = taskCosts;
    }

    public int getTaskTotalCosts() {
        return taskTotalCosts;
    }

    public void setTaskTotalCosts(int taskTotalCosts) {
        this.taskTotalCosts = taskTotalCosts;
    }

    public String getTaskStartDay() {
        return taskStartDay;
    }

    public void setTaskStartDay(String taskStartDay) {
        this.taskStartDay = taskStartDay;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DailyLog() {

    }

    public DailyLog(String sendDay, String leader, String task, String sender, int taskCosts, int taskTotalCosts, String taskStartDay, String desc) {
        this.sendDay = sendDay;
        this.leader = leader;
        this.task = task;
        this.sender = sender;
        this.taskCosts = taskCosts;
        this.taskTotalCosts = taskTotalCosts;
        this.taskStartDay = taskStartDay;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.sendDay + "|-|" + this.leader + "|-|" + this.task + "|-|" +
                this.sender + "|-|" + String.valueOf(this.taskCosts) + "|-|" +
                String.valueOf(this.taskTotalCosts) + "|-|" + this.taskStartDay + "|-|" + this.desc;
    }

    public String toString(String sep){
        return this.sendDay + sep + this.leader + sep + this.task + sep +
                this.sender + sep + String.valueOf(this.taskCosts) + sep +
                String.valueOf(this.taskTotalCosts) + sep + this.taskStartDay + sep + this.desc+sep+"\n";
    }

    public String toStringWithHtml(){
        return "<table><tr><td>"+this.sendDay + "</td><td>" + this.leader + "</td><td>" + this.task + "</td><td>" +
                this.sender + "</td><td>" + String.valueOf(this.taskCosts) + "</td><td>" +
                String.valueOf(this.taskTotalCosts) + "</td><td>" + this.taskStartDay + "</td><td>" + this.desc+"</td></tr></table>"+"\n";
    }

    public static DailyLog wrapDailyLog(String s) {
        String[] arr = s.split("\\|-\\|", -1);
        if (arr.length != 8) {
            return (DailyLog) null;
        } else {
            return new DailyLog(arr[0], arr[1], arr[2], arr[3], Integer.valueOf(arr[4]), Integer.valueOf(arr[5]), arr[6], arr[7]);
        }
    }
}
