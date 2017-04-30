package com.jason.yixianandroid.bean;

/**
 * Created by jason on 2017/3/28.
 */

public class DrawBean {


    /**
     * 颜色 : 35655
     */

    private 设置背景颜色Bean 设置背景颜色;
    /**
     * x : 0
     * y : 0
     * name : logo.jpg
     */

    private 绘制图片Bean 绘制图片;
    /**
     * x : 200
     * y : 200
     * r : 150
     * w : 3
     * 颜色 : 34525
     */

    private 绘制圆形Bean 绘制圆形;
    /**
     * x1 : 20
     * y1 : 20
     * x2 : 300
     * y2 : 600
     * w : 8
     * 颜色 : 56729
     */

    private 绘制直线Bean 绘制直线;

    public 设置背景颜色Bean get设置背景颜色() {
        return 设置背景颜色;
    }

    public void set设置背景颜色(设置背景颜色Bean 设置背景颜色) {
        this.设置背景颜色 = 设置背景颜色;
    }

    public 绘制图片Bean get绘制图片() {
        return 绘制图片;
    }

    public void set绘制图片(绘制图片Bean 绘制图片) {
        this.绘制图片 = 绘制图片;
    }

    public 绘制圆形Bean get绘制圆形() {
        return 绘制圆形;
    }

    public void set绘制圆形(绘制圆形Bean 绘制圆形) {
        this.绘制圆形 = 绘制圆形;
    }

    public 绘制直线Bean get绘制直线() {
        return 绘制直线;
    }

    public void set绘制直线(绘制直线Bean 绘制直线) {
        this.绘制直线 = 绘制直线;
    }

    public static class 设置背景颜色Bean {
        private String 颜色;

        public String get颜色() {
            return 颜色;
        }

        public void set颜色(String 颜色) {
            this.颜色 = 颜色;
        }
    }

    public static class 绘制图片Bean {
        private int x;
        private int y;
        private String name;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class 绘制圆形Bean {
        private int x;
        private int y;
        private int r;
        private int w;
        private String 颜色;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public String get颜色() {
            return 颜色;
        }

        public void set颜色(String 颜色) {
            this.颜色 = 颜色;
        }
    }

    public static class 绘制直线Bean {
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private int w;
        private String 颜色;

        public int getX1() {
            return x1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getX2() {
            return x2;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public int getY2() {
            return y2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public String get颜色() {
            return 颜色;
        }

        public void set颜色(String 颜色) {
            this.颜色 = 颜色;
        }
    }
}
