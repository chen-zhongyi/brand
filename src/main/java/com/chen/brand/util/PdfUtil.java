package com.chen.brand.util;

import com.chen.brand.Enum.XyType;
import com.chen.brand.model.*;
import com.chen.brand.sys.SysData;
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
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PdfUtil {

    public static void getPdf(Map<String, Object> data, String path, String uploadPath){
        try {
            createPdf(path, data, uploadPath);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String isNull(Object object){
        if(object == null)  return "";
        return object.toString();
    }

    private static String isNull(Object object, String str){
        if(object == null)  return str;
        return object.toString();
    }

    private static String isBooleanNull(Boolean b){
        if(b == null)   return "否";
        return b == true ? "是" : "否";
    }

    private static final int IMAGE_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 475;

    private static void addImage(String path, Document doc, String uploadPath){
        if(path == null)    return ;
        for(String temp : path.split(",")) {
            temp = uploadPath + File.separator + temp;
            System.out.println("image path = " + temp);
            try {
                Image img = new Image(ImageDataFactory.create(temp));
                if (img == null) return;
                img.setHeight(IMAGE_HEIGHT);
                img.setWidth(IMAGE_WIDTH);
                doc.add(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String dateFormat(java.sql.Date date){
        if(date == null)    return "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        return sf.format(date);
    }

    private static void createPdf(String dest, Map<String, Object> data, String uploadPath) throws IOException{
        int year = (int) data.get("year");
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
        Paragraph text = new Paragraph("杭州市出口名牌评审材料")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(font)
                .setFontSize(38);
        doc.add(text);
        doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));
        doc.add(new Paragraph());
        doc.add(new Paragraph());
        doc.add(new Paragraph());
        /**
         * name: 品牌信息
         * data: brand
         * info:
         */
        Brand brand = (Brand) data.get("brand");
        if(brand == null)   brand = new Brand();

        /**
         * name: 企业基本信息
         * data: sample
         * info:
         */

        Sample sample = (Sample) data.get("sample");
        if(sample == null)  sample = new Sample();

        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("品牌名称：" + isNull(brand.getPpmc()))
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("申请单位：" + isNull(sample.getQymcCn()))
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("联  系   人：" + isNull(sample.getLxr()))
                .addStyle(front).setFontSize(20));
        doc.add(new Paragraph().add(new Tab()).add(new Tab()).add("联系电话：" + isNull(sample.getLxdh()))
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
                "   在本次出口名牌评审中所申报的内容及相关资质证明材料，均系真实内容。如评审单位发现有不真实之处，我公司无条件接受处罚决定，并承担由此造成的一切后果。" )
                .setFirstLineIndent(40)
                .addStyle(front));
        doc.add(new Paragraph("   我公司将认真对待本次评审，完全接受评审单位做出的评审结果。如果我公司被评为市级出口名牌企业，我公司将接受市级出口名牌企业管理规定，履行相关责任和义务，遵守服务承诺。")
                .setFirstLineIndent(40)
                .addStyle(front));
        doc.add(new Paragraph("\n\n\n\n\n\n\n\n\n"));
        doc.add(new Paragraph().add(new Tab()).add("企业名称：" + isNull(sample.getQymcCn()) + "（盖章）")
                .addStyle(front));
        doc.add(new Paragraph().add(new Tab()).add("法定代表人（或委托代理人）：" + isNull(sample.getFrdb()) + "（盖章）")
                .addStyle(front));
        doc.add(new Paragraph().add(new Tab()).add("日期：" + sf.format(date))
                .addStyle(front));

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Paragraph title = new Paragraph("表1  企业基本信息");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);

        Table table = new Table(10);
        //------1
        Cell cell = new Cell(2, 2).add("企业名称:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                //.setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 8).add("(中文) " + isNull(sample.getQymcCn()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 8).add("(英文) " + isNull(sample.getQymcEn()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("住所:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 8).add("(中文) " + isNull(sample.getZcdz()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("所属区县:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getSsqx()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("社会统一信用代码")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getXydm()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("企业类型:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getQylx()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("注册资本(实缴):")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getZczb()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("法人代表:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getFrdb()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("联系人姓名:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(sample.getLxr()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(2, 2).add("联系人电话:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("(手机)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 6).add(isNull(sample.getLxsj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("(固定电话)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 6).add(isNull(sample.getLxdh()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 4).add("是否开展跨境电子商务:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 6).add(isBooleanNull(sample.getDzsw()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 4).add("跨境电子商务活动依托的平台:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 6).add(isNull(sample.getWz()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 10).add("企业简介（需要企业补充）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 10).add(isNull(sample.getQyjj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code)
                .setHeight(300);
        table.addCell(cell);

        doc.add(table);

        Image img = null;
        boolean flag;

        /**
         * name: 附件 1.1 企业法人营业执照
         * data: sample.getYyzz();
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 1.1 企业法人营业执照")
                .setFontSize(20)
                .setFont(font));
        addImage(sample.getYyzz(), doc, uploadPath);
        flag = sample.getYyzz() == null ? false : true;

        /**
         * name: 附件 1.2 对外贸易经营者备案登记证书
         * data: sample.getDjzs();
         * info:
         */
        if(flag == true || sample.getDzsw() != null)    doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 1.2 对外贸易经营者备案登记证书")
                .setFontSize(20)
                .setFont(font));
        addImage(sample.getDjzs(), doc, uploadPath);
        flag = sample.getDjzs() == null ? false : true;

        /**
         * name: 附件 1.3 海关报关单位注册登记证书
         * data:
         * info:
         */
        if(flag == true)    doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 1.3 海关报关单位注册登记证书")
                .setFontSize(20)
                .setFont(font));
        addImage(null, doc, uploadPath);

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        /**
         * name: 表2  申报企业经营情况（ **** 年报）
         * data: base -- 本年度, priBase -- 上年度
         * info:
         */
        TableBase base = data.get("base") == null ? new TableBase() : (TableBase) data.get("base");
        TableBase priBase = data.get("priBase") == null ? new TableBase() : (TableBase) data.get("priBase");
        title = new Paragraph("表2 申报企业经营情况（ " + year +  " 年报）");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);

        table = new Table(10);

        cell = new Cell(1, 10).add("一、资产与利润")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("本年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("上年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("注册资产:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getZczb(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getZczb(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("总资产:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getZzc(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getZzc(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("净资产:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getJzc(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getJzc(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("利润总额:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getLrze(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getLrze(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("实缴税额:")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(base.getSjse(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getSjse(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 10).add("二、销售与出口")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("本年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("上年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("主营业务收入")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getYwsr(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getYwsr(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("全年销售额")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getXse(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getXse(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("其中:出口额")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getCke(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getCke(), "--") + "(元)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("出口额行业年度排名")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("第 " + isNull(base.getHypm(), "--") +  " 位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("第 " + isNull(priBase.getHypm(), "--") + " 位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("排名依据(或机构)")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("本年度出口目的国前五位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 10).add("三、其他")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("本年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("上年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("现有品牌数量")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getPpsl(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getPpsl(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("其中:有出口实绩的品牌数")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getCkppsl(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getCkppsl(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 3).add("企业年末从业人员数")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add(isNull(base.getCyry(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(priBase.getCyry(), "--") + "个")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        /**
         * name: 附件 2.1 企业资产负债表（需有会计师事务所审核章）
         * data: zmcl
         * info:
         */
        ApproveZmcl zmcl = (ApproveZmcl) data.get("zmcl");
        if(zmcl == null) zmcl = new ApproveZmcl();
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.1 企业资产负债表（需有会计师事务所审核章）")
                .setFontSize(20)
                .setFont(font));
        addImage(zmcl.getFzb(), doc, uploadPath);

        /**
         * name: 附件 2.2 企业利润表（需有会计师事务所审核章）
         * data: zmcl
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.2 企业利润表（需有会计师事务所审核章）")
                .setFontSize(20)
                .setFont(font));
        addImage(zmcl.getLrb(), doc, uploadPath);

        /**
         * name: 附件 2.3 企业全年销售额证明（由统计部门出具）
         * data: zmcl
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.3 企业全年销售额证明（由统计部门出具）")
                .setFontSize(20)
                .setFont(font));
        addImage(zmcl.getXse(), doc, uploadPath);

        /**
         * name: 附件 2.4 出口额行业年度排名（省级以上协会证明材料）
         * data: zmcl
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件 2.4 出口额行业年度排名（省级以上协会证明材料）")
                .setFontSize(20)
                .setFont(font));
        addImage(zmcl.getPm(), doc, uploadPath);


        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        /**
         * name: 表3  申报品牌的基本情况
         * data: brand, ppcke, priPpcke
         * info:
         */
        ApprovePpcke ppcke = (ApprovePpcke) data.get("ppcke");
        if(ppcke == null)   ppcke = new ApprovePpcke();
        ApprovePpcke priPpcke = (ApprovePpcke) data.get("priPpcke");
        if(priPpcke == null)   priPpcke = new ApprovePpcke();

        title = new Paragraph("表3  申报品牌的基本情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);

        table = new Table(12);

        cell = new Cell(1, 2).add("申报品牌名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(brand.getPpmc()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("申报类别")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(brand.getPplx()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("品牌商标\nLOGO")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("申报品牌项下的商品")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(brand.getSp()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("品牌商标首次\n注册地")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(brand.getZcd()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("首次注册日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add(isNull(brand.getZcrq()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(2, 2).add("申报品牌项下\n出口额")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add(year + "年")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add((year - 1) + "年")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add(isNull(ppcke.getCke()) + "元")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add(isNull(priPpcke.getCke()) + "元")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add(year + "年度申报品牌项下出口目的国前5位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(ppcke.getOne()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(ppcke.getTwo()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(ppcke.getThree()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(ppcke.getFour()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(ppcke.getFive()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        /**
         * name: 附件3.1 申报品牌的商标注册证书
         * data: brand.getZs()
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件3.1 申报品牌的商标注册证书")
                .setFontSize(20)
                .setFont(font));
        addImage(brand.getZs(), doc, uploadPath);

        /**
         * name: 表4 企业专利获取情况
         * data: jnzls
         * info:
         */
        List<ApproveJnzl> jnzls = (List<ApproveJnzl>) data.get("jnzls");
        int[] num = new int[5];
        for(ApproveJnzl jnzl : jnzls){
            if(jnzl.getLx().trim().equals("发明专利"))  num[1]++;
            else if(jnzl.getLx().trim().equals("实用新型专利"))   num[2]++;
            else if(jnzl.getLx().trim().equals("外观设计专利"))   num[3]++;
            else if(jnzl.getLx().trim().equals("作品登记权"))    num[4]++;
        }
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表4 企业专利获取情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)汇总表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("数量（项）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("发明专利")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[1])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("实用新型专利")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[2])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("3")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("外观设计专利")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[3])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("4")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("作品登记权")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[4])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 6).add("合计")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + jnzls.size())
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("专利名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("专利号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("授权/登记日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        for(int i = 0;i < jnzls.size();++i){
            ApproveJnzl jnzl = jnzls.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jnzl.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jnzl.getLx()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jnzl.getZlh()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(jnzl.getRq()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        /**
         * name: 附件4.1 专利证明材料
         * data: jnzl
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件4.1 专利证明材料")
                .setFontSize(20)
                .setFont(font));
        for(int i = 0;i < jnzls.size();++i){
            ApproveJnzl jnzl = jnzls.get(i);
            addImage(jnzl.getZs(), doc, uploadPath);
            if(i != jnzls.size() - 1)
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        /**
         * name: 表5 企业科技获奖情况
         * data: kjjl
         * info:
         */
        List<ApproveKjjl> kjjls = (List<ApproveKjjl>) data.get("kjjls");
        Arrays.fill(num, 0);
        for(ApproveKjjl kjjl : kjjls){
            if(kjjl.getDj().trim().equals("国家级"))   num[1]++;
            else if(kjjl.getDj().trim().equals("省部级"))  num[2]++;
            else if(kjjl.getDj().trim().equals("市级"))   num[3]++;
            else if(kjjl.getDj().trim().equals("区（县）级"))    num[4]++;
        }
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表5 企业科技获奖情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)汇总表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("级别")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("数量（项）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("国家级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[1])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("省部级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[2])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("3")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("市级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[3])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("4")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("区（县）级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[4])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 6).add("合计")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + kjjls.size())
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        table = new Table(12);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("成果名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("奖项名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("等级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("授予单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("获奖时间")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        for(int i = 0;i < kjjls.size();++i){
            ApproveKjjl kjjl = kjjls.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(kjjl.getCgmc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(kjjl.getJxmc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(kjjl.getDj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(kjjl.getDw()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(kjjl.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        /**
         * name: 附件5.1 科技获奖证明材料
         * data: kjjl
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件5.1 科技获奖证明材料")
                .setFontSize(20)
                .setFont(font));
        for(int i = 0;i < kjjls.size();++i){
            ApproveKjjl kjjl = kjjls.get(i);
            addImage(kjjl.getCl(), doc, uploadPath);
            if(i != kjjls.size() - 1)
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        /**
         * name: 表6 企业科技平台建设情况
         * data: ptjs
         * info:
         */
        List<ApprovePtjs> ptjss = (List<ApprovePtjs>) data.get("ptjss");
        Arrays.fill(num, 0);
        for(ApprovePtjs ptjs : ptjss){
            if(ptjs.getLx().trim().equals("技术中心")) num[1]++;
            else if(ptjs.getLx().trim().equals("重点实验室")) num[2]++;
            else if(ptjs.getLx().trim().equals("孵化器"))  num[3]++;
        }
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表6 企业科技平台建设情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)汇总表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("数量（个）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("技术中心")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[1])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("重点实验室")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[2])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 2).add("3")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("孵化器")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[3])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 6).add("合计")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + ptjss.size())
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("平台名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("平台类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("评定单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("评定时间")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        for(int i = 0;i < ptjss.size();++i){
            ApprovePtjs ptjs = ptjss.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(ptjs.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(ptjs.getLx()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(ptjs.getDw()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(ptjs.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        /**
         * name: 附件6.1 科技平台证明材料
         * data: ptjs
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件6.1 科技平台证明材料")
                .setFontSize(20)
                .setFont(font));
        for(int i = 0;i < ptjss.size();++i){
            ApprovePtjs ptjs = ptjss.get(i);
            addImage(ptjs.getCl(), doc, uploadPath);
            if(i != ptjss.size() - 1)
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        /**
         * name: 表7 企业标准制定情况
         * data: bzqc
         * info:
         */
        List<ApproveBzqc> bzqcs = (List<ApproveBzqc>) data.get("bzqcs");
        Arrays.fill(num, 0);
        for(ApproveBzqc bzqc : bzqcs){
            if(bzqc.getLx().trim().equals("国家") && bzqc.getFg().trim().equals("起草单位")) num[1]++;
            else if(bzqc.getLx().trim().equals("国家") && bzqc.getFg().trim().equals("参与单位")) num[2]++;
            else if(bzqc.getLx().trim().equals("行业") && bzqc.getFg().trim().equals("起草单位"))  num[3]++;
            else if(bzqc.getLx().trim().equals("行业") && bzqc.getFg().trim().equals("参与单位"))  num[4]++;
        }
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表7 企业标准制定情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)汇总表").setFont(font).setFontSize(12));

        cell = new Cell(1, 1).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("分工")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("次数")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(2, 1).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(2, 3).add("国际标准")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("起草单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("" + num[1])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("参与单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("" + num[2])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(2, 1).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(2, 3).add("行业标准")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("起草单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("" + num[3])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("参与单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("" + num[4])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 7).add("合计")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("" + bzqcs.size())
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("标准名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("分工")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("完成时间")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < bzqcs.size();++i){
            ApproveBzqc bzqc = bzqcs.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzqc.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzqc.getLx()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzqc.getFg()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(bzqc.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }
        doc.add(table);

        /**
         * name: 附件7.1 标准起草证明材料
         * data: bzqc
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件7.1 标准起草证明材料")
                .setFontSize(20)
                .setFont(font));
        for(int i = 0;i < bzqcs.size();++i){
            ApproveBzqc bzqc = bzqcs.get(i);
            addImage(bzqc.getCl(), doc, uploadPath);
            if(i != bzqcs.size() - 1)
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        /**
         * name: 表8 企业质量体系建设情况
         * data: bzrz
         * info:
         */
        List<ApproveBzrz> bzrzs = (List<ApproveBzrz>) data.get("bzrzs");
        Arrays.fill(num, 0);
        for(ApproveBzrz bzrz : bzrzs){
            if(bzrz.getTx().trim().equals("质量管理体系认证"))  num[1]++;
            else if(bzrz.getTx().trim().equals("环境管理体系"))  num[2]++;
            else if(bzrz.getTx().trim().equals("职业健康安全管理体系或社会责任标准"))  num[3]++;
            else if(bzrz.getTx().trim().equals("行业质量安全认证"))  num[4]++;
        }
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表8 企业质量体系建设情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)汇总表").setFont(font).setFontSize(12));

        cell = new Cell(1, 1).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("级别")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("数量（项）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 1).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("质量管理体系认证")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[1])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 1).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("环境管理体系")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[2])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 1).add("3")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("职业健康安全管理体系或社会责任标准")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[3])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 1).add("4")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("行业质量安全认证")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + num[4])
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        cell = new Cell(1, 6).add("合计")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 4).add("" + bzrzs.size())
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("国际认证名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("认证系列")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("证书编号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("认证日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < bzrzs.size();++i){
            ApproveBzrz bzrz = bzrzs.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzrz.getBz()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzrz.getTx()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(bzrz.getZsbh()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(bzrz.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }
        doc.add(table);

        /**
         * name: 附件8.1 国际认证证明材料
         * data: bzrz
         * info:
         */
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new Paragraph("附件8.1 国际认证证明材料")
                .setFontSize(20)
                .setFont(font));
        for(int i = 0;i < bzrzs.size();++i){
            ApproveBzrz bzrz = bzrzs.get(i);
            addImage(bzrz.getZs(), doc, uploadPath);
            if(i != bzrzs.size() - 1)
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        /**
         * name: 表9 报品牌项下的境外注册商标
         * data: jwsb
         * info:
         */
        List<ApproveJwsb> jwsbs = (List<ApproveJwsb>) data.get("jwsbs");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表9 报品牌项下的境外注册商标");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("商标名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("商标LOGO")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("注册形式/国家")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("注册时间")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < jwsbs.size();++i){
            ApproveJwsb jwsb = jwsbs.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwsb.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add("商标LOGO")
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwsb.getXs()) + "/" + isNull(jwsb.getDygj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(jwsb.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }
        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        for(ApproveJwsb jwsb : jwsbs){
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(jwsb.getCl(), doc, uploadPath);
        }

        /**
         * name: 表10 申报品牌项下的境外收购情况d
         * data: jwpp
         * info:
         */
        List<ApproveJwpp> jwpps = (List<ApproveJwpp>) data.get("jwpps");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表10 申报品牌项下的境外收购情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("境外收购品牌名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("收购品牌商标LOGO")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("收购发生地")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("收购日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < jwpps.size();++i){
            ApproveJwpp jwpp = jwpps.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwpp.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add("收购品牌商标LOGO")
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwpp.getSzg()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(jwpp.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        for(ApproveJwpp jwpp : jwpps){
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(jwpp.getCl(), doc, uploadPath);
        }


        /**
         * name: 表11 申报品牌项下的境外申请专利情况
         * data: jwzl
         * info:
         */
        List<ApproveJwzl> jwzls = (List<ApproveJwzl>) data.get("jwzls");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表11 申报品牌项下的境外申请专利情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("境外申请专利名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("授权组织")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("申请号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("授权日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < jwzls.size();++i){
            ApproveJwzl jwzl = jwzls.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwzl.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwzl.getZz()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(jwzl.getSqh()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(jwzl.getRq()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        for(ApproveJwzl jwzl : jwzls){
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(jwzl.getZs(), doc, uploadPath);
        }

        /**
         * name: 表12 企业全球化经营情况
         * data: qqh
         * info:
         */
        List<ApproveQqh> qqhs = (List<ApproveQqh>) data.get("qqhs");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表12 企业全球化经营情况");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("境外机构名称")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("所在国")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("设立日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < qqhs.size();++i){
            ApproveQqh qqh = qqhs.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(qqh.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(qqh.getLx())
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(qqh.getSzg())
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(qqh.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        for(ApproveQqh qqh : qqhs){
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(qqh.getCl(), doc, uploadPath);
        }

        /**
         * name: 表13 企业信用体系建设
         * data: xyjs
         * info:
         */
        Map<String, ApproveXyjs> xyjss = (Map<String, ApproveXyjs>) data.get("xyjss");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表13 企业信用体系建设");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 1).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("信用类型")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("等级")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("评定年度")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        ApproveXyjs hg = xyjss.get(XyType.HG.code());
        if(hg == null) hg = new ApproveXyjs();
        cell = new Cell(1, 1).add("1")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("企业进出口信用（海关总署）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(hg.getDj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(hg.getYear()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        ApproveXyjs gs = xyjss.get(XyType.GS.code());
        if(gs == null) gs = new ApproveXyjs();
        cell = new Cell(1, 1).add("2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("企业纳税信用（国税总局）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(gs.getDj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(gs.getYear()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        ApproveXyjs zj = xyjss.get(XyType.ZJ.code());
        if(zj == null) zj = new ApproveXyjs();
        cell = new Cell(1, 1).add("3")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("出入境检验检疫企业信用（质检总局）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(zj.getDj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(zj.getYear()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        ApproveXyjs gongshang = xyjss.get(XyType.GONG_SHANG.code());
        if(gongshang == null) gongshang = new ApproveXyjs();
        cell = new Cell(1, 1).add("4")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("企业信用（工商总局）")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(gongshang.getDj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(gongshang.getYear()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        ApproveXyjs sj = xyjss.get(XyType.SJ.code());
        if(sj == null) sj = new ApproveXyjs();
        cell = new Cell(1, 1).add("5")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 5).add("市级以上信用管理示范")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(sj.getDj()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add(isNull(sj.getYear()))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        if(hg.getCl() != null) {
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(hg.getCl(), doc, uploadPath);
        }
        if(gs.getCl() != null) {
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(gs.getCl(), doc, uploadPath);
        }
        if(zj.getCl() != null) {
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(zj.getCl(), doc, uploadPath);
        }
        if(gongshang.getCl() != null) {
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(gongshang.getCl(), doc, uploadPath);
        }
        if(sj.getCl() != null) {
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(sj.getCl(), doc, uploadPath);
        }

        /**
         * name: 表14 企业荣誉与社会评价
         * data: rych
         * info:
         */
        List<ApproveRych> rychs = (List<ApproveRych>) data.get("rychs");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表14 企业荣誉与社会评价");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 1).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("荣誉称号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("授予单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 3).add("授予日期")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < rychs.size();++i){
            ApproveRych rych = rychs.get(i);
            cell = new Cell(1, 1).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 3).add(isNull(rych.getMc()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 3).add(isNull(rych.getJg()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 3).add(dateFormat(rych.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        doc.add(new Paragraph("(二)证明材料").setFont(font).setFontSize(12));
        for(ApproveRych rych : rychs){
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addImage(rych.getCl(), doc, uploadPath);
        }

        /**
         * name: 表15 部门处罚情况说明
         * data: cfsm
         * info:
         */
        List<ApproveCfsm> cfsms = (List<ApproveCfsm>) data.get("cfsms");
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        title = new Paragraph("表15 部门处罚情况说明");
        title.setTextAlignment(TextAlignment.CENTER)
                .addStyle(codeTitle);
        doc.add(title);
        table = new Table(10);
        doc.add(new Paragraph("(一)明细表").setFont(font).setFontSize(12));

        cell = new Cell(1, 2).add("序号")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("处罚事项")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("处罚单位")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("处罚时间")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);
        cell = new Cell(1, 2).add("处罚情况说明")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(code);
        table.addCell(cell);

        for(int i = 0;i < cfsms.size();++i){
            ApproveCfsm cfsm = cfsms.get(i);
            cell = new Cell(1, 2).add("" + (i + 1))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(cfsm.getSx()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(cfsm.getDw()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(dateFormat(cfsm.getSj()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
            cell = new Cell(1, 2).add(isNull(cfsm.getSm()))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(code);
            table.addCell(cell);
        }

        doc.add(table);

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        table = new Table(10);
        Paragraph p1 = new Paragraph().add("区县主管单位初审意见：");
        Paragraph p2 = new Paragraph().add("联 系 人：");
        Paragraph p3 = new Paragraph().add("电     话：");
        Paragraph p4 = new Paragraph().add("传     真：");
        Paragraph p5 = new Paragraph().add("手     机：");
        Paragraph p6 = new Paragraph().add("电子邮件：");
        Paragraph p7 = new Paragraph().add(new Tab()).add(new Tab()).add(new Tab()).add(new Tab()).add(new Tab()).add("(盖章)");
        cell= new Cell(1, 10).add(p1).add("\n\n\n\n\n\n").add(p2).add(p3).add(p4).add(p5).add(p6).add(p7).add("\n\n")
                .addStyle(code).setFontSize(14);
        table.addCell(cell);

        p1 = new Paragraph().add("市级主管单位初审意见：");
        cell= new Cell(1, 10).add(p1).add("\n\n\n\n\n\n").add(p2).add(p3).add(p4).add(p5).add(p6).add(p7).add("\n\n")
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

        getPdf(new HashMap<>(), "/Users/chenzhongyi/chen.pdf", "/Users/chenzhongyi/upload");
    }
}
