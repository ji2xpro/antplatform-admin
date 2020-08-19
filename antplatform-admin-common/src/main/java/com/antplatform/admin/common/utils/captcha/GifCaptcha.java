package com.antplatform.admin.common.utils.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>Gif验证码类</p>
 *
 * @author: maoyan
 * @date: 2020/7/16 14:58:40
 * @description:
 */
public class GifCaptcha extends Captcha {

    /**
     * 空构造函数
     */
    public GifCaptcha() {
    }

    /**
     * 设置验证码宽度，高度的构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     */
    public GifCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
        this.alphas();
    }

    /**
     * 设置验证码宽度，高度和长度的构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @param len    验证码长度
     */
    public GifCaptcha(int width, int height, int len) {
        this.width = width;
        this.height = height;
        this.len = len;
        this.alphas();
    }

    /**
     * 设置验证码宽度,高度,长度和字体的构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @param len    验证码长度
     * @param font   验证码字体
     */
    public GifCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.font = font;
    }

    /**
     * 给定一个输出流,输入图片
     *
     * @param os
     */
    @Override
    public void out(OutputStream os) {
        try {
            GifEncoder gifEncoder = new GifEncoder();
            // 生成字符
            gifEncoder.start(os);
            // 量化器取样间隔 (默认是10ms)
            gifEncoder.setQuality(180);
            // 帧延迟 (默认100)
            gifEncoder.setDelay(200);
            // 帧循环次数
            gifEncoder.setRepeat(0);
            char[] rands = this.textChar();
            Color[] fontColor = new Color[this.len];
            int i;
            for (i = 0; i < this.len; ++i) {
                fontColor[i] = new Color(20 + num(110), 20 + num(110), 20 + num(110));
            }
            for (i = 0; i < this.len; ++i) {
                BufferedImage frame = this.graphicsImage(fontColor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成随机验证码图片
     *
     * @param fontColor 随机字体颜色
     * @param strs      字符数组
     * @param alpha     透明度使用
     * @return
     */
    private BufferedImage graphicsImage(Color[] fontColor, char[] strs, int alpha) {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(this.width, this.height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // 利用指定颜色填充背景
        g2d.setColor(new Color(64, 158, 255));
        g2d.setStroke(new BasicStroke(1));
        g2d.fillRect(0, 0, this.width, this.height);
        int h = this.height - (this.height - this.font.getSize() >> 1);
        int w = this.width / this.len;
        g2d.setFont(this.font);
        for (int i = 0; i < this.len; ++i) {
            AlphaComposite ac3 = AlphaComposite.getInstance(3, this.getAlpha(alpha, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontColor[i]);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(num(this.width), num(this.height), 5 + num(10), 5 + num(10));
            g2d.drawString(String.valueOf(strs[i]), this.width - (this.len - i) * w + (w - this.font.getSize()) + 1,
                    h - 4);
        }
        g2d.dispose();
        return image;
    }

    /**
     * 获取透明度,从0到1,自动计算步长
     *
     * @param i
     * @param j
     * @return float 透明度
     */
    private float getAlpha(int i, int j) {
        int num = i + j;
        float r = 1.0F / this.len;
        float s = (this.len + 1) * r;
        return num > this.len ? num * r - s : num * r;
    }
}
