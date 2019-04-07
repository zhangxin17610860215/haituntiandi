package haituntiandi.com.haituntiandi.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 创建MVP 的Activity Code。
 * 使用方法：
 * 执行main().
 */
public class MVPCodeGenerator {

    public static void main(String[] args) {
        try {
            addMVP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMVP() throws Exception {

        List<String> models = new ArrayList<>();
        models.add("SettingReceivingAddress");

        String app = "app";
        String packageName = "haituntiandi.com.haituntiandi";
//        String projectPath = "D:/TAMC_Wallet/TamcWallet";
        String projectPath = "E:\\Android\\Project\\haituntiandi"; // 此处换成当前项目所在根目录
        String templatePath = projectPath + "/app/src/main/assets/";
        String aPath = projectPath + "/app/src/main/java/" + packageName.replaceAll("\\.", "/") + "/" + app;
        String cPath = projectPath + "/app/src/main/java/" + packageName.replaceAll("\\.", "/") + "/" + app;
        String lPath = projectPath + "/app/src/main/res/layout/";
        String pPath = projectPath + "/app/src/main/java/" + packageName.replaceAll("\\.", "/") + "/" + app;
        System.out.println("*****************************************************");
        System.out.println(aPath);
        System.out.println(cPath);
        System.out.println(lPath);
        System.out.println(pPath);
        System.out.println("*****************************************************");


        //输出Activity
        contract(models, packageName, templatePath, aPath, "Activity.java", "/template_a");

        //Contract
        contract(models, packageName, templatePath, cPath, "Contract.java", "/template_c");

        //Layout(template_l)
        contract(models, packageName, templatePath, lPath, "template_l.xml", "/template_l");

        //Presenter
        contract(models, packageName, templatePath, pPath, "Presenter.java", "/template_p");


        for (String str : models) {
            String bigName = str;
            String smallName = str.toLowerCase();

            String androidManifestPath = projectPath + "/app/src/main/AndroidManifest.xml";
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(androidManifestPath));
            Element applicationElement = document.getRootElement().element("application");

            boolean isSameAndroidName = false;
            String androidNameValue = "." + app + "." + smallName + "." + bigName + "Activity";
            for (Iterator it = applicationElement.elementIterator(); it.hasNext(); ) {
                Element childElement = (Element) it.next();
                Attribute attribute = childElement.attribute("name");
                String anv = attribute.getValue();
                System.out.println(anv);
                if (childElement.getQualifiedName().equals("activity") && anv.equals(androidNameValue)) {
                    isSameAndroidName = true;
                }
            }

            if (!isSameAndroidName) {
                Element addElement = applicationElement.addElement("activity");
                addElement.addAttribute("android:name", androidNameValue);
                addElement.addAttribute("android:screenOrientation", "portrait");

                //指定文件输出的位置
                FileOutputStream out = new FileOutputStream(androidManifestPath);
                // 指定文本的写出的格式：
                OutputFormat format = OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
                format.setEncoding("UTF-8");
                //1.创建写出对象
                XMLWriter writer = new XMLWriter(out, format);
                //2.写出Document对象
                writer.write(document);
                //3.关闭流
                writer.close();
            }

        }
        System.out.println("*****************************************************");
    }

    private static void contract(List<String> models, String packageName, String templatePath, String rootPath, String postfix, String templateName) {
        for (String str : models) {
            String bigName = str;
            String smallName = str.toLowerCase();

            String newFilePath;
            if (postfix.endsWith("template_l.xml")) {
                newFilePath = rootPath + smallName + "_activity" + ".xml";
            } else if (postfix.endsWith("template_l_flushview.xml")) {
                newFilePath = rootPath + "activity_" + smallName + "_flushview.xml";
            } else {
                newFilePath = rootPath + "/" + smallName + "/" + str + postfix;
            }

            File file = new File(templatePath + templateName);
            String temp = file2String(file);
            temp = temp.replace("{$packageName}", packageName);
            temp = temp.replace("{$Name}", bigName);
            temp = temp.replace("{$name}", smallName);
            temp = temp.replace("{$flushview}", "activity_" + smallName + "_flushview");
            System.out.println(newFilePath);
            string2File(temp, newFilePath);
        }
    }

    public static String file2String(File file) {
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    private static void string2File(String str, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
                out.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}