package com.relcache.core.pocs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TextToPic {

    public static void main(String[] args) throws IOException {
        //String path = encodeLarge();
        //String str = "arunavasarunavas";
        String str = Files.readAllLines(Paths.get("/Users/arunavasaha/personal/large.txt")).stream().collect(Collectors.joining("\n"));
        int by4 = str.length() / 4;
        //int sq = Long.valueOf(Math.round(Math.sqrt(by4))).intValue();
        int h = 1, w = by4;
        if (by4 > 65500) {
            w = 65500;
            h = by4 / w + 1;
        }
        /*if (sq * sq != by4) {
            w += 1;
        }*/
        System.out.println(by4 + " " + h + " " + w);
        //System.exit(0);
        int value = 0;
        /*value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);*/
        int s = 0;
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int[] pxls = new int[by4];
        System.out.println("W: " + w + " H: " + h + " S: " + pxls.length);
        int idx = 0;
        int x = 0, y = 0;
        for (int i = 0; (i + 4) < str.length(); i+=4) {
            int a = str.charAt(i);
            int r = str.charAt(i + 1);
            int g = str.charAt(i + 2);
            int b = str.charAt(i + 3);
            int argb = ((a & 0xFF) << 24) |
                    ((r & 0xFF) << 16) |
                    ((g & 0xFF) << 8)  |
                    ((b & 0xFF) << 0);
            try {
                bi.setRGB(x++, y, argb);
            } catch (Exception ex) {
                System.out.println(y + "." + (x - 1) + " / " + h + "." + w);
            }
            pxls[idx++] = argb;
            if (x == w) {
                x = 0;
                y++;
            }
        }
        /*for (int i = 0; i < a.length(); i++) {
            int x = a.charAt(i);
            System.out.println(x + " " + s);
            value |= (x & 0xFF) << s;
            s += 8;
        }*/
        //Color c = new Color(value);
        BufferedImage input = null;
        String[] formats = ImageIO.getWriterFormatNames();
        System.out.println(Arrays.toString(formats));
        for (String format : formats) {
            ImageIO.write(bi, format, new FileOutputStream("/Users/arunavasaha/personal/formats/z." + format));
            input = ImageIO.read(new File("/Users/arunavasaha/personal/formats/z." + format));
            if (input != null) {
                StringBuilder sb = new StringBuilder();
                w = input.getWidth();
                h = input.getHeight();
                idx = 0;
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        try {
                            int argb = input.getRGB(i, j);
                            if (argb != pxls[idx++]) {
                                System.out.println(format + " = broke @ " + j + "." + i + " (" + pxls[idx - 1] + " / " + argb + ")!");
                                i = w;
                                j = h;
                            }
                            /*c = new Color(img.getRGB(i, j));
                            if (i == 1 && j == 1) {
                                System.out.println("c: " + c.getRGB() + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
                            }
                            sb.append((char)c.getRed()).append((char)c.getGreen()).append((char)c.getBlue());*/
                        } catch (Exception ex) {
                            System.err.println("Ex: " + i);
                        }
                    }
                }
                if (idx >= pxls.length) {
                    System.out.println(format + " -> SUCCESS !!! \\o/ \\o/ \\o/ !!! <3");
                }
                /*int b = 32;
                while (b > 0) {
                    b -= 8;
                    int x = (argb >> b) & 0xFF;
                    sb.append((char) x);
                }
                System.out.println(sb.toString() + " " + b + " " + argb);*/
            } else {
                System.out.println(format + " = NULL");
            }
        }
        System.out.println(value + " " + s);
        /*StringBuilder sb = new StringBuilder();
        while (s > 0) {
            s -= 8;
            int x = (value >> s) & 0xFF;
            sb.append((char)x);
        }
        System.out.println(sb.toString() + " " + s + " " + value);*/
        //decodeLarge(path);
        //System.out.println(convert("PAYPALISHIRING", 3));
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (numRows - 2); i++) {
            sb.append(" ");
        }
        String spaces = sb.toString();
        sb.delete(0, sb.length());
        int idx = 0;
        while (numRows > 0) {
            int gap = numRows + (numRows - 2);
            System.out.println("-> I: " + idx + " R: " + numRows + " S: " + spaces.length() + " G: " + gap);
            int i = idx;
            while ((i + gap) < s.length()) {
                sb.append(s.charAt(i)).append(spaces);
                i += gap;
            }
            sb.append(s.charAt(i)).append('\n');
            idx++;
            numRows--;
            if (spaces.length() > 0) {
                spaces = spaces.substring(1);
            }
            System.out.println("<- I: " + idx + " R: " + numRows + " S: " + spaces.length() + " G: " + gap);
        }
        return sb.toString();
    }

    private static void decodeLarge(String path) throws IOException {
        File file = new File(path);
        BufferedImage img = ImageIO.read(file);

        int h = img.getHeight(), w = img.getWidth();
        StringBuilder sb = new StringBuilder();
        Color c = null;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                try {
                    c = new Color(img.getRGB(i, j));
                    if (i == 1 && j == 1) {
                        System.out.println("c: " + c.getRGB() + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
                    }
                    sb.append((char)c.getRed()).append((char)c.getGreen()).append((char)c.getBlue());
                } catch (Exception ex) {
                    System.err.println("Ex: " + i);
                }
            }
        }
        Files.write(Paths.get("/Users/arunavasaha/personal/large_d"), sb.toString().getBytes());
    }

    private static String encodeLarge() throws IOException {
        String content = Files.readAllLines(Paths.get("/Users/arunavasaha/personal/large.txt")).stream().collect(Collectors.joining("\n"));
        /*List<Character> l = new ArrayList<>();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            int x = c;
            if (x > 255) {
                l.add(c);
            }
        }
        System.out.println(l.size());
        System.exit(0);*/
        int by3 = content.length() / 3;
        if (content.length() % 3 != 0) {
            by3 += 1;
        }
        int[] pxls = new int[by3];
        int sq = Long.valueOf(Math.round(Math.sqrt(by3))).intValue();
        int h = sq, w = sq;
        if (sq * sq != by3) {
            w += 1;
        }
        System.out.println(Math.pow(sq, 2) + " " + sq + "(" + w + "x" + h + ")");
        System.out.println(content.length() + " " + (content.length() / 3));
        Map<Character, Integer> cMap = new HashMap<>();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        int i = 0;
        int x = 0, y = 0;
        int idx = 0;
        while (i + 3 < content.length()) {
            char rC = content.charAt(i++);
            char gC = content.charAt(i++);
            char bC = content.charAt(i++);
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            try {
                Color c = new Color(r, g, b);
                if (x == 1 && y == 0) {
                    System.out.println("c: " + c.getRGB() + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
                }
                img.setRGB(x++, y, c.getRGB());
                pxls[idx++] = c.getRGB();
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y + " - " + idx);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        if (i < content.length()) {
            char rC = content.charAt(i++);
            char gC = i < content.length() ? content.charAt(i++) : 0;
            char bC = i < content.length() ? content.charAt(i) : 0;
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            try {
                Color c = new Color(r, g, b);
                img.setRGB(x, y, c.getRGB());
                pxls[idx] = c.getRGB();
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println(pxls.length + " -> " + pxls[pxls.length - 1]);
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Map<Integer, Integer> pm = new HashMap<>();
        for (int p : pxls) {
            if (p < min) min = p;
            if (p > max) max = p;
            pm.put(p, pm.getOrDefault(p, 0) + 1);
        }
        System.out.println(min + " < " + max);
        for (Integer p : pm.values()) {
            if (p > max) max = p;
        }
        System.out.println("MaxDup: " + max);
        System.out.println(cMap.size());
        /*File file = new File("/Users/arunavasaha/personal/large.jpg");
        ImageIO.write(img, "JPG", file);*/
        return "/Users/arunavasaha/personal/large.jpg";
    }

    //for Unicode chars where character ascii can be beyond 255 in which case using float value to render pixel.
    public static void main2(String[] args) throws IOException {
        String content = String.join("\n", Files.readAllLines(Paths.get("/Users/arunavasaha/personal/enwik8")));
        Map<Character, Float> m = new HashMap<>();
        for (int j = 0; j < content.length(); j++) {
            char c = content.charAt(j);
            int x = c;
            if (x > 255) {
                m.put(c, toB255(x));
                System.out.println(c + " " + x + " " + m.get(c) + " " + b255ToChar(m.get(c)));

                if (m.size() == 10) {
                    j = content.length();
                }
            }
        }
        System.out.println(m);
        //System.exit(0);
        long by3 = content.length() / 3;
        if (content.length() % 3 != 0) {
            by3 += 1;
        }
        int sq = Long.valueOf(Math.round(Math.sqrt(by3))).intValue();
        int h = sq, w = sq;
        if (sq * sq != by3) {
            w += 1;
        }
        System.out.println(Math.pow(sq, 2) + " " + sq + "(" + w + "x" + h + ")");
        System.out.println(content.length() + " " + (content.length() / 3));
        Map<Character, Integer> cMap = new HashMap<>();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int i = 0;
        int x = 0, y = 0;
        while (i + 3 < content.length()) {
            char rC = content.charAt(i++);
            char gC = content.charAt(i++);
            char bC = content.charAt(i++);
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            Color c = null;
            try {
                if (r > 255 || g > 255 || b > 255) {
                    c = new Color(toB255(r), toB255(g), toB255(b));
                } else {
                    c = new Color(r, g, b);
                }
                img.setRGB(x++, y, c.getRGB());
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        if (i < content.length()) {
            char rC = content.charAt(i++);
            char gC = i < content.length() ? content.charAt(i++) : 0;
            char bC = i < content.length() ? content.charAt(i) : 0;
            cMap.put(rC, cMap.getOrDefault(rC, 0) + 1);
            cMap.put(gC, cMap.getOrDefault(gC, 0) + 1);
            cMap.put(bC, cMap.getOrDefault(bC, 0) + 1);
            int r = rC;
            int g = gC;
            int b = bC;
            Color c = null;
            try {
                if (r > 255 || g > 255 || b > 255) {
                    c = new Color(toB255(r), toB255(g), toB255(b));
                } else {
                    c = new Color(r, g, b);
                }
                img.setRGB(x++, y, c.getRGB());
                if (x == w) {
                    x = 0;
                    y++;
                }
            } catch (Exception ex) {
                System.err.println("Ex: [" + rC + " " + r + ", " + gC + " " + g + ", " + bC + " " + b + "] @ " + x + " " + y);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println(cMap.size());
        File file = new File("/Users/arunavasaha/personal/enwik8.jpg");
        ImageIO.write(img, "jpg", file);
    }

    private static char b255ToChar(float v) {
        String s = String.valueOf(v);
        int x = Integer.parseInt(new StringBuilder(s.substring(s.indexOf('.') + 1)).reverse().toString());
        System.out.println(s + " " + x);
        return (char)x;
    }

    private static float toB255(int n) {
        return Float.valueOf("0." + (new StringBuilder(String.valueOf(n)).reverse()));
    }

    private static float getB255Number(int n) {
        if (n <= 255) return n;
        int x = n;
        int y = 0;
        boolean flag = true;
        int d = 10;
        while (n / d > 255) {
            d *= 10;
        }
        return Float.valueOf(n / d + "." + n % d);
    }

}
