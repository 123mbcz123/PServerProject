package Top.DouJiang.Command;

import java.util.Map;

/**
 * Created by NicoNicoNi on 2017/8/12 0012.
 */
public class ConsoleCommandClass {
    private String Cmd=null;
    private String[] Additional_Instruction=null;
    private int TypeId=-1;
    public String getCmd(){
        return Cmd;
    }
    public int getTypeId() {
        return TypeId;
    }
    public String[] getAdditional_Instruction(){
        return Additional_Instruction;
    }

    public ConsoleCommandClass(String Cmd,String[] Additional_Instruction,int TypeId){
        this.TypeId=TypeId;
        this.Cmd=Cmd;
        this.Additional_Instruction=Additional_Instruction;
    }
}
