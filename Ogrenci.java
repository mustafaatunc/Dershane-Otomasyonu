import java.util.ArrayList;

public class Ogrenci {
    private int ogrId;
    private String ogradSoyad;
    private int ogrYas;
    
    // Ödev gereği bu özellik public bırakıldı veya doğrudan erişime açıldı 
    public ArrayList<Ders> alinanDersler = new ArrayList<Ders>();

    public Ogrenci(int ogrId, String ogradSoyad, int ogrYas) {
        this.ogrId = ogrId;
        this.ogradSoyad = ogradSoyad;
        this.ogrYas = ogrYas;
    }

    // Öğrenci Arama [cite: 69]
    public static void ara(ArrayList<Ogrenci> Ogrenciler, String ogradSoyad) {
        boolean flag = false;
        for (Ogrenci o : Ogrenciler) {
            if (o.getOgradSoyad().equalsIgnoreCase(ogradSoyad)) {
                flag = true;
                System.out.println(o.getOgrId() + "\t" + o.getOgradSoyad() + "\t" + o.getOgrYas());
            }
        }
        if (!flag) System.out.println("Öğrenci bulunamadı!");
    }

    // Öğrenci Silme [cite: 70]
    public static void sil(ArrayList<Ogrenci> Ogrenciler, int ogrId) {
        boolean flag = false;
        for (int i = 0; i < Ogrenciler.size(); i++) {
            if (Ogrenciler.get(i).getOgrId() == ogrId) {
                Ogrenciler.remove(i);
                System.out.println("Öğrenci silindi!");
                flag = true;
                break;
            }
        }
        if (!flag) System.out.println("Öğrenci bulunamadı!");
    }

    // Ayrıntılı Listeleme [cite: 78-79]
    public static void listeleAyrintili(ArrayList<Ogrenci> Ogrenciler) {
        System.out.println("Tüm Öğrenciler ve Aldıkları Dersler");
        for (Ogrenci o : Ogrenciler) {
            System.out.println(o.getOgrId() + " " + o.getOgradSoyad() + " " + o.getOgrYas());
            for (Ders d : o.alinanDersler) {
                System.out.println("\t" + d.getDersId() + " " + d.getDersAd());
            }
        }
    }

    // Ödeme Hesaplama [cite: 96-102]
    public static void odemeHesapla(ArrayList<Ogrenci> Ogrenciler, int id) {
        Ogrenci ogr = null;
        for (Ogrenci o : Ogrenciler) {
            if (o.getOgrId() == id) {
                ogr = o;
                break;
            }
        }

        if (ogr == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        int dersSayisi = ogr.alinanDersler.size();
        double toplamTutar = 0;
        double dersUcreti = 400 * 4; // Aylık (4 haftalık) ücret

        if (dersSayisi <= 1) {
            toplamTutar = dersSayisi * dersUcreti;
            System.out.println("Kampanya yok. Tutar: " + toplamTutar);
        } else if (dersSayisi == 2) {
            // İkinci ders %5 indirimli [cite: 98]
            toplamTutar = dersUcreti + (dersUcreti * 0.95);
            System.out.println("Kampanya 1 uygulandı (%5). Tutar: " + toplamTutar);
        } else if (dersSayisi == 3) {
            // Üçüncü ders %15 indirimli [cite: 99]
            toplamTutar = (dersUcreti * 2) + (dersUcreti * 0.85);
            System.out.println("Kampanya 2 uygulandı (%15). Tutar: " + toplamTutar);
        } else {
            // 3 dersten fazla ise hepsi %10 indirimli [cite: 100]
            toplamTutar = dersSayisi * (dersUcreti * 0.90);
            System.out.println("Kampanya 3 uygulandı (Hepsi %10). Tutar: " + toplamTutar);
        }
    }

    // Getter ve Setterlar
    public int getOgrId() { return ogrId; }
    public void setOgrId(int ogrId) { this.ogrId = ogrId; }
    public String getOgradSoyad() { return ogradSoyad; }
    public void setOgradSoyad(String ogradSoyad) { this.ogradSoyad = ogradSoyad; }
    public int getOgrYas() { return ogrYas; }
    public void setOgrYas(int ogrYas) { this.ogrYas = ogrYas; }
}