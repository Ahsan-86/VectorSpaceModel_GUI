package vectorspacemodel_gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammad-Ahsan
 */
public class VectorSpaceModel_GUI {
    Map<String,LinkedList<Integer>> df_Map;
    Map<String,String> VSM;
    Map<String,Double> idf_Map;
    Map<String,Map<Integer,Double>>  tf_idf_Map;

    Map<String,ArrayList<Double>> Word_tf_idf_Doc;
    Map<Integer,ArrayList<Double>> Doc_Vectors;
    Map<Integer,Double> Doc_Vectors_Mag;
    
    ArrayList<Integer> DocWordsSize = new ArrayList<Integer>();

    ArrayList stopWords;
    ArrayList<String> allTOKENS; 

    String[] finalText;

    Map<Integer,Double> cosineScores;
                    
        
//    public static void main(String[] args) {
//        
//        System.out.println("Please wait for 20-30 seconds...");
//        VectorSpaceModel_K163742 ob = new VectorSpaceModel_K163742();
//        
//        ob.ExtractingStopWords(ob.stopWords);
//        ob.WritingAllTokens(ob.stopWords,ob.DocWordsSize,ob.finalText,ob.allTOKENS);
//        
//        //System.out.println("Total Terms = "+allTOKENS.size());
//        
//        ob.CalculatingDocumentFrequency(ob.allTOKENS,ob.finalText,ob.df_Map);
//        ob.CreatingVectorSpaceModel(ob.allTOKENS,ob.finalText,ob.df_Map,ob.idf_Map,ob.tf_idf_Map,ob.VSM);
//        ob.CreatingVectorsForAllDocuments(ob.DocWordsSize,ob.allTOKENS,ob.finalText,ob.df_Map,ob.tf_idf_Map,ob.Word_tf_idf_Doc,ob.Doc_Vectors,ob.VSM);
//        ob.CalculatingDocumentVectorsMagnitude(ob.DocWordsSize,ob.allTOKENS,ob.Word_tf_idf_Doc,ob.Doc_Vectors,ob.Doc_Vectors_Mag,ob.VSM);
//        
////        System.out.println("\nVector Space Model\n");
////        VSM.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nVector Space Model Size = "+VSM.size()+"\n");
//        
////        System.out.println("-----------------------------------------\nDf Map\n");
////        df_Map.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nDF MAP Size = "+df_Map.size()+"\n");
//        
////        System.out.println("-----------------------------------------\nidf Doc Map\n");
////        idf_Map.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nidf Doc  Size = "+idf_Map.size()+"\n");
//
////        System.out.println("-----------------------------------------\nTf_idf Map\n");
////        tf_idf_Map.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nTF_IDF MAP Size = "+tf_idf_Map.size()+"\n");
//        
////        System.out.println("-----------------------------------------\nWord Tf_idf Doc Map\n");
////        Word_tf_idf_Doc.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nWord Tf_idf Doc  Size = "+Word_tf_idf_Doc.size()+"\n");
//
////        System.out.println("-----------------------------------------\nDoc Vectors\n");
////        Doc_Vectors.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nDoc Vectors Size = "+Doc_Vectors.size()+"\n");
//
////        System.out.println("-----------------------------------------\nDoc Vectors Magnitudes\n");
////        Doc_Vectors_Mag.forEach((key,value) -> System.out.println(key + " = " + value));
////        System.out.println("\nDoc Vectors Magnitudes Size = "+Doc_Vectors_Mag.size()+"\n");
// 
//        ob.QuerySearchingVSM(ob.finalText,ob.allTOKENS,ob.VSM,ob.df_Map,ob.idf_Map,ob.tf_idf_Map,ob.Word_tf_idf_Doc,ob.Doc_Vectors,ob.Doc_Vectors_Mag);
//                    
//    }
//    
    public void ExtractingStopWords(){
        stopWords = new ArrayList();
        File swFile = new File("Stopword-List.txt");        
        Scanner sc1 = null;
        try {
            sc1 = new Scanner(swFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VectorSpaceModel_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(sc1.hasNext()){
            stopWords.add(sc1.next());
        }
    }
    
    public void WritingAllTokens(){
        allTOKENS = new ArrayList<String>();
        finalText = new String[50];
        
        String fileName = "";
        String[] words = null;
                
        for(int i=1; i<51; i++){
            try {
                fileName = i+".txt";
                
                finalText[i-1] = "";
                
                File file = new File(fileName);
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();
                
                String str = new String(data, "UTF-8");
                str = str.replaceAll("[^\na-zA-Z0-9 -]","");
                str = str.replaceAll("[\\-]"," ");
                //str = str.replaceAll("[\\.]"," ");
                str = str.replaceAll("[\\\n]"," ");
                str = str.toLowerCase();
                
                words = str.split(" ");
        
                for(int w=0; w<words.length; w++){
                    if( !stopWords.contains(words[w]) ){
                        finalText[i-1] += words[w] + " ";
                    }
                }
                
//                System.out.println("file "+i+" "+finalText[i-1]);
//                newFilebr.write(i+" "+finalText[i-1]+"\n"+"end"+i+"\n");

//                String[] fileWords = finalText[i-1].split(" ");
//                System.out.println(i+" "+fileWords.length);
//                newFilebr.append(i+" "+fileWords.length+"\n");
                
//                StringTokenizer st1 = new StringTokenizer(finalText[i-1]," ",true);
//                while (st1.hasMoreTokens()){
//                      
//                    while(st1.hasMoreTokens()){
//                        String t = st1.nextToken();
//                        allTOKENS.add(t);
//                    }
//                }
                //System.out.println("File"+(i)+"\n"+finalText[i-1]);
                String[] tokens = finalText[i-1].split(" ");
                DocWordsSize.add(tokens.length);
                for(int t=0; t<tokens.length; t++){
                    String ST = tokens[t];
                    if(!allTOKENS.contains(ST)){
                        allTOKENS.add(ST);
                    }
                }
                
    
                }   
                catch (FileNotFoundException ex) {
                Logger.getLogger(VectorSpaceModel_GUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                Logger.getLogger(VectorSpaceModel_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        Collections.sort(allTOKENS);
    }
    
    public void CalculatingDocumentFrequency(){
        df_Map = new TreeMap<String,LinkedList<Integer>>();
        
        Iterator<String> allTOKENSIterator = allTOKENS.iterator();
        while(allTOKENSIterator.hasNext()){
            String word = allTOKENSIterator.next();
            LinkedList<Integer> df_Values = new LinkedList<Integer>();
            
            for(int f = 1; f < 51; f++)
            {
                if (finalText[f-1].contains(" "+word+" ") == true)
                {
                        df_Values.addLast(f);
                }
            }
            df_Map.put(word, df_Values);
        }
    }
    
    public void CreatingVectorsForAllDocuments(){
        Word_tf_idf_Doc = new TreeMap<String,ArrayList<Double>>();
        Doc_Vectors = new HashMap<Integer,ArrayList<Double>>();
        
        int i=0;
        //Creating Each Word -> Doc=>tf_idf
        Iterator<String> allTOKENSIterator = allTOKENS.iterator();
        while(allTOKENSIterator.hasNext()){
            String word = allTOKENSIterator.next();
            Map<Integer,Double> tf_idf_Map_temp = new HashMap<Integer,Double>();
            Set<Integer> docs_list = new HashSet<Integer>();
            tf_idf_Map_temp = tf_idf_Map.get(word);
            docs_list =  tf_idf_Map_temp.keySet(); 
            ArrayList<Double> TDocs = new ArrayList<Double>();
            
            for(i=1; i<51; i++){
                if(docs_list.contains(i)){
                    TDocs.add(tf_idf_Map_temp.get(i));
                }else{
                    TDocs.add(0.0);
                }
            }
            Word_tf_idf_Doc.put(word, TDocs);
        }
        
        //Creating Vectors for Each Doc
        //Iterator<String> uniqueTOKENSIterator2 = uniqueTOKENS.iterator();
        for(i=1; i<51; i++){
            ArrayList<Double> DocVectorValues = new ArrayList<Double>();
            Iterator<String> allTOKENSIterator2 = allTOKENS.iterator();
            while(allTOKENSIterator2.hasNext()){
                String word = allTOKENSIterator2.next();
                ArrayList<Double> TermVectorValues = new ArrayList<Double>();
                TermVectorValues = Word_tf_idf_Doc.get(word);
                DocVectorValues.add(TermVectorValues.get(i-1));
            }
            Doc_Vectors.put(i, DocVectorValues);
        }
    }
    
    public void CalculatingDocumentVectorsMagnitude(){
        Doc_Vectors_Mag = new HashMap<Integer,Double>();
        
        double sum = 0.0;
        double mag = 0.0;
        for(int i=1; i<51; i++){
            ArrayList<Double> DocVectorValues = new ArrayList<Double>();
            DocVectorValues = Doc_Vectors.get(i);
            for(int j=0; j<DocVectorValues.size(); j++){
                sum += Math.pow(DocVectorValues.get(j),2);
            }
            mag = Math.sqrt(sum);
            mag = Math.round(mag * 100000.0) / 100000.0;
            Doc_Vectors_Mag.put(i, mag);
            sum=0.0;
        }
    }
    
    public void CreatingVectorSpaceModel() {
        VSM = new TreeMap<String,String>();
        idf_Map = new TreeMap<String,Double>();
        tf_idf_Map = new TreeMap<String,Map<Integer,Double>>();
        
        Iterator<String> allTOKENSIterator = allTOKENS.iterator();
        while(allTOKENSIterator.hasNext()){
            String word = allTOKENSIterator.next();
            LinkedList<Integer> df_Values = new LinkedList<Integer>();
           
            int count = 0;
            int df = 0;
            String doc_pos = "";
            
//            for(int f = 1; f < 51; f++)
//            {
//                if (finalText[f-1].contains(" "+word+" ") == true)
//                {
//                        Postings.addLast(f);
//                }
//            }
            df_Values = df_Map.get(word);
            df = df_Values.size();
            doc_pos = String.valueOf(df)+"\n";
            int p=1,q = 0;
            Map<Integer,Double> temp_tfidf = new HashMap<Integer,Double>();
            //Map<Integer,Double> temp_idf = new HashMap<Integer,Double>();
            double Aidf = 0.0;
            for(p=1; p<=51; p++){
                if(q<df && p == df_Values.get(q)){
                    int dfValue = df_Values.get(q);
                    q++;

                    String tokens[] = finalText[p-1].split(" ");
                    for (int i = 0; i<tokens.length; i++)  
                    { 
                        if (word.equals(tokens[i])){ 
                            count++;
                        }
                    }
                    //count = (int) (1 + Math.log10(count));
                    double idf = Math.log10(50.0/df);
                    idf = Math.round(idf * 100000.0) / 100000.0;
                    Aidf = idf;
                    double tf_idf = count*idf;
                    tf_idf = Math.round(tf_idf * 100000.0) / 100000.0;
                    doc_pos += String.valueOf(p)+".txt"+"("+count+","+tf_idf+")";
                    //temptf_Map.put(p, count);
                    count = 0;
                    temp_tfidf.put(dfValue, tf_idf);
                    //temp_idf.put(dfValue, idf);
                    doc_pos += ";";
                    doc_pos += " ";

                }else{
                    
                }
            }
            tf_idf_Map.put(word, temp_tfidf);
            idf_Map.put(word, Aidf);
            //tf_Map.put(word, temptf_Map);
            
            VSM.put(word, doc_pos);
        }
    }
    
    public void QuerySearchingVSM(String text){
        //final double ALPHA = 0.005;
        cosineScores = new HashMap<Integer,Double>();
            //String text = MenuVSM();
            text = text.toLowerCase();
            
                text = text.replaceAll("[^a-z ]","");
                //System.out.println("Query = "+text);
                try {
                    String[] words = text.split(" ");
                    //Calculating Documents
                    LinkedList<Integer>[] DocsList = new LinkedList[words.length];
                    for(int w=0; w<words.length; w++){
                        DocsList[w] = df_Map.get(words[w]);
                        //TDocSize += DocsList[w].size();
                    }
                    LinkedList<Integer> TDocsList = new LinkedList<>();
                    int j=0;
                    while(j<DocsList.length){
                        for(int i=0; i<DocsList[j].size(); i++){
                            TDocsList.add(DocsList[j].get(i));
                        }
                        j++;
                    }
                    j=0;
                    LinkedList<Integer> TDocsListFinal = new LinkedList<>();
                    for(int i=0; i<TDocsList.size(); i++){
                        if( !(TDocsListFinal.contains(TDocsList.get(i))) ){
                            TDocsListFinal.add(TDocsList.get(i));
                        }
                    }
                    
                    //Query Vector
                    ArrayList<Double> QueryVector = new ArrayList<Double>();
                    Iterator<String> allTOKENSIterator = allTOKENS.iterator();
                    while(allTOKENSIterator.hasNext()){
                        String word = allTOKENSIterator.next();
                        int count = 0;
                       
                        for (int i=0; i<words.length; i++)  
                        { 
                            if (word.equals(words[i])){ 
                                count++;
                            }
                        }
                        //count = (int) (1 + Math.log10(count));
                        double idf = idf_Map.get(word);
                        double tf_idf = count*idf;
                        tf_idf = Math.round(tf_idf * 100000.0) / 100000.0;
                        QueryVector.add(tf_idf);
                        count = 0;
                    }
                    
                    //Query Vector Magnitude
                    double QueryVectorMag = 0.0;
                    double sum = 0.0;
                    for(int i=0; i<QueryVector.size(); i++){
                        sum += Math.pow(QueryVector.get(i),2);
                    }
                    QueryVectorMag = Math.sqrt(sum);
                    QueryVectorMag = Math.round(QueryVectorMag * 100000.0) / 100000.0;
                    
                    //Cosine Similarity
                    //String dq = "q";
                    //double d_q = 0.0,cos_S = 0.0,mul = 0.0;
                    //ArrayList<Double> cosineScores = new ArrayList<Double>();
                    //Map<Integer,Double> cosineScores = new HashMap<Integer,Double>();
                    //Map<Integer,Double> resultantVectors = new HashMap<Integer,Double>();
                    
                    for(int d=0; d<TDocsListFinal.size(); d++){
                        double d_q = 0.0,cos_S = 0.0,mul = 0.0;
                        j = TDocsListFinal.get(d);
                        
                        ArrayList<Double> docVector = new ArrayList<Double>();
                        ArrayList<Double> ResultantVector = new ArrayList<Double>();
                        double ResultantVectorSum = 0.0;
                        docVector = Doc_Vectors.get(j);
                        for(int k=0; k<QueryVector.size(); k++){
                            mul = docVector.get(k) * QueryVector.get(k);
                            ResultantVector.add(mul);
                        }
                        
//                        sum = 0.0;
                        for(int k=0; k<ResultantVector.size(); k++){
                            ResultantVectorSum += ResultantVector.get(k);
                        }
//                        ResultantVectorSum = Math.sqrt(sum);
                        ResultantVectorSum = Math.round(ResultantVectorSum * 100000.0) / 100000.0;
                        d_q = QueryVectorMag * Doc_Vectors_Mag.get(j);
                        try{
                            cos_S = ResultantVectorSum/d_q;
                            cos_S = Math.round(cos_S * 100000.0) / 100000.0;
                            //System.out.println("d_q "+cos_S+" ->j"+j);
                            cosineScores.put(j, cos_S);
                        }catch(Exception e){
                            System.out.println("Divide by Zero Error!");
                        }
                    }
                    cosineScores = sortByValue(cosineScores);
//                    double maxcS = 0.0;
//                    ArrayList<Double> TDocsListSorted = new ArrayList<>();
//                    //LinkedList<Integer> TDocsListSorted1 = new LinkedList<>();
//                    Set<Entry<Integer, Double>> entries = cosineScores.entrySet();
//                    TDocsListSorted = 
                    
//                    System.out.println(cosineScores.size()+" documents");  
//                    cosineScores.forEach((key,value) -> System.out.println(key + " = " + value));
//                    System.out.println("--------------------AFTER ALPHA Filtering.---------------------\n");
//                    int docs = 0;
//                    for(Integer key : cosineScores.keySet()){
//                        if(cosineScores.get(key)>=ALPHA){ 
//                            docs++;
//                        }
//                    }
//                    System.out.println(docs+" documents");
//                    for(Integer key : cosineScores.keySet()){
//                        if(cosineScores.get(key)>ALPHA){ 
//                            System.out.println(key + " = " + cosineScores.get(key));
//                        }
//                    }
                    
                }catch(Exception e){
                    System.out.println("Term not in Vector Space Model!");
                }
            
        
    }
    
    //---------------------copied function from internet
    public static Map<Integer, Double> sortByValue(Map<Integer, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Double>> list =
                new LinkedList<Map.Entry<Integer, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    
    public static String MenuVSM()
    {
        System.out.println("\nEnter Query:  \t OR \tEnter 0 to Exit\n");
        Scanner input = new Scanner(System.in);
        String text = input.nextLine();
        return text;
    }
}
