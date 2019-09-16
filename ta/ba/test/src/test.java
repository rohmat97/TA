
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

public class test {
    public static void main(String args[]) {
        String s="{  \n" +
                "    \"employee\": {  \n" +
                "        \"employee1\":       test,   \n" +
                "        \"employee2\":      {},   \n" +
                "        \"employee3\":    {}  \n" +
                "    }  \n" +
                "}  ";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(s);
        }catch (JSONException err){
        }
        int pageName = 0;
        try {
            pageName = jsonObject.getJSONObject("employee").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.print(pageName);
    }
}