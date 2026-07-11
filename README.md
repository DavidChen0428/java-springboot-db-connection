# SpringBoot 在資料庫連線介紹

在 SpringBoot 連線資料庫處理資料或是查詢資料是有很多種方式，這篇文章將會介紹 SpringBoot 連線資料庫的幾種方式，並且比較各種方式的優缺點。

## 資料庫連線種類

- **JDBC Template** <br>
    - 特色 : 底層封裝 <br>
    - SQL 撰寫方式 : 原生 SQL <br>
    - 優點 : 效能最高 <br>
    - 缺點 : 手動處理 Mapping、程式碼冗長

---

- **Spring Data JPA (Hibernate)** <br>
    - 特色 : 以物件為中心的完全 ORM <br>
      不需要寫 SQL，只要定義好實體 (Entity) 的關聯 <br>
      Create, Read, Update, Delete 自動搞定 <br>
    - SQL 撰寫方式 : 自動生成 / JPQL <br>
    - 優點 : 快速開發、跨資料庫相容性高、物件導向思維 <br>
    - 缺點 : 內部機制複雜、容易踩到 N+1 效能坑、快取/延遲載入<br>
    - 適合誰 : 業務邏輯複雜、表結構多但著重快速迭代的企業級微服務或B2M/B2B 系統。

---

- **MyBatis** <br>
    - 特色 : 將 SQL 的掌控權完全交還給開發者 <br>
      透過 XML 實現動態 SQL <br>
    - SQL 撰寫方式 : XML 檔案 / 註解 <br>
    - 優點 : SQL 與程式碼分離、高度客製化 SQL 極方便 <br>
    - 缺點 : 需維護大量 XML，缺乏編譯期的型別安全檢查 <br>
    - 適合誰 : 資料庫需要高度最佳化、經常需要寫複雜大 SQL、或是資料庫管理員 (DBA) 嚴格管控 SQL 效能的專案。

---

- **Spring Data JDBC** <br>
  - 特色 : 拒絕魔法的 ORM <br>
    它保留了 Spring Data Repository 的便利性（如 `findById`），但砍掉了 Hibernate
  - 複雜的狀態轉換與一級快取。 <br>
  - SQL 撰寫方式 : 自動生成 / 原生 SQL <br>
  - 優點 : 概念簡單、無快取/無 Session 負擔、直球對決 <br>
  - 缺點 : 不支援複雜的關聯映射 (如關聯多國語言表)，需手動補足 <br>
  - 適合誰 : 想擺脫 Hibernate 繁瑣設定，追求架構透明、簡單好維護的小型專案。

---

- **jOOQ (Java Object Oriented Querying)** <br>
  特色 : 透過 Codegen 讀取 DB Schema 並產生 Java 類別。你是在用 Java 寫
  SQL，享受程式碼自動補全的快感，徹底告別拼錯欄位名稱的低級錯誤。 <br>
  SQL 撰寫方式 : Java 流式 API (Fluent) <br>
  優點 : 型別安全，欄位打錯在編譯期就報錯，兼顧 SQL 掌控度與安全 <br>
  缺點 : 需依賴外掛生成 Code，商業進階功能 (如 Oracle/SQL Server) 需付費 <br>
  適合誰 : 注重代碼質量、排斥 Hibernate 的黑盒子、但又**想要 SQL 擁有型別安全 (Type-Safe)** 的中大型專案。

---

- **Spring Data R2DBC** <br>
  特色 : 響應式基礎建設。利用事件驅動和異步 IO，讓單一執行緒不需等待資料庫回傳即可繼續處理其他請求。 <br>
  SQL 撰寫方式 : 自動生成 / 原生 SQL <br>
  優點 : 非阻塞 (Non-blocking)，高併發、高吞吐量場景效能極佳 <br>
  缺點 : 生態系較新，不支援傳統的事務機制與封裝，思維門檻高 <br>
  適合誰 : 使用 Spring WebFlux 架構、需要處理高併發、即時串流、物聯網 (IoT) 數據接收等高吞吐量場景。

---

# 如何選擇合適的連線方式

當你在面對新專案開發時，可以依據以下思維進行技術挑選：

1. **專案是否採用響應式 (Reactive/WebFlux) 架構？**
    * 💡 **是** ➡️ 唯一選擇 **Spring Data R2DBC**。
    * 💡 **否** ➡️ 前往第 2 步。
2. **團隊的核心痛點與偏好是什麼？**
    * ➡️ *想用最快速度開發，物件關聯清晰* ➡️ **Spring Data JPA**
    * ➡️ *希望完全掌控 SQL，需要動態拼接 XML* ➡️ **MyBatis-Plus**
    * ➡️ *排斥 ORM 黑盒子，但希望欄位更動時編譯器能自動檢查錯字* ➡️ **jOOQ**
    * ➡️ *想要 JPA 的便利，但想要絕對的簡單與直觀* ➡️ **Spring Data JDBC**