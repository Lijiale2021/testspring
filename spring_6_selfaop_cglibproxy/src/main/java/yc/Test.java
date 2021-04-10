package yc;


import yc.biz.StudentBizimpl;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        StudentBizimpl sbm=new StudentBizimpl();

        LogAspectcglib lac=new LogAspectcglib(sbm);

        Object o=lac.createProxy();


        if (o instanceof StudentBizimpl){
            StudentBizimpl sb=(StudentBizimpl) o;
            sb.add("张三");
        }
    }
}
