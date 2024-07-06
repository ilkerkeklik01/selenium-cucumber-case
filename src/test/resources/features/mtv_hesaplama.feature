Feature: Motorlu Taşıtlar Vergisi Hesaplama

  Scenario: MTV Hesaplama
    Given kullanıcı dijital.gib.gov.tr adresine gider
    When Hesaplamalar tab'ine tıklar
    And Motorlu Taşıtlar Vergisi Hesaplama butonuna tıklar
    And Araç tipi olarak "Panel Van ve Motorlu Karavanlar" seçer
    And Araç Yaşı olarak "1-6" seçer
    And Motor Silindir Hacmi olarak "1.901 ve üstü" seçer
    And Hesapla butonuna tıklar
    Then gelen sonuç ekranının doğruluğunu kontrol eder
    When Temizle butonuna tıklar
    Then sonuç ekranının silindiğini ve veri alanlarının temizlendiğini kontrol eder
