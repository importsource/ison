# ison
一个只有100行的超轻量级的json序列化实现。小而美才是王道。我们只支持string等primitive类型和Map<String, Object>。因为这已经能够描述世界上大部分情况了。

###只需要两行代码就序列化完毕，如下：
```java

Ison ison = new Ison();
System.out.println(ison.toJson(users, "employees"));
System.out.println(ison.toJson(users));

```

###初始化数据：
```java

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

```
###序列化结果：
```json

{
    "employees": [
        {
            "lastName": "Smith",
            "firstName": "Anna"
        },
        {
            "lastName": "Jones",
            "family": [
                {
                    "lastName": "Smith",
                    "firstName": "Anna"
                }
            ],
            "firstName": "Peter"
        }
    ]
}

```

| 实现jar | 总耗时 | 平均耗时 | 第一次 | 第二次 | 第三次 |第四次 |第五次 |第六次 |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- |---- |---- |---- |
| gson |  |   |  2.201|  1.988| 0.297 | 2.035 | 3.447|  1.670 |
| ison |  |   | 0.278 | 0.281 | 1.682 | 1.821 | 1.673| 1.697  |
