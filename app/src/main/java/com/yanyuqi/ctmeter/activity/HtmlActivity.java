package com.yanyuqi.ctmeter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yanyuqi.ctmeter.R;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * 待完善，不使用
 */
public class HtmlActivity extends AppCompatActivity {


    private WebView webView;
    private String docPath = "";
    private String docName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        webView = (WebView) findViewById(R.id.wb);
        Intent intent = getIntent();
        docPath = intent.getStringExtra("path") + File.separator;
        docName = intent.getStringExtra("name");
        String name = docName.substring(0, docName.indexOf("."));

        convert2Html(docPath + docName, docPath + name + ".html");

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webView.loadUrl("file:/" + docPath + name + ".html");

    }

    private void convert2Html(String s, String s1) {
        HWPFDocument wordDocument = null;
        try {
            wordDocument = new HWPFDocument(new FileInputStream(s));
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            HtmlDocumentFacade htmlDocumentFacade = new HtmlDocumentFacade(document);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(htmlDocumentFacade);
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] bytes, PictureType pictureType, String s, float v, float v1) {
                    String name = docName.substring(0, docName.indexOf("."));
                    String picPath = name + "/" + s;
                    Log.i("yyqdata","===>"+picPath);
                    return picPath;
                }
            });

            List<Picture> pictures = wordDocument.getPicturesTable().getAllPictures();
            if (pictures != null) {
                for (int i = 0; i < pictures.size(); i++) {
                    Picture picture = pictures.get(i);
                    String name = docName.substring(0, docName.indexOf("."));
                    //图片写入文件保存
                    String picPath = docPath + name + "/" + picture.suggestFullFileName();
                    File picFile = new File(picPath);
                    if (!(picFile.getParentFile()).exists()){
                        picFile.getParentFile().mkdirs();
                    }
                    picture.writeImageContent(new FileOutputStream(picPath));
                    Log.i("yyqdata",">>>>>>"+picPath);
                }
            }

            wordToHtmlConverter.processDocument(wordDocument);
            Document document1 = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult(bos);
            DOMSource domSource = new DOMSource(document1);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty(OutputKeys.METHOD,"html");
            transformer.transform(domSource,streamResult);

            writeFile(new String(bos.toByteArray()),s1);//写入html文件
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    private void writeFile(String content, String path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),"utf-8"));
            bw.write(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
