import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileRename {

    public static void main(String[] args) {
        multiRename("E:\\开发软件\\360加固\\360jiagubao1537\\jiagu\\output\\18682333090");
    }

    /**
     * 批量重命名
     * @param path
     */
    public static void multiRename(String path){
        File file = new File(path);
        boolean isDirectory = file.isDirectory();

        /** 如果不是文件夹，就返回* */
        if(!isDirectory){
            System.out.println(path + "不是一个文件夹！");
            return;
        }

        String[] files = file.list();
        File f = null;
        String oldFileName = ""; //之前的名字
        int count = 0; // 下划线个数统计
        int validApkcount = 0; // 有效apk包个数
        /** 循环遍历所有文件* */
        for(String fileName : files){
            oldFileName = fileName;
            count = fileName.length() - fileName.replace("_", "").length();
            if(fileName.endsWith(".apk") && count ==9){

                String tempFileName = getText(fileName, 0, 6) + ".apk"; // 截取有用的文件名
                String channelStr = getText(fileName, 8, 9); // 加固后的渠道
                String originalChannelStr = getText(fileName,3, 4); // 加固前的渠道

                fileName = tempFileName.replace(originalChannelStr, channelStr);
                System.out.print(count + ":");
                System.out.print(originalChannelStr + ":");
                System.out.print(channelStr + ":");
                System.out.print(tempFileName + ":");
                System.out.print(fileName + "\n");


                f = new File(path + "\\" + oldFileName); //输出地址和原路径保持一致
                f.renameTo(new File(path + "\\" +  fileName));
                validApkcount++;
            }else{
                f = new File(path + "\\" + oldFileName);
                f.delete();
            }

        }

        if(validApkcount > 0){
            System.out.println("恭喜，批量重命名成功！" + "共完成" + validApkcount +"个安卓包。");
            System.out.println("并已删除无效文件！");
        }else{
            System.out.println("未找到匹配的文件，请查看文件名是否符合线上环境包命名规则！");
            System.out.println("清空加固输出文件目录，请重新加固！");
        }

    }

    private static String getText(String str, int st, int ed) {
        int n = 0;
        int pos = -1;
        while (n < st) {
            pos = str.indexOf("_", pos + 1);
            if (pos == -1) {
                return "";
            }
            n++;
        }
        int st_pos = pos;
        while (n < ed) {
            pos = str.indexOf("_", pos + 1);
            if (pos == -1) {
                return str.substring(pos + 1);
            }
            n++;
        }
        return str.substring(st_pos + 1, pos);
    }

}
