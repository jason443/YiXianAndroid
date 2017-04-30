package com.jason.yixianandroid.util;

import android.util.Log;

import com.google.gson.Gson;
import com.jason.yixianandroid.bean.ContactBean;
import com.jason.yixianandroid.bean.DrawBean;
import com.jason.yixianandroid.bean.LabelBean;
import com.jason.yixianandroid.bean.LoginBean;
import com.jason.yixianandroid.bean.MsgBean;
import com.jason.yixianandroid.bean.SaveChatBean;
import com.jason.yixianandroid.bean.UserBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/18.
 */

public class DataLoader {

    private static String TAG = DataLoader.class.getSimpleName();

    public static DrawBean getDrawData(String filePath) {
        String url = "/sdcard/wechaterData/HMI绘图/" + filePath;
        File urlFile = new File(url);
        DrawBean drawBean = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            return CommonParser.parseForSingle(sb.toString(),DrawBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawBean;
    }

    public static void addChats(String sender,String receiver,List<MsgBean> msg1Bean) {
        Gson gson = new Gson();
        SaveChatBean bean = new SaveChatBean(msg1Bean);
        String a = gson.toJson(bean);
        File file1 = new File("/sdcard/wechaterData/聊天记录/" + sender + "_" + receiver + ".txt");
        if (file1.exists()) { //如果文件存在
            try {
                FileWriter fw = new FileWriter(file1,false); //重新写入
                fw.write(a);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if(file1.createNewFile()) { //不在则创建
                    FileWriter fw = new FileWriter(file1,false);
                    fw.write(a);
                    fw.close();
                } else {
                    Log.d(TAG, "addChats: " + "无法创建文件！！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file2 = new File("/sdcard/wechaterData/聊天记录/"+receiver+"_"+sender+".txt");//接收方也要写入记录
        if (file2.exists()) {
            try {
                FileWriter fw = new FileWriter(file2,false); //重新写入
                fw.write(a);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if(file2.createNewFile()) {
                    FileWriter fw = new FileWriter(file2,false);
                    fw.write(a);
                    fw.close();
                } else {
                    Log.d(TAG, "addChats: " + "无法创建文件！！");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "addChats: 创建文件失败");
            }
        }
        Log.d(TAG, "addChats: " + a);
    }

    public static List<LoginBean> getLoginBeans() {
        List<LoginBean> loginBeans = new ArrayList<>(0);
        File urlFile = new File("/sdcard/wechaterData/用户列表.json");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, "getLoginBeans: " + sb.toString());
            loginBeans = CommonParser.parseForList(sb.toString(),"list",LoginBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG, "getLoginBeans: " + "UnsupportedEncodingException");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "getLoginBeans: " + "FileNotFoundException");
        } catch (IOException e) {
            Log.d(TAG, "getLoginBeans: " + "IOException");
            e.printStackTrace();
        }
        return loginBeans;
    }

    public static UserBean getUser(String userAccount) {
        String url = "/sdcard/wechaterData/" + userAccount + "/我.txt";
        File urlFile = new File(url);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, "getUser: " + sb.toString());
            return CommonParser.parseForSingle(sb.toString(),UserBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 获取好友列表（人）
    * */
    public static List<UserBean> getFriends(String userAccount) {
        List<UserBean> mBeans = getContacts(userAccount);
        List<UserBean> mFriendBean = new ArrayList<>();
        for (UserBean b : mBeans) {
            if (b.get类型().equals("人")) {
                mFriendBean.add(b);
            }
        }
        return mFriendBean;
    }

    /*
    * 获取设备的数量
    * */
    public static int getEquimentCount(String userAccount) {
        return getEquipments(userAccount).size();
    }

    /*
    * 获取设备列表
    * */
    public static List<UserBean> getEquipments(String userAccount) {
        List<UserBean> mBeans = getContacts(userAccount);
        List<UserBean> mEquipBeans = new ArrayList<>();
        for (UserBean b : mBeans) {
            if (b.get类型().equals("HMI设备")) {
                mEquipBeans.add(b);
            }
        }
        return mEquipBeans;
    }

    /*
    *
    * 获取好友列表（包含详细信息）*/
    public static ArrayList<UserBean> getContacts(String userAccount) {
        String listUrl = "/sdcard/wechaterData/" + userAccount + "/好友列表.txt";
        File urlFile = new File(listUrl);
        List<ContactBean> contacts = null;
        ArrayList<UserBean> returnData = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            contacts = CommonParser.parseForList(sb.toString(),"list",ContactBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contacts != null) {
            for (ContactBean contactBean : contacts) {
                returnData.add(getUser(contactBean.getAccount()));
            }
        }
        return returnData;
    }

    public static List<MsgBean> getMsgs(String sender,String receiver) {
        List<MsgBean> returnData = null;
        String url = "/sdcard/wechaterData/聊天记录/" + sender + "_" + receiver + ".txt";
        File urlFile = new File(url);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            returnData = CommonParser.parseForList(sb.toString(),"list",MsgBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnData;
    }

    /*
   * 获取好友列表（仅列表）
   * */
    public static List<ContactBean> getContactsList(String userAccount) {
        String listUrl = "/sdcard/wechaterData/" + userAccount + "/好友列表.txt";
        File urlFile = new File(listUrl);
        List<ContactBean> contacts = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            contacts = CommonParser.parseForList(sb.toString(),"list",ContactBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * @param sender
     * @param contactBeanList
     * @return 一组最新的msg
     */
    public static List<MsgBean> getAllLatestMsg(String sender,List<ContactBean> contactBeanList) {
        List<MsgBean> msg1BeanList = new ArrayList<>();
        for (ContactBean contactBean : contactBeanList) {
            MsgBean msgBean = getSingleLatestMsg(sender,contactBean.getAccount());
            if (msgBean != null) {
                msg1BeanList.add(msgBean);
            }
        }
        return msg1BeanList;
    }

    /**
     * @param sender
     * @param receiver
     * @return 最新的消息
     */
    public static MsgBean getSingleLatestMsg(String sender,String receiver) {
        List<MsgBean> msg1BeanList = getMsgs(sender,receiver);
        if (msg1BeanList != null && msg1BeanList.size()>0) {
            return msg1BeanList.get(msg1BeanList.size()-1);
        }
        return null;
    }

    /*
    * 获取标签列表
    * */
    public static List<LabelBean> getLabelList(String account) {
        String url = "/sdcard/wechaterData/" + account + "/label.txt";
        File urlFile = new File(url);
        List<LabelBean> returnData = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, "getLabelList: " + sb.toString());
            returnData = CommonParser.parseForList(sb.toString(),"list",LabelBean.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnData;
    }

}
