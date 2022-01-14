package backend;

import java.util.List;

public class Result {
    public Object data;
    public String message;
    public Result(Object data,String message){
        this.data = data;
        this.message = message;
    }
    public static Result SetOk(Object data){
        return new Result(data,"ok");
    }
    public static Result SetError(String err){
        return new Result(null,err);
    }
}
