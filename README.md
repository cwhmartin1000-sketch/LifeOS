# LifeOS
**Personal Growth Operating System**

透過「Goal → Habit / Task → Practice Log → Skill evidence → Review → Next challenge」，培養習慣、精進能力並反覆運用已學知識。此 repo 同時用來提升 **Senior Java/Spring Backend** 能力。

## 現況
第一版 domain：Goal、Habit、Task、Skill、Practice Log。
目前僅實作 Sprint 1 Task；其他模組僅定義產品職責，沒有空的 CRUD 或預先設計關聯。
採單一部署的 Spring Boot modular monolith，逐步練習可靠的 API、交易、資料建模與測試。

技術：Java 21、Spring Boot 3.5.16、Maven、Spring MVC、Bean Validation、JPA、Flyway、H2。
Spring Boot 版本依 [官方系統需求](https://docs.spring.io/spring-boot/3.5/system-requirements.html) 固定；升級須驗證，不自動追最新。
H2 file 模式供本機開發，並非 PostgreSQL production 行為的替代驗證。

## 本機執行
在工作電腦獨立學習，先閱讀 [任務 001](docs/tasks/001-development-environment.md)，完成後填寫 [學習紀錄](docs/worklogs/template.md)。進度見 [progress](docs/progress.md)，兩台電腦交付方式見 [GitHub workflow](docs/github-workflow.md)。

先安裝 JDK 21 與 Maven 3.6.3+，讓 JAVA_HOME 指向 JDK，確認 java -version 與 mvn -version。

```shell
mvn verify
mvn spring-boot:run
```

在此 repo 根目錄執行。服務預設 http://127.0.0.1:8080，只綁定本機。
資料位於 data/（已忽略）；重新啟動會保留。測試使用獨立記憶體 DB。
尚未提供 Maven Wrapper，需先有 Maven。沒有認證與多使用者隔離，不可直接公開部署。

## Task API
| Method | Path | 行為 |
|---|---|---|
| POST | /api/v1/tasks | 建立，201 + Location |
| GET | /api/v1/tasks/{id} | 取得，找不到 404 |
| GET | /api/v1/tasks?status=TODO&page=0&size=20 | 分頁清單 |
| PUT | /api/v1/tasks/{id} | 替換標題、描述、期限 |
| PATCH | /api/v1/tasks/{id}/status | 改變狀態 |
| DELETE | /api/v1/tasks/{id} | 刪除，204；不存在 404 |

建立 / 更新 body：
```json
{"title":"練習 Spring Transaction","description":"比較 isolation level","dueDate":"2026-10-10"}
```
狀態 body：`{"status":"DONE"}`。可用 TODO、IN_PROGRESS、DONE，允許重開與重複設定。
title 必填且最多 200 字；description 最多 2000 字，省略/null 視為空字串；dueDate 可省略/null。
PUT 省略 description 或 dueDate 會清空；狀態由 PATCH 修改。過去日期允許，用來表達逾期任務。
page 從 0 開始，size 1–100；依 createdAt 降序、id 次序排序。
清單回傳 items/page/size/totalElements/totalPages；錯誤使用 application/problem+json。
格式 / 驗證錯誤 400，不存在 404，偵測同時寫入衝突 409。版本鎖避免重疊交易寫入，尚無 ETag，無法防止所有 stale-client 覆寫。

## 專案導覽
- AGENTS.md：Codex 的工作規範。
- docs/product.md：願景、domain、假設與待定需求。
- docs/architecture.md 與 docs/adr/：邊界、決策與 trade-off。
- docs/roadmap.md：逐步技術演進。
- docs/sprints/sprint-1.md：當前工作與驗收。
- docs/development.md：接續開發及驗證限制。
- src/main/java/com/lifeos/task：Task 垂直切片。
- src/main/java/com/lifeos/shared：共用 HTTP 錯誤處理。

Git repo 已在 lifeos/ 初始化；未設定 remote、未對外發布。
