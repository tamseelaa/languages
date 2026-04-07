CREATE DATABASE IF NOT EXISTS shopping_cart_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;


-- Main cart records
CREATE TABLE IF NOT EXISTS cart_records (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            total_items INT NOT NULL,
                                            total_cost DOUBLE NOT NULL,
                                            language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Individual cart items
CREATE TABLE IF NOT EXISTS cart_items (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          cart_record_id INT,
                                          item_number INT NOT NULL,
                                          price DOUBLE NOT NULL,
                                          quantity INT NOT NULL,
                                          subtotal DOUBLE NOT NULL,
                                          FOREIGN KEY (cart_record_id)
    REFERENCES cart_records(id)
    ON DELETE CASCADE
    );

-- Localization table
CREATE TABLE IF NOT EXISTS localization_strings (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
    );


-----insert------
-- ---------- ENGLISH ----------
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('greet','Welcome to the shopping cart','en'),
                                                              ('instruction','Enter quantity and price','en'),
                                                              ('prompt1','Quantity','en'),
                                                              ('prompt2','Price','en'),
                                                              ('current','Current total','en'),
                                                              ('add','Add item','en'),
                                                              ('subs','Remove item','en'),
                                                              ('proceed','Proceed to payment','en'),
                                                              ('cancel','Cancel','en'),
                                                              ('exit','Thank you for using the shopping cart','en');

-- ---------- FINNISH ----------
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('greet','Tervetuloa ostoskoriin','fi'),
                                                              ('instruction','Syötä määrä ja hinta','fi'),
                                                              ('prompt1','Määrä','fi'),
                                                              ('prompt2','Hinta','fi'),
                                                              ('current','Nykyinen summa','fi'),
                                                              ('add','Lisää tuote','fi'),
                                                              ('subs','Poista tuote','fi'),
                                                              ('proceed','Siirry maksuun','fi'),
                                                              ('cancel','Peruuta','fi'),
                                                              ('exit','Kiitos käytöstä','fi');

-- ---------- SWEDISH ----------
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('greet','Välkommen till kundvagnen','sv'),
                                                              ('instruction','Ange antal och pris','sv'),
                                                              ('prompt1','Antal','sv'),
                                                              ('prompt2','Pris','sv'),
                                                              ('current','Nuvarande total','sv'),
                                                              ('add','Lägg till','sv'),
                                                              ('subs','Ta bort','sv'),
                                                              ('proceed','Fortsätt till betalning','sv'),
                                                              ('cancel','Avbryt','sv'),
                                                              ('exit','Tack för att du använder appen','sv');

-- ---------- JAPANESE ----------
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('greet','ショッピングカートへようこそ','ja'),
                                                              ('instruction','数量と価格を入力してください','ja'),
                                                              ('prompt1','数量','ja'),
                                                              ('prompt2','価格','ja'),
                                                              ('current','合計','ja'),
                                                              ('add','追加','ja'),
                                                              ('subs','削除','ja'),
                                                              ('proceed','支払いへ進む','ja'),
                                                              ('cancel','キャンセル','ja'),
                                                              ('exit','ご利用ありがとうございました','ja');

-- ---------- URDU ----------
INSERT INTO localization_strings (`key`, value, language) VALUES
                                                              ('greet','شاپنگ کارٹ میں خوش آمدید','ur'),
                                                              ('instruction','مقدار اور قیمت درج کریں','ur'),
                                                              ('prompt1','مقدار','ur'),
                                                              ('prompt2','قیمت','ur'),
                                                              ('current','کل رقم','ur'),
                                                              ('add','شامل کریں','ur'),
                                                              ('subs','کم کریں','ur'),
                                                              ('proceed','ادائیگی کے لیے آگے بڑھیں','ur'),
                                                              ('cancel','منسوخ کریں','ur'),
                                                              ('exit','استعمال کرنے کا شکریہ','ur');