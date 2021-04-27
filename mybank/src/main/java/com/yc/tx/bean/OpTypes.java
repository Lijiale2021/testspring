package com.yc.tx.bean;

public enum OpTypes {
    deposite ("deposite",1),withdraw("withdraw",2),transfer("transfer",3);
    //枚举中的成员变量
    //枚举对象中的成员变量
    private String name;
    private int index;

    //构造方法
    private OpTypes(String name,int index){
        this.name=name;
        this.index=index;
    }

    //覆盖方法

    @Override
    public String toString() {
        return "OpTypes{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }

    public String getName(){
        return this.name;
    }
}
