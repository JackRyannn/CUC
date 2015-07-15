package com.digitalcuc;

/**
 * Created by pro on 15/7/10.
 */
public class Single_Account {
    private static Single_Account account = null;
    private static String account_name = "JackRyan";
    private static String account_password = "1352";


    public static Single_Account getAccount(){
        if(account == null){
            synchronized (Single_Account.class){
                if(account == null){
                    account = new Single_Account();
                }
            }
        }
        return account;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }
}
