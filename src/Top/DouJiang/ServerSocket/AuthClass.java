package Top.DouJiang.ServerSocket;

/**
 * Created by NicoNicoNi on 2017/8/8 0008.
 */
public class AuthClass {
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private String Id=null;

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    private boolean isAuth=false;
    public AuthClass(String Id,boolean isAuth){
        this.Id=Id;
        this.isAuth=isAuth;
    }
    public AuthClass(){

    }
}
