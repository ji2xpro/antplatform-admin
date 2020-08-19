package com.antplatform.admin.common.utils.captcha;

import com.antplatform.admin.common.utils.Randoms;
import lombok.Data;

import java.awt.*;
import java.io.OutputStream;

/**
 * <p>验证码抽象类,暂时不支持中文</p>
 *
 * @author: maoyan
 * @date: 2020/7/16 15:30:18
 * @description:
 */
@Data
public abstract class Captcha extends Randoms {

    /**
     * 字体
     */
    protected Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28);

    /**
     * 验证码随机字符长度
     */
    protected int len = 5;

    /**
     * 验证码显示宽度
     */
    protected int width = 150;

    /**
     * 验证码显示高度
     */
    protected int height = 40;

    /**
     * 随机字符串
     */
    private String chars = null;

    /**
     * 生成随机字符数组
     *
     * @return 字符数组
     */
    protected char[] alphas() {
        char[] cs = new char[this.len];
        for (int i = 0; i < this.len; ++i) {
            cs[i] = alpha();
        }
        this.chars = new String(cs);
        return cs;
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return Color 随机颜色
     */
    protected Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码输出,抽象方法，由子类实现
     *
     * @param arg0 输出流
     */
    public abstract void out(OutputStream arg0);

    /**
     * 获取随机字符串
     *
     * @return
     */
    public String text() {
        return this.chars;
    }

    /**
     * 获取随机字符数组
     * @return
     */
    public char[] textChar() {
        return this.chars.toCharArray();
    }
}
