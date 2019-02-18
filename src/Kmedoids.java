/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Nazim
 */
public class Kmedoids {
    
    public double d(String x, String y, String[] info){
        if (x.equals("out") || y.equals("out")) return 1;
        else if (info[0].equals("R")){
            return Math.abs((Double.parseDouble(x)-Double.parseDouble(y)))/(Double.parseDouble(info[1])-Double.parseDouble(info[2]));
        }
        else if (info[0].equals("O")){
            return Math.abs((Double.parseDouble(x)-Double.parseDouble(y)))/(Double.parseDouble(info[1])-1);
        }
        else{
            if (x.equals(y)) return 0;
            else return 1;
        }
    }
    
    public double distance(String[] indiv1, String[] indiv2, HashMap<Integer, String[]> info){ 
        
        double result = 0;
        
        for (int i=0;i<indiv1.length;++i){
            result += d(indiv1[i], indiv2[i], info.get(i+1));
        }
        
        return result/indiv1.length;
    }
    
    public int getMinIndex(double[] a){
        double x = a[0];
        int index = 0;
        for (int i=1;i<a.length;++i){
            if (a[i] < x){
                x = a[i];
                index = i;
            }
        }
        return index;
    }
    
    public ArrayList<Integer> getMedoids(String[][] kData, HashMap<Integer, String[]> info, ArrayList<ArrayList<Integer>> groups, ArrayList<Integer> medId){
        ArrayList<Integer> newMeds = new ArrayList<Integer>();
        for (int i=0;i<groups.size();++i){
            double min = -1;
            int id = -1;
            for (int j=0;j<groups.get(i).size();++j){
                double d = 0;              
                for (int k=0;k<groups.get(i).size();++k){
                    if (k!=j){
                        d += distance(kData[groups.get(i).get(j)-1], kData[groups.get(i).get(k)-1], info);
                    }
                }
                if (min==-1) {min = d; id = j;}
                else if (d<min) {min = d; id = j;}
            }
            newMeds.add(id);
        }
        return newMeds;
    }
    
    public boolean groupSame(ArrayList<Integer> a, ArrayList<Integer> b){
        if (a.isEmpty() && b.isEmpty()) return true;
        else if ((a.isEmpty() && !b.isEmpty()) || (!a.isEmpty() && b.isEmpty())) return false;
        else{
            for (int x : a){
                if (!b.contains(x)) return false;
            }
            return true;
        }
    }
    
    public boolean allGroupSame(ArrayList<ArrayList<Integer>> a, ArrayList<ArrayList<Integer>> b){
        for (int i=0;i<a.size();++i){
            if (!groupSame(a.get(0), b.get(0))) return false;
        }
        return true;
    }
    
    public double groupSim(ArrayList<Integer> a, ArrayList<Integer> b){
        double som = 0;
        if (a.size()>=b.size()){
            for (int x : a) if (b.contains(x)) ++som;
            return som/a.size();
        }
        else {
            for (int x : b) if (a.contains(x)) ++som;
            return som/b.size();
        }
    }
    
    public ArrayList<ArrayList<Integer>> groupCopy(ArrayList<ArrayList<Integer>> a){
        ArrayList<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<a.size();++i){
            copy.add(new ArrayList<Integer>());
            for (int j=0;j<a.get(i).size();++j){
                copy.get(i).add(a.get(i).get(j));
            }
        }
        return copy;
    }
    
    public ArrayList algorithm(String[][] kData, HashMap<Integer, String[]> info, int k){
        
        ArrayList result = new ArrayList();
        
        Random r = new Random();
        ArrayList<Integer> medId = new ArrayList<Integer>();
        for (int i=0;i<k;++i){
            int j = r.nextInt(kData.length);
            while (medId.contains(j)) j = r.nextInt(kData.length);
            medId.add(j);
        }
        
        String[][] medoids = new String[k][kData[0].length];
        for (int i=0;i<medId.size();++i){
            for (int j=0;j<medoids[0].length;++j){
                medoids[i][j] = kData[medId.get(i)][j];
            }
        }
        
        double[][] distances = new double[kData.length][medId.size()];
        
        for (int i=0;i<distances.length;++i){
            for (int j=0;j<distances[0].length;++j){
                distances[i][j] = distance(kData[i], kData[medId.get(j)], info);
            }
        }
        
        ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<k;++i){
            groups.add(new ArrayList<Integer>());
        }
        
        ArrayList<ArrayList<Integer>> lastGroups = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<k;++i){
            lastGroups.add(new ArrayList<Integer>());
        }
        
        for (int i=0;i<distances.length;++i){
            int minIndex = getMinIndex(distances[i]);
            groups.get(minIndex).add(i+1);
        }
        
        while (!allGroupSame(groups, lastGroups)){
            lastGroups = groupCopy(groups);
            
            medId = getMedoids(kData, info, groups, medId);
        }
        
        for (int i=0;i<distances.length;++i){
            for (int j=0;j<distances[0].length;++j){
                distances[i][j] = distance(kData[i], kData[medId.get(j)], info);
            }
        }
        
        for (int i=0;i<groups.size();++i) groups.get(i).clear();

        for (int i=0;i<distances.length;++i){
            int minIndex = getMinIndex(distances[i]);
            groups.get(minIndex).add(i+1);
        }
        result.add(groups);
        result.add(medId);
        
        return result;
    }
    
}
