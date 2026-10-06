# 架構
單一 Maven artifact、單一 Spring Boot 應用、依功能分 package：
com.lifeos.task 為第一個模組；goal、habit、skill、practice 尚未建立 runtime 類別。
package-private 封裝是目前的邊界工具；尚未引入 Spring Modulith 或 ArchUnit，跨模組互動出现時再評估自動邊界檢查。

TaskController → TaskService（交易邊界）→ TaskRepository → H2。
Task 負責狀態與資料；HTTP request/response record 與 persistence entity 分離。
shared 僅收容已存在的共通問題，不作所有模組的抽象基底。

Flyway 管理 schema；Hibernate 驗證；關閉 Open Session in View；DTO 在交易內產生。
版本欄位提供 optimistic locking；API 不暴露 version，因此僅處理真正重疊寫入。
分頁限制與一致排序控制讀取量。現階段偏移分頁不保證並發新增時的快照一致性。

## 上線之前的缺口
目前是工程起點，非完成的 production 系統。尚缺 PostgreSQL 整合驗證、認證與資料隔離、秘密管理、備份/還原、TLS/部署、監控與負載驗證。
具體導入條件見 roadmap；不為了掛上 production-grade 名稱而一次加入全部。
