package com.jason.yixianandroid.util;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jason on 2017/4/25.
 */

public class SortUtil {

    public static String getLetter(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            String[] str = PinyinHelper.toHanyuPinyinStringArray(c);
            if (str != null && str.length >= 1) {
                sb.append(str[0].charAt(0));
            } else {
                sb.append(c);
            }

        }
        return sb.toString();
    }

    /**
     * 按照字母排序
     *
     * @param list
     * @return
     */
    public static <T extends ISort> void sortByLetter(ArrayList<T> list) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                String l = getLetter(lhs.getSortName());
                String r = getLetter(rhs.getSortName());
                int minLength = Math.min(l.length(), r.length());
                int result = 0;
                for (int i = 0; i < minLength; i++) {
                    if (l.charAt(i) < r.charAt(i)) {
                        result = -1;
                        break;
                    } else if (l.charAt(i) > r.charAt(i)) {
                        result = 1;
                        break;
                    } else {
                        result = 0;
                        continue;
                    }

                }

                if (result == 0) {
                    return l.length() > r.length() ? 1 : -1;
                }
                return result;
            }
        });
    }

}
