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
public class Kmeans {
    
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
    
    public double getMoyenne(String[][] data, int att, ArrayList<Integer> group){
        double som = 0;
        for (int i=0;i<data.length;++i) if (group.contains(i+1)) som += Double.parseDouble(String.valueOf(data[i][att]));
        return (som/group.size());
    }
    
    public  double getMode(String[][] cData, int att, ArrayList<Integer> group){
        ArrayList elt = new ArrayList();
        ArrayList<Integer> freq = new ArrayList<Integer>();
        for (int i=0;i<cData.length;++i){
            if (group.contains(i+1)){
                if (!elt.contains(cData[i][att])){
                    elt.add(cData[i][att]);
                    freq.add(1);
                }
                else{
                    int index = elt.indexOf(cData[i][att]);
                    freq.set(index,freq.get(index)+1);
                }
            }
        }
        if (!freq.isEmpty()){
            int maxFreq = freq.get(0);
            int maxIndex = 0;
            for (int i=1;i<freq.size();++i){
                if (freq.get(i)>maxFreq){
                    maxFreq = freq.get(i);
                    maxIndex = i;
                }
            }
            return Double.parseDouble(String.valueOf(elt.get(maxIndex)));
        }
        else return 0;
    }
    
    public String[] getCentroid(String[][] data, ArrayList<Integer> group, HashMap<Integer, String[]> info){
        String[] centroid = new String[data[0].length];
        for (int i=0;i<centroid.length;++i){
                if (info.get(i+1)[0].equals("R")) centroid[i] = String.valueOf(getMoyenne(data, i, group));
                else centroid[i] = String.valueOf(getMode(data, i, group));
        }
        return centroid;
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
        Random r = new Random();
        ArrayList result = new ArrayList();
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i=0;i<k;++i){
            int j = r.nextInt(kData.length);
            while (randoms.contains(j)) j = r.nextInt(kData.length);
            randoms.add(j);
        }
        
        String[][] centroids = new String[k][kData[0].length];
        for (int i=0;i<randoms.size();++i){
            for (int j=0;j<centroids[0].length;++j){
                centroids[i][j] = kData[randoms.get(i)][j];
            }
        }
        
        double[][] distances = new double[kData.length][centroids.length];
        
        for (int i=0;i<distances.length;++i){
            for (int j=0;j<distances[0].length;++j){
                distances[i][j] = distance(kData[i], centroids[j], info);
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
            
            for (int i=0;i<centroids.length;++i){
                centroids[i] = getCentroid(kData, groups.get(i), info);
            }
            
            for (int i=0;i<distances.length;++i){
                for (int j=0;j<distances[0].length;++j){
                    distances[i][j] = distance(kData[i], centroids[j], info);
                }
            }            
            
            for (int i=0;i<groups.size();++i) groups.get(i).clear();
            
            for (int i=0;i<distances.length;++i){
                int minIndex = getMinIndex(distances[i]);
                groups.get(minIndex).add(i+1);
            }
        }
        
        result.add(groups);
        result.add(centroids);
        
        return result;
    }
    
}
