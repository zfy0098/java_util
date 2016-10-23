package com.rom.util.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoad {

    // 迅雷下载 弱爆了 看看这个
    public static void main(String[] args) {

    	DownLoad download = new DownLoad();
        download.run();

    }

    public void run() {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(new File("/alidata1/linshi/zfy/picurl.txt"));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String x = "";
            while ((x = br.readLine()) != null) {
                list.add(x);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < list.size(); i++) {
            downloadpic(list.get(i));
        }
    }

    public void downloadpic(String imgurl) {

        // 下载链接
        // imgurl =
        // "http://vod81.c20.lixian.vip.xunlei.com/download?fid=Fa6JYKhSktIFAC9CS/"
        // +
        // "vSiPqvKcgRZYoBAAAAAE+OzDDQMKcK1JvL8LnS4EJ0IzVi&mid=666&threshold=150&tid="
        // + "69F8077525A6DBF6225FDA76633D456C&srcid=6&verno=1&g="
        // +
        // "4F8ECC30D030A70AD49BCBF0B9D2E04274233562&ui=xlkuaichuan&s=25847057&"
        // + "pk=kuaichuan&ak=8:0:999:0&e=1427397980&ms=1433600&ci=&ck="
        // +
        // "27868D3F2491379692DDBA74FA8A3353&at=3129FA76E995680A00968DB0BD6756D1&"
        // + "n=03A556D175706A6A678744C13A71617200&k=1&ts=1425521078";
        // 开始从哪个下载(默认为0)
        int nStartPos = 0;
        // 每次读取下载的字节数目(默认2048)

        int nRead = 2048;

        // 下载成功的文件名称

        // String sName = "tortoisehg-3.2.2-x64.rar";
        String sName = imgurl.substring(imgurl.lastIndexOf("/") + 1);

        // 保存的文件目录

        System.out.println(sName);

        String sPath = "/alidata1/linshi/zfy/pic6p/";

        if (new File(sPath + sName).exists()) {
            System.out.println("文件存在");
            return;
        }

        try {

            URL url = new URL(imgurl);

            // 打开连接

            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            // 获得文件长度

            long nEndPos = getFileSize(imgurl);

            RandomAccessFile oSavedFile = new RandomAccessFile(sPath + sName,"rw");

            httpConnection.setRequestProperty("User-Agent", "Internet Explorer");

            String sProperty = "bytes=" + nStartPos + "-";

            httpConnection.setRequestProperty("RANGE", sProperty);

            InputStream input = httpConnection.getInputStream();

            byte[] b = new byte[nRead];

            int sum = 0;

            int nwrite = 0;

            // 读取网络文件,写入指定的文件中

            while ((nwrite = input.read(b, 0, nRead)) > 0 && nStartPos < nEndPos) {

                oSavedFile.write(b, 0, nwrite);

                nStartPos += nwrite;

                ++sum;

                // 每下载200kb打印一次

                if (sum % (1024 * 256 / nRead) == 0)

                    System.out.println("已下载："
                            + (String.format("%.4f",(double) nStartPos / 1024 / 1024))
                            + "MB        "
                            + String.format("%.2f", (double) nStartPos * 100 / nEndPos) + "%");

            }

            oSavedFile.close();

            httpConnection.disconnect();

            System.out.println("---下载完成---");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // 获得文件长度

    public static long getFileSize(String s) {

        int nFileLength = -1;

        try {

            URL url = new URL(s);

            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestProperty("User-Agent", "Internet Explorer");

            int responseCode = httpConnection.getResponseCode();

            if (responseCode >= 400) {
                System.err.println("Error Code : " + responseCode);
                return -2;
            }

            String sHeader;

            for (int i = 1;; i++) {

                sHeader = httpConnection.getHeaderFieldKey(i);

                if (sHeader != null) {
                    if (sHeader.equals("Content-Length")) {
                        nFileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
                        break;
                    }
                } else{
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("文件大小为："
                + String.format("%.4f", (double) nFileLength / 1024 / 1024)
                + "MB\n");

        return nFileLength;

    }
}