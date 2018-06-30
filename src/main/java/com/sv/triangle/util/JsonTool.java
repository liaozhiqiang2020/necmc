package com.sv.triangle.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * 通用多层json递归解析。在没有Object对象，或是极度复杂的多级嵌套json，情况下以类的方式，直接获取结果。
 * 支持String、Map、ArrayList、ArrayMap四种返回对象的数据获取
 * 使用方式：根据json层级关系直接使用: 基节点.子节点.孙节点
 *
 * @author Kang Kai
 */
public class JsonTool {

    private static ObjectMapper mapper = new ObjectMapper();
    private int i = 0;

    /**
     * 复杂嵌套Map转Json
     */
    public static String getJsonByObject(Object obj) {
        String str = "";
        try {
            str = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("###[Error] getObjectToJson() " + e.getMessage());
        }
        return str;
    }

    /**
     * 复杂嵌套Json层级展示
     */
    public static Object viewJsonTree(Object m) {
        if (m == null) {
            System.out.println("over...");
            return false;
        }

        try {
            Map mp = null;
            List ls = null;
            if (m instanceof Map || m instanceof LinkedHashMap) {
                mp = (LinkedHashMap) m;
                for (Iterator ite = mp.entrySet().iterator(); ite.hasNext(); ) {
                    Map.Entry e = (Map.Entry) ite.next();

                    if (e.getValue() instanceof String) {
                        System.out.println("[String]" + e.getKey() + " | " + e.getValue());
                    } else if (e.getValue() instanceof LinkedHashMap) {
                        System.out.println("{Map}" + e.getKey() + " | " + e.getValue());
                        viewJsonTree((LinkedHashMap) e.getValue());
                    } else if (e.getValue() instanceof ArrayList) {
                        System.out.println("[Array]" + e.getKey() + " | " + e.getValue());
                        viewJsonTree((ArrayList) e.getValue());
                    }
                }
            }
            if (m instanceof List || m instanceof ArrayList) {
                ls = (ArrayList) m;
                for (int i = 0; i < ls.size(); i++) {
                    if (ls.get(i) instanceof LinkedHashMap) {
                        viewJsonTree((LinkedHashMap) ls.get(i));
                    } else if (ls.get(i) instanceof ArrayList) {
                        viewJsonTree((ArrayList) ls.get(i));
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("###[Error] viewJsonTree() " + e.getMessage());
        }
        return null;
    }

    /**
     * 复杂嵌套Json获取Object数据
     */
    public Object getObjectByJson(String jsonStr, String argsPath, TypeEnum argsType) {
        if (argsPath == null || argsPath.equals("") || argsType == null) {
            return null;
        }

        Object obj = null;
        try {
            Map maps = mapper.readValue(jsonStr, Map.class);
            //多层获取
            if (argsPath.indexOf(".") >= 0) {
                //类型自适应
                obj = getObject(maps, argsPath, argsType);
            } else { //第一层获取
                if (argsType == TypeEnum.STRING) {
                    obj = maps.get(argsPath).toString();
                } else if (argsType == TypeEnum.MAP) {
                    obj = (Map) maps.get(argsPath);
                } else if (argsType == TypeEnum.ARRAY_LIST) {
                    obj = (List) maps.get(argsPath);
                } else if (argsType == TypeEnum.ARRAY_MAP) {
                    obj = (List<Map>) maps.get(argsPath);
                }
            }
        } catch (Exception e) {
            System.out.println("###[Error] getObjectByJson() " + e.getMessage());
        }
        i = 0;
        return obj;
    }

    //递归获取object
    private Object getObject(Object m, String key, TypeEnum type) {
        if (m == null) {
            return null;
        }
        Object o = null;
        Map mp = null;
        List ls = null;
        try {
            //对象层级递归遍历解析
            if (m instanceof Map || m instanceof LinkedHashMap) {
                mp = (LinkedHashMap) m;
                for (Iterator ite = mp.entrySet().iterator(); ite.hasNext(); ) {
                    Map.Entry e = (Map.Entry) ite.next();

                    if (i < key.split("\\.").length && e.getKey().equals(key.split("\\.")[i])) {
                        i++;
                        if (e.getValue() instanceof String) {
                            if (i == key.split("\\.").length) {
                                o = e.getValue();
                                return o;
                            }
                        } else if (e.getValue() instanceof LinkedHashMap) {
                            if (i == key.split("\\.").length) {
                                if (type == TypeEnum.MAP) {
                                    o = (LinkedHashMap) e.getValue();
                                    return o;
                                }
                            } else {
                                o = getObject((LinkedHashMap) e.getValue(), key, type);
                            }
                            return o;
                        } else if (e.getValue() instanceof ArrayList) {
                            if (i == key.split("\\.").length) {
                                if (type == TypeEnum.ARRAY_LIST) {
                                    o = (ArrayList) e.getValue();
                                    return o;
                                }
                                if (type == TypeEnum.ARRAY_MAP) {
                                    o = (ArrayList<Map>) e.getValue();
                                    return o;
                                }
                            } else {
                                o = getObject((ArrayList) e.getValue(), key, type);
                            }
                            return o;
                        }
                    }
                }
            }
            //数组层级递归遍历解析
            if (m instanceof List || m instanceof ArrayList) {
                ls = (ArrayList) m;
                for (int i = 0; i < ls.size(); i++) {
                    if (ls.get(i) instanceof LinkedHashMap) {
                        if (i == key.split("\\.").length) {
                            if (type == TypeEnum.MAP) {
                                o = (LinkedHashMap) ls.get(i);
                                return o;
                            }
                        } else {
                            o = getObject((LinkedHashMap) ls.get(i), key, type);
                        }
                        return o;
                    } else if (ls.get(i) instanceof ArrayList) {
                        if (i == key.split("\\.").length) {
                            if (type == TypeEnum.ARRAY_LIST) {
                                o = (ArrayList) ls.get(i);
                                return o;
                            }
                            if (type == TypeEnum.ARRAY_MAP) {
                                o = (ArrayList<Map>) ls.get(i);
                                return o;
                            }
                        } else {
                            o = getObject((ArrayList) ls.get(i), key, type);
                        }
                        return o;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("###[Error] getObject() " + e.getMessage());
        }

        return o;
    }


    /*
     * Json数据解析返回数据类型枚举
     */
    public enum TypeEnum {
        /**
         * 单纯的键值对，通过key获取valus
         */
        STRING,
        /**
         * 通过key获取到Map对象
         */
        MAP,
        /**
         * 通过key获取到ArrayList数组
         */
        ARRAY_LIST,
        /**
         * 通过key获取到ArrayMap数组对象
         */
        ARRAY_MAP;
    }

}
