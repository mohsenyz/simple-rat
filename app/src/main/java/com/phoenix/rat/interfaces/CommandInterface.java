package com.phoenix.rat.interfaces;

import android.content.Context;

import com.phoenix.rat.helper.AsyncHttp;
import com.phoenix.rat.helper.Command;
import com.phoenix.rat.helper.TinyDB;

/**
 * Created by mphj on 7/17/17.
 */
public abstract class CommandInterface extends Thread{
    private Command command;
    private Context context;
    public CommandInterface(Command command){
        this.command = command;
    }
    public void execute(Context context){
        this.context = context;
        start();
    }

    public void success(){
        new TinyDB(context).putBoolean(String.valueOf(command.getId()), true);
    }

    @Override
    public abstract void run();
}
