package com.root.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class GZipUtils {


    /**
     * 压缩
     */
    public static byte[] getGZipCompressed(String data) {
        byte[] compressed = null;
        try {
            byte[] byteData = data.getBytes();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(
                    byteData.length);
            Deflater compressor = new Deflater();
            // 将当前压缩级别设置为指定值。
            compressor.setLevel(4);
            compressor.setInput(byteData, 0, byteData.length);
            // 调用时，指示压缩应当以输入缓冲区的当前内容结尾。
            compressor.finish();

            // Compress the data
            final byte[] buf = new byte[1024];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
            compressor.end(); // 关闭解压缩器并放弃所有未处理的输入。
            compressed = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compressed;
    }

    /**
     * 解压
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] getGZipUncompress(byte[] data) throws IOException {
        byte[] unCompressed = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(data);
            final byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }

            unCompressed = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            decompressor.end();
        }
        return unCompressed;
    }
}
