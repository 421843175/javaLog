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
    private String prevalue;
    private BufferedWriter fw;
    private BufferedReader fr;
    private int day=10;

    /**
     * @param :
     * @return ������־��յ�����
     * @date: 2022/7/18 16:56
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day: ������־����������
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
     * @param pretext: ��־ÿ��ǰ׺�ַ���
     * @return 
     * @date: 2022/7/18 14:51
     */
    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    /**
     * @param :
     * @return ���๹�캯��������ʹ��������־�ļ�����
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

    /**д����־�ļ�
     * @param str: д����־�ļ����ַ���
     * @return
     * @date: 2022/7/18 15:05
     */
    public void write(String str){
        pz();

        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**������־�����ķ���
     * @param t:Tip�еı�������
     * @param str:��־��Ϣ
     * @return
     * @date: 2022/7/18 15:46
     */
    public void write(Tip t,String str){
        pz();
        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+" "+t+" "+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**�Զ�������ľ�������
     * @param t:�Զ���ı�������
     * @param str:
     * @return
     * @date: 2022/7/18 15:49
     */
    public void write(String t,String str){
        pz();
        try {
            if(clearLog()) fw=new BufferedWriter(new FileWriter(f,false));
            fw=new BufferedWriter(new FileWriter(f,true));
            fw.write(prevalue+" "+t+" "+str+"\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param :
     * @return ��ȡ����־������Ϣ,����һ������
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
 * @return ������ת��Ϊ�ַ���
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
 * @param li: ��������־�ļ�
 * @param t: ɸѡ�ı�������
 * @return ��־�к��и��౨���Ľ��
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
        System.out.printf("\33[33;4m"+prevalue+" "+t+" "+str+"\n");
        else if(t==Tip.ERROR)
            System.out.printf("\33[31;4m"+prevalue+" "+t+" "+str+"\n");
        else if(t==Tip.MESSAGE)
            System.out.printf("\33[34;4m"+prevalue+" "+t+" "+str+"\n");
        else if(t==Tip.MESSAGE)
            System.out.printf("\33[35;4m"+prevalue+" "+t+" "+str+"\n");
    }

/**
 * @param b: ������־�ļ����ƻ���е�ʲôλ��,ע����к�ö���ᱻ�������գ�
 * @param isCopy: trueΪ���ƣ�falseΪ����
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
