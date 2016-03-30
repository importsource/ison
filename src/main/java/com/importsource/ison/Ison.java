package com.importsource.ison;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现一个真正轻量级的json转换器。
 * <p>
 * @author Hezf
 */
public class Ison {
	public static final String COMMA = ",", LEFT_CURLY_BRACES = "{", RIGHT_CURLY_BRACES = "}",
			LEFT_SQUARE_BRACKETS = "[", RIGHT_SQUARE_BRACKETS = "]", COLON = ":";

	protected StringBuilder sb;

	/**
	 * 把list转换成json
	 * @param list 转换对象
	 * @param rootName 根节点名称
	 * @return String 转换结果输出
	 */
	public String toJson(List<Map<String, Object>> list, String rootName) {
		return toJson1(list, rootName);
	}

	/**
	 * 把list转换成json,根节点默认为data
	 * @param list 转换对象
	 * @return String 转换结果输出
	 */
	public String toJson(List<Map<String, Object>> list) {
		return toJson1(list, "data");
	}

	
	
	
	private String toJson1(List<Map<String, Object>> list, String rootName) {
		// "employees":[ {"firstName":"Anna", "lastName":"Smith"},
		// {"firstName":"Peter", "lastName":"Jones"}]
		sb = new StringBuilder();
		appendLCB();
		sb.append("\"" + rootName + "\"");
		appendColon();
		appendLSB();
		append(list);
		appendRSB();
		appendRCB();
		return sb.toString();
	}

	private void append(List<Map<String, Object>> list) {
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
				} else if (value instanceof List) {
					appendLSB();
					try {
						append((List<Map<String, Object>>) value);
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
		return value instanceof String || value instanceof Integer;
	}

	private void appendRSB() {
		sb.append(RIGHT_SQUARE_BRACKETS);
	}

	private void appendLSB() {
		sb.append(LEFT_SQUARE_BRACKETS);
	}

	private void appendColon() {
		sb.append(COLON);
	}

	private void appendLCB() {
		sb.append(LEFT_CURLY_BRACES);
	}

	private void appendRCB() {
		sb.append(RIGHT_CURLY_BRACES);
	}

	private void appendSeparator() {
		sb.append(COMMA);
	}

	private void appendValue(Object value) {
		sb.append("\"");
		sb.append(value);
		sb.append("\"");
	}

	private void appendKey(String key) {
		sb.append("\"");
		sb.append(key);
		sb.append("\"");
	}

}
