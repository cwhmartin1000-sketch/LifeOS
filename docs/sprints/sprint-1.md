# Sprint 1 — Task 模組
目標：提供本機單人可用的 Task API，練習一條完整的 Spring backend 垂直切片。
排除：登入、Goal 關聯、提醒、重複 Task、事件、cache、前端、部署。

## 已建立（尚未執行測試）
- POST/GET/list/PUT/PATCH status/DELETE。
- UUID、TODO/IN_PROGRESS/DONE、可選 dueDate、時間戳、版本鎖。
- title/description 驗證，分頁上限、status filter、穩定排序。
- 交易服務、Flyway schema、H2 file persistence、ProblemDetail。
- MockMvc 完整 lifecycle 與錯誤路徑測試。

## 驗收
- [ ] JDK 21 + Maven 上執行 mvn verify 通過（含 migration 與 JPA schema validation）。
- [ ] 手動啟動、建立任務、重啟後讀取確認資料仍在。
- [ ] 201/Location、200、204、400、404 行為符合 README。
- [ ] 用兩個重疊交易補上版本衝突的可靠測試，確認 409。
- [ ] 回顧交易邊界、DTO/Entity 分離、H2 的限制與 optimistic locking。

不得在上述項目未完成時宣稱 Sprint 1 完成。
下一個小步先解決工具環境及驗收，再由真實使用選 Goal 或 Practice Log；不要同時開四個模組。
