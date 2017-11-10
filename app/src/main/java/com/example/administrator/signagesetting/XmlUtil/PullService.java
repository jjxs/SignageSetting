package com.example.administrator.signagesetting.XmlUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;
import android.widget.Toast;

import com.example.administrator.signagesetting.bean.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Administrator on 2017/11/7.
 */

public class PullService {

    private static String fileName = "setting.xml";
    private XmlSerializer serializer;

    /**
     *   写入xml文件
     */
    public void writeXML(Properties properties, Context context) {

        File dirFile = new File(Environment.getExternalStorageDirectory().toString() + "/signage");
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdir();
        }
        try {
            File xmlFile = new File(dirFile.getPath() + "/" + fileName);
            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(xmlFile);
            serializer = Xml.newSerializer();
            serializer.setOutput(fos, "utf-8");

            //设置文件头
            setFileTitle();

            //server_url
            setContent("server_url",properties.getServer_url());

            //application_root
            setContent("application_root",properties.getApplication_root());

            //usb_root
            setContent("usb_root",properties.getUsb_root());

            //interval_get_schedule
            setContent("interval_get_schedule",properties.getGet_schedule());

            //interval_notify_status
            setContent("interval_notify_status",properties.getNotify_status());

            //margin_next_schedule_download
            setContent("margin_next_schedule_download",properties.getSchedule_download());

            serializer.endTag(null, "properties");
            serializer.endDocument();
            fos.close();
            Toast.makeText(context,"保存文件成功",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置文件头
     * @throws IOException
     */
    private void setFileTitle() throws IOException {
        serializer.startDocument("utf-8", true);
        serializer.startTag(null, "properties");

        serializer.startTag(null, "comment");
        serializer.text("signage_application_setting");
        serializer.endTag(null, "comment");
    }

    /**
     * 设置节点的公共方法
     * @param attribute  属性名称
     * @param text       属性内容
     * @throws IOException
     */

    public void setContent(String attribute, String text) throws IOException {
        serializer.startTag(null, "entry");
        serializer.attribute(null, "key", attribute);
        serializer.text(text);
        serializer.endTag(null, "entry");
    }

    /**
     * 读取xml文件
     * @param xmlfile 文件输入流
     * @return 集合对象
     */
    public Properties getProperties(File xmlfile) {
        Properties properties = new Properties();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlfile);
            //获取根节点
            Element root = document.getDocumentElement();
            NodeList node = root.getElementsByTagName("entry");
            for (int i = 0; i < node.getLength(); i++) {
                if (null == node.item(0).getFirstChild()) {
                    properties.setServer_url("");
                } else {
                    properties.setServer_url(node.item(0).getFirstChild().getNodeValue());
                }

                if (null == node.item(1).getFirstChild()) {
                    properties.setApplication_root("");
                } else {
                    properties.setApplication_root(node.item(1).getFirstChild().getNodeValue());
                }

                if (null == node.item(2).getFirstChild()) {
                    properties.setUsb_root("");
                } else {
                    properties.setUsb_root(node.item(2).getFirstChild().getNodeValue());
                }

                if (null == node.item(3).getFirstChild()) {
                    properties.setGet_schedule("");
                } else {
                    properties.setGet_schedule(node.item(3).getFirstChild().getNodeValue());
                }

                if (null == node.item(4).getFirstChild()) {
                    properties.setNotify_status("");
                } else {
                    properties.setNotify_status(node.item(4).getFirstChild().getNodeValue());
                }

                if (null == node.item(5).getFirstChild()) {
                    properties.setSchedule_download("");
                } else {
                    properties.setSchedule_download(node.item(5).getFirstChild().getNodeValue());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }





}
