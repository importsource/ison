package com.importsource.ison;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 实现一个真正轻量级的json转换器。
 * <p>
 * 
 * @author Hezf
 */
public class Ison {
	public static final String COMMA = ",", LEFT_CURLY_BRACES = "{", RIGHT_CURLY_BRACES = "}",
			LEFT_SQUARE_BRACKETS = "[", RIGHT_SQUARE_BRACKETS = "]", COLON = ":";

	/**
	 * 支持线程安全append
	 */
	protected Appendable sb;
	
	protected boolean safe=false;
	
	public Ison(){
		sb = new StringBuilder();
	}
	
	public Ison(boolean safe){
		this.safe=safe;
		sb = new StringBuffer();
	}
	
	

	
	
	/**
	 * 把对象转换成json,根节点默认为data
	 * @param obj 转换对象
	 * @return String 转换结果输出
	 * @throws IOException 
	 */
	public String toJson(Object obj)  {
		try {
			return toJson1(obj,"data");
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	/**
	 * 把对象转换成json
	 * 
	 * @param obj
	 *            转换对象
	 * @param rootName
	 *            根节点名称
	 * @return String 转换结果输出
	 * @throws IOException 
	 */
	public String toJson(Object obj,String rootName)  {
		try {
			return toJson1(obj,rootName);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	/**
	 * 把list转换成json
	 * 
	 * @param list
	 *            转换对象
	 * @param rootName
	 *            根节点名称
	 * @return String 转换结果输出
	 * @throws IOException 
	 */
	private String toJsonList(List<Map<String, Object>> list, String rootName) throws IOException {
		clear();
		return toJsonList1(list, rootName);
	}

	

	/**
	 * 把list转换成json,根节点默认为data
	 * 
	 * @param list
	 *            转换对象
	 * @return String 转换结果输出
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private String toJsonList(List<Map<String, Object>> list) throws IOException {
		clear();
		return toJsonList1(list, "data");
	}

	private String toJson1(Object obj,String rootName) throws IOException {
		if(obj instanceof List){
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> obj2 = (List<Map<String, Object>>)obj;
			return toJsonList(obj2, rootName);
		}else if(primitive(obj) ){
			return toJsonPrimitive(obj,rootName);
		}else if(obj instanceof Map){
			return toJsonMap(obj,rootName);
		} else{
			throw new IllegalArgumentException("only List<Map<String, Object>> or Map type supported!");
		}
	}
	

	private String toJsonMap(Object obj, String rootName)throws IOException {
		/**
		 * {
		 * "root": {
                "firstName": "Anna",
                "lastName": "Smith"
            }
		 * }
		 */
		clear();
		appendLCB();
		appendRoot(rootName);
		appendColon();
		appendLCB();
		
		
		Map<Object, Object> tmpMap=(Map<Object, Object>)obj;
		for (Iterator iter = tmpMap.keySet().iterator(); iter.hasNext();) {
			Object key =  iter.next();
			Object value = tmpMap.get(key);
			appendKey(key.toString());
			appendColon();
			appendValue(value.toString());
			if(iter.hasNext()){
				appendSeparator();
			}
		}
		
		appendRCB();
		appendRCB();
		return sb.toString();
	}

	private String toJsonPrimitive(Object obj,String rootName) throws IOException {
		clear();
		appendLCB();
		appendKey(rootName);
		appendColon();
		appendValue(obj.toString());
		appendRCB();
		return sb.toString();
	}

	private String toJsonList1(List<Map<String, Object>> list, String rootName) throws IOException {
		// "employees":[ {"firstName":"Anna", "lastName":"Smith"},
		// {"firstName":"Peter", "lastName":"Jones"}]
		
		appendLCB();
		appendRoot(rootName);
		appendColon();
		appendLSB();
		append(list);
		appendRSB();
		appendRCB();
		return sb.toString();
	}

	private void appendRoot(String rootName) throws IOException {
		sb.append("\"" + rootName + "\"");
	}

	
	private void append(List<Map<String, Object>> list) throws IOException {
		for (int i = 0; i < list.size(); i++) {
			appendLCB();
			Map<String, Object> map = list.get(i);
			Set<String> keys = map.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Object value = map.get(key);

				appendKey(key);

				appendColon();
				
				if (primitive(value)) {
					appendValue(value.toString());
				} else if(instanceOfByteArray(value)){
					try {
						appendValue(new String((byte[])value, "GB2312"));
					} catch (UnsupportedEncodingException e) {
						throw new IllegalArgumentException("only List<Map<String, Object>> or Map type supported!");
					}
				}else if (value instanceof List) {
					appendLSB();
					try {
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> value2 = (List<Map<String, Object>>) value;
						append(value2);
					} catch (Exception e) {
						throw new IllegalArgumentException("only List<Map<String, Object>> or Map type supported!");
					}
					appendRSB();
				} else {
					throw new IllegalArgumentException("only List<Map<String, Object>> or Map type supported!");
				}

				if (iterator.hasNext()) {
					appendSeparator();
				}
			}
			appendRCB();
			if (i != list.size() - 1) {
				appendSeparator();
			}
		}
	}

	private boolean primitive(Object value) {
		return value instanceof String || value instanceof Integer || value instanceof Double || value instanceof Float
				|| value instanceof Long || value instanceof Boolean || value instanceof Date
				|| value instanceof BigDecimal;
	}

	private boolean instanceOfByteArray(Object value){
	   return  value instanceof byte[];
	}

	private void appendRSB() throws IOException {
		sb.append(RIGHT_SQUARE_BRACKETS);
	}

	private void appendLSB() throws IOException {
		sb.append(LEFT_SQUARE_BRACKETS);
	}

	private void appendColon() throws IOException {
		sb.append(COLON);
	}

	private void appendLCB() throws IOException {
		sb.append(LEFT_CURLY_BRACES);
	}

	private void appendRCB() throws IOException {
		sb.append(RIGHT_CURLY_BRACES);
	}

	private void appendSeparator() throws IOException {
		sb.append(COMMA);
	}

	private void appendValue(Object value) throws IOException {
		sb.append("\"");
		sb.append((CharSequence) value);
		sb.append("\"");
	}

	private void appendKey(String key) throws IOException {
		sb.append("\"");
		sb.append(key);
		sb.append("\"");
	}
	
	private void clear() {
		if(safe){
			sb=new StringBuffer();
		}else{
			sb=new StringBuilder();
		}
		
	}

}
