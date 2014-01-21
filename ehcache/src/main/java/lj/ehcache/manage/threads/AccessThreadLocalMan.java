package lj.ehcache.manage.threads;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-12
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class AccessThreadLocalMan {

    public void access() {
        Student student = (Student) ThreadLocalDemo.getStudentLocal().get();
        System.out.println("thread======"+Thread.currentThread().getName() + " is running!" + student.getAge());
    }

    public void access(Object o) {

    }

}
