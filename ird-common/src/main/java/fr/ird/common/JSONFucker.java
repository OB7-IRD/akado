package fr.ird.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe utilitaire permettant la conversion d'une Map en objet JSON et
 * inversement.
 *
 * @see {@link https://gist.github.com/sheharyarn/cba56ff154de2cc62fc5}
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.1
 * @date 26 mars 2015
 *
 */
public class JSONFucker {
    private static final Logger log = LogManager.getLogger(JSONFucker.class);
    // HashMap > Map
    // ArrayList > List

    /**
     * Convert an object to a JSON object or JSON array.
     *
     * @param object l'objet à convertir
     * @return le JSON ou l'objet d'entrée
     * @throws JSONException
     */
    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof HashMap) {
            JSONObject json = new JSONObject();
            HashMap map = (HashMap) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }

    /**
     * Convert a MAP to a JSON object
     *
     * @param object l'objet à convertir
     * @return le JSON
     */
    public static JSONObject toJSON(Map object) {
        JSONObject o = null;
        try {
            o = (JSONObject) toJSON((Object) object);
        } catch (JSONException ex) {
            log.error(ex.getLocalizedMessage());
        }
        return o;
    }

    /**
     * Returns a string representation of the object.
     *
     * @param object
     * @return the string or null if the object is null
     */
    public static String stringify(JSONObject object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    /**
     * Test if the object is empty.
     *
     * @param object to test
     * @return true if the object is empty
     */
    public static boolean isEmptyObject(JSONObject object) {
        return object.names() == null;
    }

    public static HashMap<String, Object> getMap(JSONObject object, String key) throws JSONException {
        return toMap(object.getJSONObject(key));
    }

    public static ArrayList getList(JSONObject object, String key) throws JSONException {
        return toList(object.getJSONArray(key));
    }

    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    /**
     * Convert a JSON array to a {@link List}.
     *
     * @param array to convert
     * @return the list
     * @throws JSONException
     */
    public static ArrayList toList(JSONArray array) throws JSONException {
        ArrayList list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    /**
     * Convert a JSON object to a {@link Map} or a {@link List}.
     *
     * @param json to convert
     * @return the object converted or the object passed in parameter
     * @throws JSONException
     */
    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
}
