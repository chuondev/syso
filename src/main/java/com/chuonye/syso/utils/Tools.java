package com.chuonye.syso.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 辅助工具
 * 
 * @author chuonye@foxmail.com
 */
public final class Tools {
    
    /** 清除 html 标签 */
    public static String stripTags(String content) {
        return content.replaceAll("<[^>]+>", "");
    }
    
    // 单位 毫秒 ms
    public static final Long ONE_SECONED = 1000L;
    public static final Long ONE_MINUTE = 60 * ONE_SECONED;
    public static final Long ONE_HOUR = 60 * ONE_MINUTE;
    public static final Long ONE_DAY = 24 * ONE_HOUR;
    public static final Long ONE_MONTH = 30 * ONE_DAY;
    public static final Long ONE_YEAR = 12 * ONE_MONTH;

    public static String timeWord(Long mills) {
        StringBuilder word = new StringBuilder(8);
        mills = System.currentTimeMillis() - mills;
        if (mills >= ONE_YEAR) {
            word.append(mills / ONE_YEAR).append("年前");
        } else if (mills >= ONE_MONTH) {
            word.append(mills / ONE_MONTH).append("个月前");
        } else if (mills >= ONE_DAY) {
            word.append(mills / ONE_DAY).append("天前");
        } else if (mills >= ONE_HOUR) {
            word.append(mills / ONE_HOUR).append("小时前");
        } else if (mills >= ONE_MINUTE) {
            word.append(mills / ONE_MINUTE).append("分钟前");
        } else if (mills >= ONE_SECONED) {
            word.append(mills / ONE_SECONED).append("秒前");
        } else {
            word.append("刚刚");
        }
        return word.toString();
    }
    
    public static String timeToDhms(Long secondsTime) {
        String retv = null;
        long days    =  secondsTime / (ONE_DAY/1000);
        long hours   = (secondsTime % (ONE_DAY/1000)) / (ONE_HOUR/1000);
        long minutes = (secondsTime % (ONE_HOUR/1000)) / (ONE_MINUTE/1000);
        long seconds =  secondsTime % (ONE_MINUTE/1000);
        
        if (days > 0) {
            retv = days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (hours > 0) {
            retv = hours + "h " + minutes + "m " + seconds + "s";
        } else if (minutes > 0) {
            retv = minutes + "m " + seconds + "s";
        } else {
            retv = seconds + "s";
        }
        
        return retv;
    }
    
    
    public static String format(String tpl, Object... args) {
        String token = "{}";
        StringBuilder builder = new StringBuilder(tpl.length() + 50);
        int start = 0;
        for (Object arg : args) {
            int tokenIndex = tpl.indexOf(token, start);
            if (tokenIndex < 0) {
                builder.append(tpl.substring(start));
                builder.append(" ");
                builder.append(arg);
                start = tpl.length();
            } else {
                builder.append(tpl.substring(start, tokenIndex));
                builder.append(arg);
                start = tokenIndex + token.length();
            }
        }
        builder.append(tpl.substring(start));
        return builder.toString();
    }
    
    private static final Random RANDOM = new Random();
    
    public static String randomString(int count) {
        if (count <= 0) {
            count = 5;
        }
        
        int start = ' ', end = 'z' + 1; // 字母数字
        int gap = end - start;
        
        char[] buffer = new char[count];
        
        while (count-- != 0) {
            char ch = (char) (RANDOM.nextInt(gap) + start);
            
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                buffer[count] = ch;
            } else {
                count++;
            }
        }
        
        return new String(buffer);
    }
    
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    public static String md5Hex(String target) {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] data = md5Digest.digest(target.getBytes(StandardCharsets.UTF_8));
            
            int l = data.length;
            char[] out = new char[l << 1];
            // two characters form the hex value.
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
                out[j++] = DIGITS_LOWER[0x0F & data[i]];
            }
            return new String(out);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    // from common-lang-StringUtils
    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }
    public static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {
        if (str == null || searchStr == null || ordinal <= 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? str.length() : -1;
        do {
            if(lastIndex) {
                index = str.lastIndexOf(searchStr, index - 1);
            } else {
                index = str.indexOf(searchStr, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }
    
    public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes)
                        throws IOException {
                    Files.delete(path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
                    Files.delete(directory);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
