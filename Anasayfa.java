import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Anasayfa {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ArrayList<Ogrenci> Ogrenciler = new ArrayList<>();
        ArrayList<Ders> Dersler = new ArrayList<>();

        // Dosya Yollarını Düzenleme 
        File ogrenciFile = new File("ogrenci.txt");
        File dersFile = new File("ders.txt");

        // 1. Önce Dersleri Yükle 
        if (dersFile.exists()) {
            BufferedReader brDers = new BufferedReader(new FileReader(dersFile));
            String line;
            while ((line = brDers.readLine()) != null) {
                String[] parts = line.split("%");
                if (parts.length >= 2) {
                    Dersler.add(new Ders(Integer.parseInt(parts[0]), parts[1]));
                }
            }
            brDers.close();
        }

        // 2. Öğrencileri Yükle ve Derslerini Eşleştir 
        if (ogrenciFile.exists()) {
            BufferedReader brOgr = new BufferedReader(new FileReader(ogrenciFile));
            String line;
            Ogrenci sonEklenenOgrenci = null;

            while ((line = brOgr.readLine()) != null) {
                String[] parts = line.split("%");
                
                if (line.startsWith("+")) { // Yeni Öğrenci Satırı
                    int id = Integer.parseInt(parts[0].substring(1)); // '+' işaretini atla
                    String adSoyad = parts[1];
                    int yas = Integer.parseInt(parts[2]);
                    sonEklenenOgrenci = new Ogrenci(id, adSoyad, yas);
                    Ogrenciler.add(sonEklenenOgrenci);
                    
                } else if (line.startsWith("*") && sonEklenenOgrenci != null) { // Ders Satırı
                    // Dosyada sadece ID ve Ad var, biz bellekteki Ders nesnesini bulup ekleyelim
                    int dersId = Integer.parseInt(parts[0].substring(1)); // '*' işaretini atla
                    String dersAd = parts[1];
                    // Ders nesnesini oluşturup öğrenciye ekle
                    sonEklenenOgrenci.alinanDersler.add(new Ders(dersId, dersAd));
                }
            }
            brOgr.close();
        }

        boolean kontrol = true;
        while (kontrol) {
            System.out.println("\n--- DERSHANE OTOMASYONU ---");
            System.out.println("1- Ders Ekle");
            System.out.println("2- Ders Listele");
            System.out.println("3- Ders Ara");
            System.out.println("4- Ders Sil");
            System.out.println("5- Öğrenci Ekle");
            System.out.println("6- Öğrenci Ara");
            System.out.println("7- Öğrenci Sil");
            System.out.println("8- Öğrenci Listele");
            System.out.println("9- Öğrencileri Ayrıntılı Listele");
            System.out.println("10- Öğrenci Ödeme Hesapla");
            System.out.println("11- Çıkış");
            System.out.print("Seçiminiz: ");
            
            int secim = scan.nextInt();
            scan.nextLine(); // Dummy read

            switch (secim) {
                case 1: // Ders Ekle [cite: 39]
                    System.out.print("Ders Adı: ");
                    String yeniDersAd = scan.nextLine();
                    Dersler.add(new Ders(yeniDersAd));
                    System.out.println("Ders Eklendi.");
                    break;

                case 2: // Ders Listele [cite: 40]
                    System.out.println("ID\tDers Adı");
                    for (Ders d : Dersler) {
                        System.out.println(d.getDersId() + "\t" + d.getDersAd());
                    }
                    break;

                case 3: // Ders Ara
                    System.out.print("Aranacak Ders Adı: ");
                    Ders.ara(Dersler, scan.nextLine());
                    break;

                case 4: // Ders Sil (Öğrenci kontrolü ile) 
                    System.out.print("Silinecek Ders Adı: ");
                    String silDersAd = scan.nextLine();
                    boolean dersiAlanVar = false;

                    // Dersi alan öğrenci var mı kontrolü
                    for (Ogrenci o : Ogrenciler) {
                        for (Ders d : o.alinanDersler) {
                            if (d.getDersAd().equalsIgnoreCase(silDersAd)) {
                                dersiAlanVar = true;
                                break;
                            }
                        }
                    }

                    if (dersiAlanVar) {
                        System.out.println("HATA: Bu dersi alan öğrenciler var, silinemez! ");
                    } else {
                        Ders.sil(Dersler, silDersAd);
                    }
                    break;

                case 5: // Öğrenci Ekle (ID Kontrolü ve Ders Seçimi ile) 
                    System.out.print("ID: ");
                    int yeniId = scan.nextInt();
                    scan.nextLine();

                    // ID Kontrolü
                    boolean idVar = false;
                    for (Ogrenci o : Ogrenciler) {
                        if (o.getOgrId() == yeniId) {
                            idVar = true;
                            break;
                        }
                    }
                    if (idVar) {
                        System.out.println("Bu ID ile kayıtlı öğrenci var!");
                        break;
                    }

                    System.out.print("Ad Soyad: ");
                    String yeniAdSoyad = scan.nextLine();
                    System.out.print("Yaş: ");
                    int yeniYas = scan.nextInt();
                    
                    Ogrenci yeniOgrenci = new Ogrenci(yeniId, yeniAdSoyad, yeniYas);

                    // Ders Seçimi Döngüsü
                    System.out.println("Öğrencinin alacağı dersleri seçin (Çıkış için 0):");
                    while (true) {
                         // Mevcut dersleri göster
                        for (Ders d : Dersler) {
                            System.out.println(d.getDersId() + "- " + d.getDersAd());
                        }
                        System.out.print("Ders ID giriniz: ");
                        int secilenDersId = scan.nextInt();
                        if (secilenDersId == 0) break;

                        boolean dersBulundu = false;
                        for(Ders d : Dersler) {
                            if(d.getDersId() == secilenDersId) {
                                yeniOgrenci.alinanDersler.add(d); // Burada Ders objesi kopyalanabilir veya ref verilebilir
                                System.out.println(d.getDersAd() + " eklendi.");
                                dersBulundu = true;
                                break;
                            }
                        }
                        if(!dersBulundu) System.out.println("Geçersiz Ders ID.");
                    }
                    
                    Ogrenciler.add(yeniOgrenci);
                    System.out.println("Öğrenci ve dersleri kaydedildi.");
                    break;

                case 6: // Öğrenci Ara
                    System.out.print("Ad Soyad: ");
                    Ogrenci.ara(Ogrenciler, scan.nextLine());
                    break;

                case 7: // Öğrenci Sil
                    System.out.print("Silinecek ID: ");
                    Ogrenci.sil(Ogrenciler, scan.nextInt());
                    break;

                case 8: // Öğrenci Listele
                    System.out.println("Tüm Öğrenciler");
                    for (Ogrenci o : Ogrenciler) {
                        System.out.println(o.getOgrId() + "\t" + o.getOgradSoyad() + "\t" + o.getOgrYas());
                    }
                    break;

                case 9: // Ayrıntılı Listele 
                    Ogrenci.listeleAyrintili(Ogrenciler);
                    break;

                case 10: // Ödeme Hesapla
                    System.out.print("Hesaplanacak Öğrenci ID: ");
                    Ogrenci.odemeHesapla(Ogrenciler, scan.nextInt());
                    break;

                case 11: // Çıkış ve Dosyaya Yazma 
                    // Öğrencileri Yaz
                    BufferedWriter bwOgr = new BufferedWriter(new FileWriter(ogrenciFile));
                    for (Ogrenci o : Ogrenciler) {
                        bwOgr.write("+" + o.getOgrId() + "%" + o.getOgradSoyad() + "%" + o.getOgrYas() + "\n");
                        for (Ders d : o.alinanDersler) {
                            bwOgr.write("*" + d.getDersId() + "%" + d.getDersAd() + "\n");
                        }
                    }
                    bwOgr.close();

                    // Dersleri Yaz
                    BufferedWriter bwDers = new BufferedWriter(new FileWriter(dersFile));
                    for (Ders d : Dersler) {
                        bwDers.write(d.getDersId() + "%" + d.getDersAd() + "\n");
                    }
                    bwDers.close();
                    
                    System.out.println("Bilgiler dosyaya kaydedildi. Çıkılıyor...");
                    kontrol = false;
                    break;

                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
        scan.close();
    }
}
