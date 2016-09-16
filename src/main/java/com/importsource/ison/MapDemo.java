package com.importsource.ison;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapDemo {

	public static void main(String[] args) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstName", "Anna");
		map.put("lastName", "Smith");

		Ison ison = new Ison();
		System.out.println(ison.toJson(map, "employees"));
	}

}
