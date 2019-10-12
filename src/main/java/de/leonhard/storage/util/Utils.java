package de.leonhard.storage.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "unchecked"})
public class Utils {

    public static Object getObjectFromMap(final Map<String, Object> map, final String key) {
        final String[] parts = key.split("\\.");
        return getObjectFromMap(map, parts, 0);
    }

    private static Object getObjectFromMap(final Map<String, Object> map, final String[] key, final int id) {
        if (id < key.length - 1) {
            if (map.get(key[id]) instanceof Map) {
                Map<String, Object> tempMap = (Map<String, Object>) map.get(key[id]);
                return getObjectFromMap(tempMap, key, id + 1);
            } else {
                return null;
            }
        } else {
            return map.get(key[id]);
        }
    }


    public static void insertKeyToMap(final Map<String, Object> map, final String key, final Object value) {
        final String[] parts = key.split("\\.");
        map.put(parts[0], insertKeyToMap(map, parts, value, 1));
    }

    private static Object insertKeyToMap(final Map<String, Object> map, final String[] key, final Object value, final int id) {
        if (id < key.length) {
            Map<String, Object> tempMap = new HashMap<>(map);
            Map<String, Object> childMap = map.containsKey(key[id]) ? (map.get(key[id]) instanceof Map ? (Map<String, Object>) map.get(key[id]) : new HashMap<>()) : new HashMap<>();
            tempMap.put(key[id], insertKeyToMap(childMap, key, value, id + 1));
            return tempMap;
        } else {
            return value;
        }
    }


    public static boolean contains(final Map<String, Object> map, String key) {
        String[] parts = key.split("\\.");
        return contains(map, parts, 0);
    }

    private static boolean contains(final Map<String, Object> map, String[] key, int id) {
        if (id < key.length - 1) {
            if (map.containsKey(key[id]) && map.get(key[id]) instanceof Map) {
                Map<String, Object> tempMap = (Map<String, Object>) map.get(key[id]);
                return contains(tempMap, key, id + 1);
            } else {
                return false;
            }
        } else {
            return map.containsKey(key[id]);
        }
    }


    public static void remove(final Map<String, Object> map, final String key) {
        if (contains(map, key)) {
            final String[] parts = key.split("\\.");
            remove(map, parts, 0);
        }
    }

    private static void remove(final Map<String, Object> map, final String[] key, final int id) {
        Map tempMap = new HashMap<>(map);
        for (int i = 0; i < key.length - (1 + id); i++) {
            if (tempMap.containsKey(key[i]) && tempMap.get(key[i]) instanceof Map) {
                tempMap = (Map) tempMap.get(key[i]);
            }
        }
        if (tempMap.keySet().size() <= 1) {
            map.remove(key[key.length - (1 + id)]);
            remove(map, key, id + 1);
        }
    }

    /* static Map insertKeyToMap(final String string) {
        final Map result = new HashMap();
        final String str = string.replace("{", "").replace("}", "");
        final String[] array = str.split(",");
        for (String s : array) {
            final String[] splitted = s.split("=");
            if (splitted.length != 2)
                continue;
            result.put(splitted[0].replace(" ", ""), splitted[1]);
        }
        return result;
    }*/


    // Method to create nested objects from String keys
    /*public static Map<String, Object> insertKeyToMap(final String string, final Object value, final Map object) {

        if (string.contains(".")) {
            final String[] parts = string.split("\\.");
            HashMap<String, Object> keyMap = new HashMap<>();

            int j = 0;
            for (int i = parts.length - 1; i > 0; i--) {

                final String key = getFirst(string, j);

                if (contains(key, object)) {
                    final Object obj = get(key, object);
                    if (obj instanceof Map) {
                        if (i == parts.length - 2) {
                            keyMap = (HashMap<String, Object>) deepMerge((Map) keyMap.clone(), (Map) obj);
                            if (keyMap.containsKey(parts[i + 1])) {
                                keyMap.remove(parts[i + 1]);
                                keyMap.put(parts[i + 1], value); // PUTTING THE
                                // VALUE
                            }
                        } else {
                            keyMap = (HashMap<String, Object>) deepMerge((Map) keyMap.clone(), (Map) obj);
                        }
                    } else {
                        keyMap.put(parts[i], obj);// NOW BUGFREE
                    }
                }
                if (i == parts.length - 1) {
                    keyMap.put(parts[parts.length - 1], value); // ADDED DIE
                    // VALUE ->
                    // BUGGFREI
                } else {

                    if (keyMap.containsKey(parts[i])) {
                        keyMap.remove(parts[i]);
                        keyMap.put(parts[i + 1], value);
                    }

                    HashMap<String, Object> preResult = new HashMap<>();
                    preResult.put(parts[i], keyMap);
                    keyMap = preResult;
                }
                j++;
            }
            // Merging
            final Map result = new HashMap();

            result.put(parts[0], keyMap);

            return deepMerge(object, result);
        }
        Map<String, Object> result = new HashMap<>();
        result.put(string, value);
        return result;
    }*/

    /*public static boolean contains(String string, final Map object) {
        if (string.contains(".")) {
            String[] parts = string.split("\\.");
            Map preResult = object;
            for (int i = 0; i < parts.length; i++) {
                if (!preResult.containsKey(parts[i])) {
                    return false;
                } else if (!(preResult.get(parts[i]) instanceof HashMap) && i != parts.length - 1) {
                    return false;
                } else if (preResult.containsKey(parts[i]) && preResult.get(parts[i]) instanceof HashMap) {
                    preResult = (HashMap) preResult.get(parts[i]);
                } else {
                    return preResult.containsKey(parts[i]) && i == parts.length - 1;
                }
            }
            return true;
        } else {
            return object.containsKey(string);
        }
    }*/


    /*public static Object get(final Map object, final String key) {
        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            Map preResult = (get(object, parts[0]) == null || (!(get(object, parts[0]) instanceof Map)) ? new HashMap()
                    : (HashMap) get(object, parts[0]));// WARNING RECURSION!
            for (int i = 1; i < parts.length; i++) {
                if (!(Objects.requireNonNull(preResult).get(parts[i]) instanceof HashMap) || i == parts.length - 1)
                    return preResult.get(parts[i]);
                preResult = (HashMap) preResult.get(parts[i]);
            }
        }
        return object.containsKey(key) ? object.get(key) : null;
    }*/

    /*public static Map<String, Object> remove(final Map<String, Object> map, final String key) {
        //
        // TODO Get ->Via method ->Remove key ->Add as new Map via put
        //
        final String[] parts = key.split("\\.");
        final String withoutLast = getFirst(key);
        final String first = parts[0];
        final String last = parts[parts.length - 1];

        // System.out.println(map);
        // System.out.println(withoutLast);

        final Map tmp = (Map) get(withoutLast, map); // Getter

        if (tmp == null)
            throw new IllegalStateException("Map mustn't be null");

        tmp.remove(last);

        Map<String, Object> preResult = insertKeyToMap(map, withoutLast, tmp);

        return deepMerge(map, preResult);
    }*/


    /*private static String getFirst(final String string) {
        final ArrayList<String> strings = new ArrayList<>(Arrays.asList(string.split("\\.")));
        final StringBuilder sb = new StringBuilder();

        final int max = strings.size() - 1;
        int i = 0;
        for (final String str : strings) {
            if (i < max) {
                i++;
                if (i != 1) {
                    sb.append(".");
                }
                sb.append(str);
            }
        }

        return sb.toString();
    }*/

    // Method to merge Maps
    /*private static Map<String, Object> deepMerge(Map original, Map newMap) {
        for (Object key : newMap.keySet()) {
            if (newMap.get(key) instanceof Map && original.get(key) instanceof Map) {
                Map originalChild = (Map) original.get(key);
                Map newChild = (Map) newMap.get(key);
                original.put(key, deepMerge(originalChild, newChild));
            } else {
                original.put(key, newMap.get(key));
            }
        }
        return original;
    }*/
}
