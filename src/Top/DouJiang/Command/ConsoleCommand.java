package Top.DouJiang.Command;

import Top.DouJiang.Main.CallEventClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/12 0012.
 */
public class ConsoleCommand {
    private String[] args=null;
    private String[] Additional_Instruction=null;
    private int TypeId=-1;
    public ConsoleCommand(String[]  args,int TypeId){
        this.args=args;
        this.TypeId=TypeId;
        Additional_Instruction=new String[args.length-1];
    }
    public void CallConsoleCommand(){
        String Cmd=args[0];
        for(int i=1;i<args.length;i++){
            Additional_Instruction[i-1]=args[i];
        }
        ConsoleCommandClass ccc=new ConsoleCommandClass(Cmd,Additional_Instruction,TypeId);
        CallEventClass cec=new CallEventClass();

    }
}
