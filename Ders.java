import java.util.ArrayList;

public class Ders {
    private int dersId;
    private String dersAd;
    // Başlangıç değeri 990, ilk yeni eklemede 1000 olacak
    public static int syc = 990; 

    // Yeni ders eklerken kullanılacak 
    public Ders(String dersAd) {
        this.dersId = (syc += 10);
        this.dersAd = dersAd;
    }

    // Dosyadan okurken kullanılacak 
    public Ders(int dersId, String dersAd) {
        this.dersId = dersId;
        this.dersAd = dersAd;
        // Eğer dosyadan gelen ID sayaçtan büyükse, sayacı güncelle ki çakışma olmasın
        if (dersId > syc) {
            syc = dersId;
        }
    }

    // Arama Metodu 
    public static void ara(ArrayList<Ders> Dersler, String dersAd) {
        boolean flag = false;
        for (Ders d : Dersler) {
            if (d.getDersAd().equalsIgnoreCase(dersAd)) {
                flag = true;
                System.out.println("Ders ID: " + d.getDersId() + " - Ders Adı: " + d.getDersAd());
            }
        }
        if (!flag) System.out.println("Ders bulunamadı!");
    }

    // Silme Metodu 
    public static void sil(ArrayList<Ders> Dersler, String dersAd) {
        // Not: İlişkisel kontrol Anasayfa.java içerisinde yapılmalıdır.
        Ders silinecek = null;
        for (Ders d : Dersler) {
            if (d.getDersAd().equalsIgnoreCase(dersAd)) {
                silinecek = d;
                break;
            }
        }
        if (silinecek != null) {
            Dersler.remove(silinecek);
            System.out.println("Ders silindi.");
        } else {
            System.out.println("Ders bulunamadı.");
        }
    }

    // Getter ve Setterlar
    public int getDersId() { return dersId; }
    public void setDersId(int dersId) { this.dersId = dersId; }
    public String getDersAd() { return dersAd; }
    public void setDersAd(String dersAd) { this.dersAd = dersAd; }
}
