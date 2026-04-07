# Localization Setup (Database-Based)

This application uses database-driven localization instead of property files. All UI text is loaded dynamically from the database.

---

## 1. Configure Environment Variables

Copy the example file and update it with your database credentials:

```bash
cp .env.example .env
```

Edit `.env`:

```env
DB_URL=jdbc:mariadb://localhost:3306/shopping_cart_localization
DB_USER=root
DB_PASSWORD=your_password
```

---

## 2. Create and Populate Database

Run the provided SQL script:

```bash
mysql -u root -p < sqlscript.sql
```

This script will:

- Create the database `shopping_cart_localization`
- Create required tables:
  - `cart_records`
  - `cart_items`
  - `localization_strings`
- Insert localization data for all supported languages

---

## 3. Supported Languages

The application supports the following languages:

| Language | Code |
|----------|------|
| English  | `en` |
| Finnish  | `fi` |
| Swedish  | `sv` |
| Japanese | `ja` |
| Urdu     | `ur` |

Localization values are stored in the `localization_strings` table.

---

## 4. How Localization Works

1. The application reads the selected language from the GUI
2. It queries the database using `LocalizationService`
3. UI text is loaded as key-value pairs from `localization_strings`
4. All labels, buttons, and messages update dynamically

---

## 5. Running the Application

After setup, run:

```bash
mvn javafx:run
```

---

## 6. Verify Localization

To confirm localization is working:

1. Launch the application
2. Change the language from the dropdown
3. UI text should update instantly

You can also verify stored values directly in the database:

```sql
SELECT * FROM localization_strings;
```

---

## ⚠️ Notes

- `.env` is ignored by Git and **must be created locally** — never commit it
- The database must be running **before** starting the application
- Incorrect credentials will prevent localization from loading
