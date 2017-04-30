package com.jason.yixianandroid.adapter;

import android.widget.BaseAdapter;

import com.jason.yixianandroid.util.SortUtil;

import java.util.ArrayList;

/**
 * Created by jason on 2017/4/25.
 */

public abstract class BaseSortByLetterAdapter<T> extends BaseAdapter implements android.widget.SectionIndexer {

    protected String[] sections;
    protected ArrayList<T> datas;

    public BaseSortByLetterAdapter(ArrayList<T> datas) {
        sections = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
        this.datas = datas;
    }


    @Override
    public String[] getSections() {

        return sections;
    }

    //需要进行排序的字符串
    protected abstract String getSortString(T bean);

    //通过section位置，获取首个position位置
    @Override
    public int getPositionForSection(int sectionIndex) {
        String section = sections[sectionIndex];
        //todo ListView的数据要按照字母顺序排列
        for (int i = 0; i < getCount(); i++) {
            T bean = datas.get(i);
            String headerLetter = SortUtil.getLetter(getSortString(bean));

            if (String.valueOf(headerLetter.charAt(0)).equalsIgnoreCase(section)) {
                return i;
            } else if (sectionIndex == 0) {
                return 0;
            }
        }
        return -1;
    }

    //通过位置获取sectionIndex位置
    @Override
    public int getSectionForPosition(int position) {
        T bean = datas.get(position);
        String name = getSortString(bean);
        String letter = SortUtil.getLetter(name);
        String header = String.valueOf(letter.charAt(0));
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].equalsIgnoreCase(header)) {
                return i;
            }
        }
        return 0;
    }


    public int getSectionIndex(String section) {
        for (int i = 0; i < sections.length; i++) {
            if (section.equalsIgnoreCase(sections[i])) {
                return i;
            }
        }
        return 0;
    }

}
