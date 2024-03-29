package com.rom.util.image;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/******************************************************************************* 
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 * @author zhoufy
 */
public class CompressPicDemo {

    /**
     * 文件对象
     */
    private File file = null;

    /**
     * 输入图路径
     */
    private String inputDir;
    /**
     * 输出图路径
     */
    private String outputDir;

    /**
     * 输入图文件名
     */
    private String inputFileName;

    /**
     * 输出图文件名
     */
    private String outputFileName;

    /**
     * 默认输出图片宽
     */
    private int outputWidth;

    /**
     * 默认输出图片高
     */
    private int outputHeight;

    /**
     * 是否等比缩放标记(默认为等比缩放)
     */
    private boolean proportion;


    public CompressPicDemo() {
        inputDir = "";
        outputDir = "";
        inputFileName = "";
        outputFileName = "";
        outputWidth = 100;
        outputHeight = 100;
    }


    public void setWidthAndHeight(int width, int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }

    /**
     * 获得图片大小
     * 传入参数 String path ：图片路径
     */
    public long getPicSize(String path) {
        file = new File(path);
        return file.length();
    }

    /**
     * 图片处理
     */
    public String compressPic() {
        try {
            //获得源文件
            file = new File(inputDir + inputFileName);
            if (!file.exists()) {
                return "";
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                System.out.println(" can't read,retry!" + "<BR>");
                return "no";
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newHeight = img.getHeight(null);
                    newWidth = img.getWidth(null);
                }
                saveImage(img, newWidth, newHeight, outputDir, outputFileName);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "ok";
    }

    private void saveImage(Image img, int newWidth, int newHeight, String outputDir, String outputFileName) throws IOException {
        BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        /*
         * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
         * 优先级比速度高 生成的图片质量比较好 但速度慢
         */
        tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING), 0, 0, null);
        FileOutputStream out = new FileOutputStream(outputDir + outputFileName);
        String formatName = outputFileName.substring(outputFileName.lastIndexOf(".") + 1);

        ImageIO.write(tag, formatName, out);

        out.close();
    }

    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName) {
        // 输入图路径   
        this.inputDir = inputDir;
        // 输出图路径   
        this.outputDir = outputDir;
        // 输入图文件名   
        this.inputFileName = inputFileName;
        // 输出图文件名  
        this.outputFileName = outputFileName;
        return compressPic();
    }

    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {
        // 输入图路径   
        this.inputDir = inputDir;
        // 输出图路径   
        this.outputDir = outputDir;
        // 输入图文件名   
        this.inputFileName = inputFileName;
        // 输出图文件名   
        this.outputFileName = outputFileName;
        // 设置图片长宽  
        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记   
        this.proportion = gp;
        return compressPic();
    }

    /**
     * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
     */
    public static void main(String[] arg) {
        CompressPicDemo myPic = new CompressPicDemo();
        myPic.compressPic("/Users/zhoufy/Downloads/", "/Users/zhoufy/Downloads/", "14371.jpg", "r114599.jpg");
        System.out.println("输入的图片大小：" + myPic.getPicSize("/Users/zhoufy/Downloads/14599.jpg") / 1024 + "KB");
    }
}
