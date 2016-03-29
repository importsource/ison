package com.importsource.ison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsonDemo {

	public static void main(String[] args) {

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

		Ison ison = new Ison();
		System.out.println(ison.toJson(users, "employees"));
		System.out.println(ison.toJson(users));
	}

}
