package com.phoenix.rat.events;


/**
 * Created by dahlia on 2/25/17.
 */
public class SmsMessage {
    private String message;
    private String phone;


    public SmsMessage(String phone, String message){
        this.message = message;
        this.phone = phone;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }



    public String toString(){
        return phone + ",_,_," + message;
    }


    public static SmsMessage fromString(String str){
        String[] vars = str.split(",_,_,");
        return new SmsMessage(vars[0], vars[1]);
    }
}
