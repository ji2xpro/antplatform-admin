package com.antplatform.admin.common.utils.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>png格式验证码</p>
 *
 * @author: maoyan
 * @date: 2020/7/16 17:33:50
 * @description:
 */
public class SpecCaptcha extends Captcha {

    /**
     * 空构造函数
     */
    public SpecCaptcha() {
    }

    /**
     * 设置验证码宽度，高度的构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     */
    public SpecCaptcha(int width, int height) {
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
    public SpecCaptcha(int width, int height, int len) {
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
    public SpecCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.font = font;
    }

    /**
     * 生成验证码
     *
     * @param out
     */
    @Override
    public void out(OutputStream out) {
        this.graphicsImage(out);
    }

    /**
     * 生成随机验证码图片
     *
     * @param out 输出流
     * @return
     */
    private boolean graphicsImage(OutputStream out) {
        boolean ok = false;
        try {
            char[] strs = this.textChar();
            BufferedImage e = new BufferedImage(this.width, this.height, 1);
            Graphics2D g = (Graphics2D) e.getGraphics();
            int len = strs.length;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.width, this.height);
            Color color;
            // 随机画干扰的蛋蛋
            for (int i = 0; i < 15; ++i) {
                color = this.color(150, 250);
                g.setColor(color);
                // 画蛋蛋，有蛋的生活才精彩
                g.drawOval(num(this.width), num(this.height), 5 + num(10), 5 + num(10));
                color = null;
            }
            g.setFont(this.font);
            int h = this.height - (this.height - this.font.getSize() >> 1);
            int w = this.width / len;
            int size = w - this.font.getSize() + 1;
            for (int i = 0; i < len; ++i) {
                // 指定透明度
                AlphaComposite ac3 = AlphaComposite.getInstance(3, 0.7F);
                g.setComposite(ac3);
                // 对每个字符都用随机颜色
                color = new Color(20 + num(110), 20 + num(110), 20 + num(110));
                g.setColor(color);
                g.drawString(String.valueOf(strs[i]), this.width - (len - i) * w + size, h - 4);
                color = null;
                ac3 = null;
            }
            ImageIO.write(e, "png", out);
            out.flush();
            ok = true;
        } catch (IOException arg20) {
            ok = false;
        } finally {
            try {
                out.close();
            } catch (IOException arg19) {
                arg19.printStackTrace();
            }
        }
        return ok;
    }
}
