package jupiter;

import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {
    private Date da;
    private File f;
    private String pretext="[yyyy-MM-dd HH:mm:ss.SSS]";
    private String pretip="";
    private String prevalue;
    private BufferedWriter fw;
    private BufferedReader fr;
    private int day=10;
    
    
    public String getPretip() {
        return pretip;
    }

    public void setPretip(String pretip) {
        this.pretip = pretip;
    }

    /**
     * @param :
     * @return 返回日志清空的天数
     * @date: 2022/7/18 16:56
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day: 设置日志多少天后清空
     * @return
     * @date: 2022/7/18 16:55
     */
    public void setDay(int day) {
        this.day = day;
    }

    public String getPretext() {
        return pretext;
    }

    /**
     * @param pretext: 日志每句前缀字符串
     * @return 
     * @date: 2022/7/18 14:51
     */
    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    /**
     * @param :
     * @return 该类构造函数仅建议使用于无日志文件需求
     * @date: 2022/7/18 16:25
     */
    public Log() {
    }

    public Log(String filename)
    {
        f=new File(filename);
    }


    protected void finalize(int San) {
        if(fw!=null) {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fr!=null) {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private void pz()
    {
        da=new Date();
        DateFormat a=DateFormat.getDateInstance();
        SimpleDateFormat df=new SimpleDateFormat(pretext);
        prevalue=df.format(da);
    }

    /**写入日志文件
     * @param str: 写入日志文件的字符串
     * @return
     * @date: 2022/7/18 15:05
     */
    public void write(String str){
        pz();

        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+" "+pretip+" "+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**具有日志报警的方法
     * @param t:Tip中的报警级别
     * @param str:日志信息
     * @return
     * @date: 2022/7/18 15:46
     */
    public void write(Tip t,String str){
        pz();
        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+" "+t+" "+pretip+" "+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**自定义情况的警报方法
     * @param t:自定义的报警级别
     * @param str:
     * @return
     * @date: 2022/7/18 15:49
     */
    public void write(String t,String str){
        pz();
        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+" "+t+" "+pretip+" "+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param :
     * @return 读取的日志所有信息,返回一个集合
     * @date: 2022/7/18 15:52
     */
    public LinkedList<String> read(){
        LinkedList<String> ll=new LinkedList<>();
        String str;
        try {
            fr=new BufferedReader(new FileReader(f));
            try {
                while((str=fr.readLine())!=null)
                {
                    ll.add(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  ll;
    }

/**
 * @param li:
 * @return 将集合转化为字符串
 * @date: 2022/7/18 16:11
 */
    public String read(LinkedList<String> li){
        StringBuffer sbu=new StringBuffer();
        for(String l:li)
        {
            sbu.append(l+"\n");
        }
        return new String(sbu);
    }


/**
 * @param li: 读到的日志文件
 * @param t: 筛选的报警级别
 * @return 日志中含有该类报警的结果
 * @date: 2022/7/18 16:21
 */
    public String select(LinkedList<String> li,Tip t){
        StringBuffer sbu=new StringBuffer();
        for(String l:li)
        {
            if(l.indexOf(t.toString())!=-1)
            {
                sbu.append(l+"\n");
            }
        }
        return new String(sbu);
    }

    public void printf(String str)
    {
        pz();
        System.out.print(prevalue+str+"\n");
    }

    public void printf(Tip t,String str){
        pz();
        if(t==Tip.WARRING)
        System.out.printf("\33[33;4m"+prevalue+" "+t+" "+pretip+" "+str+"\n");
        else if(t==Tip.ERROR)
            System.out.printf("\33[31;4m"+prevalue+" "+t+" "+pretip+" "+str+"\n");
        else if(t==Tip.MESSAGE)
            System.out.printf("\33[34;4m"+prevalue+" "+t+" "+pretip+" "+str+"\n");
        else if(t==Tip.EXCEPTION)
            System.out.printf("\33[35;4m"+prevalue+" "+t+" "+pretip+" "+str+"\n");
        else
            System.out.printf("\33[30;4m"+prevalue+" "+t+" "+pretip+" "+str+"\n");
    }

/**
 * @param b: 将该日志文件复制或剪切到什么位置,注意剪切后该对象会被垃圾回收！
 * @param isCopy: true为复制，false为剪切
 * @return 
 * @date: 2022/7/18 16:35
 */
    public void cocu(String b,boolean isCopy){
        try {
            fr=new BufferedReader(new FileReader(f));
            fw=new BufferedWriter(new FileWriter(b));
            String str="";
            while((str=fr.readLine())!=null)
            {
                fw.write(str);
                fw.newLine();
                fw.flush();
            }
            fw.close();
            fr.close();
            System.gc();
            if(!isCopy){
                f.delete();
                f=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean clearLog(){
        try {
            try {
                fr = new BufferedReader(new FileReader(f));
            }catch (FileNotFoundException fe)
            {
                return false;
            }
            String bj=fr.readLine();
           SimpleDateFormat sdf=new SimpleDateFormat(this.pretext);
           Long t1=sdf.parse(bj).getTime();
            Long t2= new Date().getTime();
            if((t2-t1)/1000/60/60/24>this.day)
            {
                return true;
            }
        return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
