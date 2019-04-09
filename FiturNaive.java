package Naivebayes;
/**
 *
 * @author Danar
 */
public class FiturNaive {
    private int luasBangunan;
    private String bbm, jenisLantai;
    private double f_MakanDaging;
    private String kategori;

    public FiturNaive(int luasBangunan, String bbm, String jenisLantai, double f_MakanDaging, String kategori) {
        this.luasBangunan = luasBangunan;
        this.bbm = bbm;
        this.jenisLantai = jenisLantai;
        this.f_MakanDaging = f_MakanDaging;
        this.kategori = kategori;
    }

    public int getLuasBangunan() {
        return luasBangunan;
    }

    public String getBbm() {
        return bbm;
    }

    public String getJenisLantai() {
        return jenisLantai;
    }

    public double getF_MakanDaging() {
        return f_MakanDaging;
    }

    public String getKategori() {
        return kategori;
    }    
}
