/**
 * Created by Lenovo on 25.11.2017.
 */
public class UnexpectedRequest extends Exception {
    String str;
    UnexpectedRequest(){}
    UnexpectedRequest(String string){
        str=string;
    }
    @Override
    public String toString(){
        return str;
    }
}
