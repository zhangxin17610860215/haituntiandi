package haituntiandi.com.haituntiandi.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author yuehaotian
 */
public class JsonObjectUtils {
	/** JSON对象 */
	private JSONObject jsonObj = null;

	/**
	 * 设置JSON对象
	 * @param jsonObj 要设置成的json对象
	 */
	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	/**
	 * 构造方法
	 */
	public JsonObjectUtils() {}

	/**
	 * 构造方法
	 * @param jsonStr JSON串
	 */
	public JsonObjectUtils(String jsonStr) {
		try {
			jsonObj = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取jsonName对应的String对象
	 * @return String 根据name返回与其对应的value，当name不存在时，value的返回值为空字符串
	 */
	public String getString(String name) {
		String strValue = "";

		try {
			if(jsonObj.isNull(name)) {
				strValue = "";
			}else {
				strValue = jsonObj.getString(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strValue;
	}

	/**
	 * 获取所有name/value对的key迭代器
	 */
	public Iterator<String> getKeys() {
		Iterator<String> iterator = null;

		try {
			iterator = jsonObj.keys();
		} catch (Exception e) {

		}
		return iterator;
	}

	/**
	 * 获取该对象的json字符串表示
	 */
	public String toString() {
		String retString = "";
		try {
			retString = jsonObj.toString();
		} catch (Exception e) {

		}
		return retString;
	}
}
