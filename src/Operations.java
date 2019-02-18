/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ahmed
 */
public class Operations {

    public double getMoyenne(Object[][] data, int att){
        double som = 0;
        for (int i=0;i<data.length;++i) som += Double.parseDouble(String.valueOf(data[i][att]));
        return (som/data.length);
    }


    public double getMedian(Object[][] data, int att){
        ArrayList list = new ArrayList();
        list.add(data[0][att]);
        for (int i=1;i<data.length;++i){
            int j=0;
            boolean b=false;
            while (j<list.size() && b==false){
                if (Double.parseDouble(String.valueOf(data[i][att]))>Double.parseDouble(String.valueOf(list.get(j)))) ++j;
                else b=true;
            }
            if (b==true) list.add(j,data[i][att]);
            else list.add(data[i][att]);
        }
        if (list.size()%2==0) return (Double.parseDouble(String.valueOf(list.get(list.size()/2-1))) + Double.parseDouble(String.valueOf(list.get(list.size()/2))))/2;
        return (double) list.get(list.size()/2);
    }
   
    public  Object[] getMode(Object[][] data, int att){
        ArrayList elt = new ArrayList();
        ArrayList<Integer> freq = new ArrayList<Integer>();
        for (int i=0;i<data.length;++i){
            if (!elt.contains(data[i][att])){
                elt.add(data[i][att]);
                freq.add(1);
            }
            else{
                int index = elt.indexOf(data[i][att]);
                freq.set(index,freq.get(index)+1);
            }
        }
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        int max=freq.get(0);
        indexList.add(0);
        for (int i=1;i<freq.size();++i){
            if (freq.get(i)>max){
                indexList.clear();
                indexList.add(i);
                max = freq.get(i);
            }
            else if (freq.get(i)==max) indexList.add(i);
        }
        Object[] modes = new Object[indexList.size()];
        for (int i=0;i<modes.length;++i){
            modes[i] = elt.get(indexList.get(i));
        }
        return modes;
    }   
}
