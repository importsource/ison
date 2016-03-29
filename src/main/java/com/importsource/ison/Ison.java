package com.importsource.ison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现一个真正轻量级的json转换器
 * 暂不支持线程安全。
 * 
 * @author Hezf
 *
 *
 *
 *         Data is in name/value pairs Data is separated by commas Curly braces
 *         hold objects Square brackets hold arrays
 */
public class Ison {
	public static final String COMMA = ",", LEFT_CURLY_BRACES = "{", RIGHT_CURLY_BRACES = "}",
			LEFT_SQUARE_BRACKETS = "[", RIGHT_SQUARE_BRACKETS = "]", COLON = ":";
	
	protected StringBuilder sb;

	public String toJson(List<Map<String, Object>> list, String rootName) {
		return toJson1(list, rootName);
	}

	
	public String toJson(List<Map<String, Object>> list) {
		return toJson1(list, "data");
	}


	private String toJson1(List<Map<String, Object>> list, String rootName) {
		// "employees":[ {"firstName":"Anna", "lastName":"Smith"},
		// {"firstName":"Peter", "lastName":"Jones"}]
		StringBuilder sb = new StringBuilder();
		sb.append("\"" + rootName + "\"");
		appendColon(sb);
		appendLSB(sb);
		append(sb, list);
		appendRSB(sb);
		return sb.toString();
	}

	

	private void append(StringBuilder sb2, List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			appendLCB(sb2);
			Map<String, Object> map = list.get(i);
			Set<String> keys = map.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Object value = map.get(key);

				appendKey(sb2, key);

				appendColon(sb2);

				if (value instanceof String) {
					appendValue(sb2, value);
				}
				if(value instanceof List){
					appendLSB(sb2);
					append(sb2, (List<Map<String, Object>>)value);
					appendRSB(sb2);
				}

				if (iterator.hasNext()) {
					appendSeparator(sb2);
				}
			}
			appendRCB(sb2);
			if (i != list.size() - 1) {
				appendSeparator(sb2);
			}
		}
	}

	
	private void appendRSB(StringBuilder sb) {
		sb.append(RIGHT_SQUARE_BRACKETS);
	}

	private void appendLSB(StringBuilder sb) {
		sb.append(LEFT_SQUARE_BRACKETS);
	}
	
	private void appendColon(StringBuilder sb2) {
		sb2.append(COLON);
	}

	private void appendLCB(StringBuilder sb2) {
		sb2.append(LEFT_CURLY_BRACES);
	}

	private void appendRCB(StringBuilder sb2) {
		sb2.append(RIGHT_CURLY_BRACES);
	}

	private void appendSeparator(StringBuilder sb2) {
		sb2.append(COMMA);
	}

	private void appendValue(StringBuilder sb2, Object value) {
		sb2.append("\"");
		sb2.append(value);
		sb2.append("\"");
	}

	private void appendKey(StringBuilder sb2, String key) {
		sb2.append("\"");
		sb2.append(key);
		sb2.append("\"");
	}

	public static void main(String[] args) {
		Ison ison = new Ison();
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstName", "Anna");
		map.put("lastName", "Smith");
		
		users.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("firstName", "Peter");
		map1.put("lastName", "Jones");
		List<Map<String, Object>> users1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put("firstName", "Anna");
		map11.put("lastName", "Smith");
		users1.add(map11);
		
		map1.put("family", users1);
		users.add(map1);
		
		System.out.println(ison.toJson(users, "employees"));
		System.out.println(ison.toJson(users));
	}
}
