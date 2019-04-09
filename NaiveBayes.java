package Naivebayes;

/**
 *
 * @author Danar
 */

import java.util.Scanner;
public class NaiveBayes {
   
    static FiturNaive[] data;
    static double luasBangunan, f_makanDaging;
    static String bahanBakarMemasak, jenisLantai, kategori;
    
    public double rerataLuasBangunan(String kat){
        double jml = 0;
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                jml += data[i].getLuasBangunan();
                n++;
            }
        }
        return jml/n;
    }
    
    public double rerataFrekMakanDaging(String kat){
        double jml = 0;
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                jml += data[i].getF_MakanDaging();
                n++;
            }
        }
        return jml/n;
    }
    
    public double varianLuasBangunan(String kat){
        double jml = 0;
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                jml += Math.pow(data[i].getLuasBangunan() - rerataLuasBangunan(kat), 2);
                n++;
            }
        }
        return jml/n;
    }
    
    public double varianFrekMakanDaging(String kat){
        double jml = 0;
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                jml += Math.pow(data[i].getF_MakanDaging()- rerataFrekMakanDaging(kat), 2);
                n++;
            }
        }
        return jml/n;
    }
    
    public double likehoodLuasBangunan(String kat){
        double lh = 0, pangkat = 0;
        pangkat = -(Math.pow(luasBangunan - rerataLuasBangunan(kat), 2) / (2 * varianLuasBangunan(kat)));
        lh = (1 / (Math.sqrt(2 * Math.PI * varianLuasBangunan(kat))) * Math.pow(Math.E, pangkat));
        return lh;
    }
    
    public double likehoodFrekMakanDaging(String kat){
        double lh = 0, pangkat = 0;
        pangkat = -(Math.pow(f_makanDaging - rerataFrekMakanDaging(kat), 2) / (2 * varianFrekMakanDaging(kat)));
        lh = (1 / (Math.sqrt(2 * Math.PI * varianFrekMakanDaging(kat))) * Math.pow(Math.E, pangkat));
        return lh;
    }

    public double priorKategori(String kat){
        double p = 0, prior = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                p++;
            }
        }
        return prior = p/data.length;
    }
    
    public double likehoodJenisLantai(String kat){
        double pembilang = 0, n = 0, posterior = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                if (data[i].getJenisLantai().equalsIgnoreCase(jenisLantai)) {
                    pembilang++;            
                }
                n++;
            }  
        }
        posterior = pembilang/n;
        return posterior;
    }
    
    public double likehoodBahanBakarMemasak(String kat){
        double pembilang = 0, n = 0, posterior = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getKategori().equalsIgnoreCase(kat)) {
                if (data[i].getBbm().equalsIgnoreCase(bahanBakarMemasak)) {
                    pembilang++;
                }
                n++;
            }
        }
        posterior = pembilang/n;
        return posterior;
    }

    public double posteriorLuasBangunan(String kat){
        double posterior = 0;
        posterior = likehoodLuasBangunan(kat) * priorKategori(kat);
        return posterior;
    }
    
    public double posteriorFrekMakanDaging(String kat){
        double posterior = 0;
        posterior = likehoodFrekMakanDaging(kat) * priorKategori(kat);
        return posterior;
    }
    
    //Mengkategorikan MISKIN, SEDANG, KAYA
    public double posteriorMiskin(){
        double posterior = likehoodLuasBangunan("Miskin")*likehoodBahanBakarMemasak("Miskin")*
                likehoodJenisLantai("Miskin")*likehoodFrekMakanDaging("Miskin")*priorKategori("Miskin");
        
        return posterior;
    }
    
    public double posteriorSedang(){
        double posterior = likehoodLuasBangunan("Sedang")*likehoodBahanBakarMemasak("Sedang")*
                likehoodJenisLantai("Sedang")*likehoodFrekMakanDaging("Sedang")*priorKategori("Sedang");
        return posterior;
    }
    
    public double posteriorKaya(){
        double posterior = likehoodLuasBangunan("Kaya")*likehoodBahanBakarMemasak("Kaya")*
                likehoodJenisLantai("Kaya")*likehoodFrekMakanDaging("Kaya")*priorKategori("Kaya");
        return posterior;
    }
    
    public String Kategori(){
        if (posteriorMiskin()>posteriorSedang() && posteriorMiskin()>posteriorKaya()) {
            kategori = "Miskin";
        }else if(posteriorSedang()>posteriorMiskin() && posteriorSedang()>posteriorKaya()){
            kategori = "Sedang";
        }else{
            kategori = "Kaya";
        }
        return kategori;
    }
    
    public void display(){
        System.out.println("Posterior Miskin : "+posteriorMiskin());
        System.out.println("Posterior Sedang : "+posteriorSedang());
        System.out.println("Posterior Kaya : "+posteriorKaya());
        System.out.println("Kategori : "+Kategori());
    }
    

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        NaiveBayes nb = new NaiveBayes();
        System.out.print("Masukkan Luas Bangunan (1-100) : ");
        luasBangunan = in.nextDouble();
        System.out.print("Masukkan BBM (KayuBakar/GasLPG/KomporListrik) : ");
        bahanBakarMemasak = in.next();
        System.out.print("Masukkan Jenis Lantai (Tanah/Plester/Ubin) : ");
        jenisLantai = in.next();
        System.out.print("Masukkan Frekuensi Makan Daging (1-100) : ");
        f_makanDaging = in.nextDouble();     
        System.out.println("========================HASIL=======================");
        

        FiturNaive fn1 = new FiturNaive(9, "KayuBakar", "Ubin", 3, "Sedang");
        FiturNaive fn2 = new FiturNaive(10, "GasLPG", "Ubin", 2, "Sedang");
        FiturNaive fn3 = new FiturNaive(15, "GasLPG", "Plester", 2, "Sedang");
        FiturNaive fn4 = new FiturNaive(30, "GasLPG", "Ubin", 4, "Kaya");
        FiturNaive fn5 = new FiturNaive(16, "Kompor Listrik", "Ubin", 3, "Kaya");
        FiturNaive fn6 = new FiturNaive(25, "GasLPG", "Ubin", 5, "Kaya");
        FiturNaive fn7 = new FiturNaive(9, "GasLPG", "Plester", 0.5, "Miskin");
        FiturNaive fn8 = new FiturNaive(8, "KayuBakar", "Tanah", 1, "Miskin");
        FiturNaive fn9 = new FiturNaive(10, "KayuBakar", "Tanah", 2, "Miskin");
        FiturNaive fn10 = new FiturNaive(14, "GasLPG", "Tanah", 1, "Miskin");
        data = new FiturNaive[]{fn1, fn2, fn3, fn4, fn5, fn6, fn7, fn8, fn9, fn10};

        nb.display();        
    }
}