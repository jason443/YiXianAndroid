package com.jason.yixianandroid.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/18.
 */

public class CommonParser {

    /**
     * 用来解析列表性的JSON数据
     * 如:
     * {"success":true,"fileList":[{"filename":"文件名1","fileSize":"文件大小1"},
     * {"filename":"文件名2","fileSize":"文件大小2"}]}
     *
     * @param result     网络返回来的JSON数据   比如:上面的整串数据
     * @param arrKey     列表的字段            比如:上面的fileList字段
     * @param clazz      需要解析成的Bean类型
     * @param <T>        需要解析成的Bean类型
     * @return
     */
    public static <T> List<T> parseForList(String result, String arrKey, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JSONObject rootJsonObject = null;
        try {
            rootJsonObject = new JSONObject(result);
            JSONArray rootJsonArray = rootJsonObject.getJSONArray(arrKey);
            Gson g = new Gson();
            for (int i = 0; i < rootJsonArray.length(); i++) {
                T t = g.fromJson(rootJsonArray.getJSONObject(i).toString(), clazz);
                list.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> T  parseForSingle(String result, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(result,clazz);
    }


}
