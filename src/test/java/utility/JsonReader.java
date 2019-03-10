package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.Iterator;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;

public class JsonReader{
	
	private static FileReader fileReader=null;
	private static JsonObject object = null;

	static {
		try {
			 fileReader=	new FileReader("./testData/testCasesData.json");
			 object = Json.parse(fileReader).asObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static JsonObject jsonReader(String key) {
		
			return object.get(key).asObject();
	}

public static Object[][] jsonObjectToObjectArray(JsonObject testData,String key) {
	
	JsonArray testDataArray=testData.get(key).asArray();
	int countMembers=0;
	for(JsonValue testDataSingleObject:testDataArray) {
		 countMembers=0;
		JsonObject	testDataJsonObject=testDataSingleObject.asObject();
		Iterator<Member> itr=testDataJsonObject.iterator();
		while(itr.hasNext()) {itr.next();countMembers++;}
	}
	
    Object[][] data= new Object[testDataArray.size()][countMembers];
	int rows=0;
	int cols;
	
		for(JsonValue testDataSingleObject:testDataArray){
		
		JsonObject	testDataJsonObject=testDataSingleObject.asObject();
		Iterator<Member> itr=testDataJsonObject.iterator();
		cols=0;
		while(itr.hasNext()) {
			String readData=itr.next().getValue().toString();
		data[rows][cols]=readData.replaceAll("^[\"']+|[\"']+$", "");
		cols++;
		}
		rows++;
	}
	
	return data;
}

}
