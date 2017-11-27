package com.chen.brand.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PdfUtil {

    public static void getPdf(Map<String, Object> data, String path){
        try {
            createPdf(path, data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String isNull(Object object){
        if(object == null)  return "";
        return object.toString();
    }

    public static void createPdf(String dest, Map<String, Object> data) throws IOException{
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PageSize pageSize = PageSize.A4;
        Document doc = new Document(pdfDoc, pageSize, false);
        doc.setLeftMargin(60);
        doc.setRightMargin(60);
        doc.setTopMargin(60);
        doc.setBottomMargin(60);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        Date date = new Date(System.currentTimeMillis());

        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
        //PdfFont font = PdfFontFactory.createFont("c://windows//fonts//simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
        Style code = new Style();
        code.setFont(font)
                .setFontSize(11)
                .setFontColor(Color.BLACK)
                //.setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        Style codeTitle = new Style();
        codeTitle.setFont(font)
                .setFontSize(14)
                .setFontColor(Color.BLACK)
                .setBold();

        Style codeArea = new Style();
        codeArea.setFont(font)
                .setFontSize(11)
                .setFontColor(Color.BLACK);

        Style front = new Style()
                .setFont(font)
                .setFontSize(18)
                .setFontColor(Color.BLACK);

        doc.add(new Paragraph("附件 1")
                .setFontSize(20)
                .setFont(font));
        doc.add(new Paragraph("\n\n\n\n\n\n\n"));
        Paragraph text = new Paragraph("杭州市公共海外仓评审材料")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(font)
                .setFontSize(38);
        doc.add(text);
        doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));
        doc.add(new Paragraph());
        doc.add(new Paragraph());
        doc.add(new Paragraph());
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("项目名称：")
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("申请单位：")
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("联  系   人：")
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("联系电话：")
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("申请日期：")
                .addStyle(front).setFontSize(20));

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new Paragraph("承诺书")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .addStyle(front));

        doc.add(new Paragraph("\n\n"));
        doc.add(new Paragraph(" 我公司郑重承诺：\n" )
                .setFirstLineIndent(40)
                .addStyle(front));
        doc.add(new Paragraph(
                "   在本次市级公共海外仓评审中所申报的内容及相关资质证明材料，均系真实内容。如评审单位发现有不实之处，我公司无条件接受处罚决定，并承担由此造成的一切后果。" )
                .setFirstLineIndent(40)
                .addStyle(front));
        doc.add(new Paragraph("   我公司将认真对待本次评审，完全接受评审单位做出的评审结果。如果我公司被评为市级公共海外仓，作为服务单位，我公司将接受市级公共海外仓的管理规定，履行相关责任和义务，遵守服务承诺。")
                .setFirstLineIndent(40)
                .addStyle(front));
        doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n"));
        doc.add(new Paragraph().add(new Tab()).add("海外仓运营企业：" + "（盖章）")
                .addStyle(front));
        doc.add(new Paragraph().add(new Tab()).add("法定代表人（或委托代理人）：" + "（盖章）")
                .addStyle(front));
        doc.add(new Paragraph().add(new Tab()).add("日期：" + sf.format(date))
                .addStyle(front));

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Paragraph title = new Paragraph("表1  建设单位情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);

        Table table = new Table(10);
        //------1
        Cell cell= new Cell(1, 10).add("一、基本情况")
                .addStyle(codeTitle);
        table.addCell(cell);

        cell = new Cell(1, 2).add("运营企业名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                //.setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 8).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        /*doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2 相关佐证材料")
                .setFontSize(20)
                .setFont(font));
        doc.add(new Paragraph("附件 2.1 境外营业执照")
                .setFontSize(16)
                .setFont(font));
        String name;
        Image img;

        if(warehouse.get("yyzz") != null) {
            name = (String) warehouse.get("yyzz");
            System.out.println("name = " + name);
            String zipName = name.split("/")[name.split("/").length - 1];
            System.out.println("zipName = " + zipName);
            String zipPath = PathKit.getWebRootPath() + File.separator + "hwckimages" + File.separator + zipName;
            System.out.println("zipName = " + zipName + ", indexOf(.) = " + zipName.indexOf(".") + ", " + Arrays.toString(zipName.split(".")));
            String unPath = PathKit.getWebRootPath() + File.separator + "unzips" + File.separator + zipName.substring(0, zipName.indexOf("."));
            System.out.println("zipPath = " + zipPath);
            System.out.println("unPath = " + unPath);
            java.util.List<String> images = unZip(zipPath, unPath);
            if(images != null) {
                for (int i = 0; i < images.size(); ++i) {
                    String image = images.get(i);
                    *//*if (i != 0) {
                        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                    }*//*
                    img = new Image(ImageDataFactory.create(image));
                    //img.setHeight(297 * 2);
                    img.setWidth(475);
                    doc.add(img);
                }
            }
        }

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.2 仓库平面图")
                .setFontSize(16)
                .setFont(font));
        *//*if(warehouse.get("pmt") != null) {
            name = warehouse.getStr("pmt");
            img = new Image(ImageDataFactory.create(PathKit.getWebRootPath() + File.separator +
                    "hwckimages" + File.separator + name.split("/")[name.split("/").length - 1]));
            //img.setHeight(297 * 2);
            img.setWidth(475);
            doc.add(img);
        }*//*
        if(warehouse.get("pmt") != null) {
            name = (String) warehouse.get("pmt");
            System.out.println("name = " + name);
            String zipName = name.split("/")[name.split("/").length - 1];
            System.out.println("zipName = " + zipName);
            String zipPath = PathKit.getWebRootPath() + File.separator + "hwckimages" + File.separator + zipName;
            System.out.println("zipName = " + zipName + ", indexOf(.) = " + zipName.indexOf(".") + ", " + Arrays.toString(zipName.split(".")));
            String unPath = PathKit.getWebRootPath() + File.separator + "unzips" + File.separator + zipName.substring(0, zipName.indexOf("."));
            System.out.println("zipPath = " + zipPath);
            System.out.println("unPath = " + unPath);
            java.util.List<String> images = unZip(zipPath, unPath);
            if(images != null) {
                for (int i = 0; i < images.size(); ++i) {
                    String image = images.get(i);
                    *//*if (i != 0) {
                        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                    }*//*
                    img = new Image(ImageDataFactory.create(image));
                    //img.setHeight(297 * 2);
                    img.setWidth(475);
                    doc.add(img);
                }
            }
        }*/

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.15  主管部门审核意见")
                .setFontSize(16)
                .setFont(font));

        table = new Table(10);
        cell= new Cell(1, 2).add("注册地商务部门推荐意见")
                .addStyle(code).setFontSize(14);
        table.addCell(cell);
        Paragraph p1 = new Paragraph().add(new Tab()).add(new Tab()).add(new Tab()).add(new Tab()).add("（单位盖章）");
        Paragraph p2 = new Paragraph().add(new Tab()).add(new Tab()).add(new Tab()).add("年").add(new Tab()).add("月").add(new Tab()).add("日");
        Paragraph p3 = new Paragraph().add(new Tab()).add(new Tab()).add("联系人：").add(new Tab()).add(new Tab()).add("电话：");
        cell= new Cell(1, 8).add("\n\n\n\n\n\n\n\n").add(p1).add("\n").add(p2).add("\n").add(p3).add("\n")
                .addStyle(code).setFontSize(14);
        table.addCell(cell);

        cell= new Cell(1, 2).add("注册地财政部门推荐意见")
                .addStyle(code).setFontSize(14);
        table.addCell(cell);
        cell= new Cell(1, 8).add("\n\n\n\n\n\n\n\n").add(p1).add("\n").add(p2).add("\n").add(p3).add("\n")
                .addStyle(code).setFontSize(14);
        table.addCell(cell);
        doc.add(table);

        int n = pdfDoc.getNumberOfPages();
        Paragraph footer;
        for (int page = 1; page <= n; page++) {
            if(page == 1 || page == 2)  continue;
            footer = new Paragraph(String.format("第 %s 页 共 %s 页", page - 2, n - 2)).addStyle(code);
            doc.showTextAligned(footer, 297.5f, 20, page,
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0);
        }

        doc.close();
    }

    /*public static java.util.List<String> unZip(String path, String unPath){
        File unZip = new File(unPath);
        if(!unZip.exists()){
            unZip.mkdirs();
        }
        File file = new File(path);
        ZipFile zipFile = null;
        try{
            zipFile = new ZipFile(file, "GBK");
        }catch(IOException e){
            e.printStackTrace();
            log.info("压缩文件不存在");
            return null;
        }
        Enumeration e = zipFile.getEntries();
        java.util.List<String> images = new LinkedList<String>();
        while(e.hasMoreElements()){
            ZipEntry zipEntry = (ZipEntry) e.nextElement();
            if(zipEntry.isDirectory()){
                System.out.println("dir = " + zipEntry.getName());
                new File(unZip + File.separator + zipEntry.getName())
                        .mkdirs();
                continue;
            }else {
                String imagePath = unZip + File.separator + zipEntry.getName();
                System.out.println("imagePath = " + imagePath);
                if(imagePath.lastIndexOf(".") == -1)    continue;
                String suffer = imagePath.substring(imagePath.lastIndexOf("."), imagePath.length());
                if(imagePath.contains("__MACOSX"))  continue;
                suffer = suffer.toLowerCase();
                if(!(suffer.equals(".jpg") || suffer.equals(".jpeg") || suffer.equals(".png") || suffer.equals(".gif"))) continue;
                new File(imagePath).getParentFile().mkdirs();
                File image = new File(imagePath);
                images.add(imagePath);
                if(! image.exists()){
                    InputStream is = null;
                    FileOutputStream fo = null;
                    try {
                        image.createNewFile();
                        is = zipFile.getInputStream(zipEntry);
                        fo = new FileOutputStream(image);
                        int length = 0;
                        byte[] b = new byte[1024];
                        while((length = is.read(b, 0, 1024)) != -1){
                            fo.write(b, 0, length);
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }finally{
                        try {
                            if (is != null) is.close();
                            if (fo != null) fo.close();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            zipFile.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Collections.sort(images);
        return images;
    }*/

    public static void main(String[] args) throws IOException{

        getPdf(new HashMap<>(), "/Users/chenzhongyi/chen.pdf");
    }
}
