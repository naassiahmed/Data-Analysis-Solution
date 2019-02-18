/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nazim
 */
public class Apriori {
    
    public String affT(String[] t){
        String aff = "["+t[0];
        for (int i=1;i<t.length;++i){
            aff += ", "+t[i];
        }
        aff += "]";
        return aff;
    }
    
    public String affL(ArrayList<String> l){
        String aff = "";
        for (String s : l){
            aff += s + " ";
        }
        return aff;
    }
	
    public String affLT(ArrayList<String[]> l){
        String aff = "";
        for (String[] t : l){
            aff += affT(t) + "\n";
        }
        return aff;
    }
    
    public String affD(HashMap<String[], Integer> c){
        String aff = "";
        for (String[] t : c.keySet()){
            aff += affT(t) + " : " + String.valueOf(c.get(t)) + "\n";
        }
        return aff;
    }
    
    public HashMap<String[], Integer> copyD(HashMap<String[], Integer> c){
        HashMap<String[], Integer> copy = new HashMap<String[], Integer>();
        for (String[] t : c.keySet()){
            copy.put(t, c.get(t));
        }
        return copy;
    }
    
    public boolean partOf(String s, String[] superS){
        for (String sup : superS){
                if (s.equals(sup)) return true;
        }
        return false;
    }

    public boolean subItem(String[] s1, String[] s2){
        for (String s : s1){
                if(!partOf(s, s2)) return false;
        }
        return true;
    }
    
    public boolean isSame (String[] s1, String[] s2){
        if (s1.length==s2.length && subItem(s1, s2)) return true;
        return false;
    }
	
    public boolean itemExist(String[] s, ArrayList<String[]> items){
        for (String[] t : items){
                if (isSame(s, t)) return true;
        }
        return false;
    }
    
    public ArrayList<String> getUniqueItems(ArrayList<String[]> database){
        ArrayList<String> uniqueItems = new ArrayList<String>();
        for (int i=0;i<database.size();++i){
            for (int j=0;j<database.get(i).length;++j){
                    if (!uniqueItems.contains(database.get(i)[j])) {
                            uniqueItems.add(database.get(i)[j]);
                    }
            }
        }
        return uniqueItems;
    }
    
    public int itemCount(String[] item, ArrayList<String[]> database){
        int cpt = 0;
        for (String[] t : database) if (subItem(item, t)) ++cpt;
        return cpt;
    }
    
    public HashMap<String[], Integer> getItemsCount(ArrayList<String[]> items, ArrayList<String[]> database){
        HashMap<String[], Integer> c = new HashMap<String[], Integer>();
        for (String[] item : items){
                /*int occ = 0;
                for (String[] trans : database){
                        if (subItem(item, trans)) ++occ;
                }*/
                c.put(item, itemCount(item, database));
        }
        return c;
    }
    
    public boolean sameItems(ArrayList<String[]> items, HashMap<String[], Integer> c){
        for (String[] t : c.keySet()){
            if (!items.contains(t)){
                return false;
            }
        }
        return true;
    }
	
    public ArrayList<ArrayList<String[]>> getFrequent(HashMap<String[], Integer> c, int take){
        ArrayList<String[]> frequent = new ArrayList<String[]>();
        ArrayList<String[]> tabu = new ArrayList<String[]>();
        ArrayList<ArrayList<String[]>> result = new ArrayList<ArrayList<String[]>>();

        for (String[] t : c.keySet()){
                if (c.get(t)>=take) frequent.add(t);
                else tabu.add(t);
        }

        result.add(frequent);
        result.add(tabu);

        return result;
    }
    
    public String[] fusion(String[] a, String[] b){
        ArrayList<String> c = new ArrayList<String>();
        for (String s : a) c.add(s);
        for (String s : b){
                if (!c.contains(s)) c.add(s);
        }
        String[] d = new String[c.size()];
        for (int i=0;i<d.length;++i) d[i] = c.get(i);
        return d;
    }
	
    public ArrayList<String[]> getItems(ArrayList<String[]> last, ArrayList<String[]> tabu){
        ArrayList<String[]> items = new ArrayList<String[]>();
        int max = last.get(0).length+1;
        for (int i=0;i<last.size()-1;++i)
                for (int j=i+1;j<last.size();++j){
                        String[] s = fusion(last.get(i), last.get(j));
                        if (s.length==max && !itemExist(s, items)){
                                boolean b = true;
                                int k = 0;
                                while (k<tabu.size() && b==true){
                                        if (subItem(tabu.get(k), s)) b = false;
                                        else ++k;
                                }
                                if (b) items.add(s);
                        }
                }
        return items;
    }
    
    public ArrayList<String[]> getSubsets(String[] pattern, int l){
        
        ArrayList<String[]> subsets = new ArrayList<String[]>();
        
        int[] p = new int[l];
        for (int i=0;i<p.length;++i) p[i] = i;
        
        boolean extract = true;
        
        while(extract){
            ArrayList<String> temp = new ArrayList<String>();
            for (int j=0;j<p.length;++j){
                temp.add(pattern[p[j]]);
            }
            subsets.add(temp.toArray(new String[temp.size()]));
            int k = p.length-1;
            boolean check = true;
            boolean inc = false;

            while(check){
                ++p[k];
                if(p[k]==pattern.length-(p.length-1-k)){
                    if (k==p.length){
                        --k;
                    }
                    else if(k>0){
                        --k;
                        inc = true;
                    }
                    else{
                        check=false;
                        extract=false;
                    }					
                }
                else if (inc==true){
                    for (int i=k+1;i<p.length;++i) p[i] = p[i-1] + 1;
                    inc = false;
                    check = false;
                }
                else check = false;
            }
        }      
                
        return subsets;
    }
    
    public ArrayList<String[]> getAllSubsets(String[] pattern){
        
        ArrayList<String[]> subsets = new ArrayList<String[]>();
        
        for (int i=1;i<pattern.length;++i){
            ArrayList<String[]> temp = getSubsets(pattern, i);
            for (String[] t : temp) subsets.add(t);
        }
        
        return subsets;
    }
    
    public String[] substract(String[] subset, String[] pattern){
        
        //ArrayList<String[]> result = new ArrayList<String[]>();
        String[] result = new String[pattern.length - subset.length];
        
        int i=0;
        for (String s : pattern){
            if (!partOf(s, subset)){
                result[i] = s;
                ++i;
            }
        }
        
        return result;
    }
    
}
