# Chapter 01 Assignment｜Spring Boot 基礎與 Task 模組

> 適用任務：002–006  
> 模式：整章挑戰包。這一章不逐題解鎖；一次完成後統一 Review。

## 目標
這一章不是練習 CRUD，而是驗證你能否用 Senior 工程師角度理解、驗證並評論一個既有 Spring Boot Task 模組。

完成後你應能：
- 追蹤完整 HTTP Request → Controller → Service → Repository → JPA → DB → Response 流程
- 解釋 Controller / Service / Repository 的責任邊界
- 說明 DTO 與 Entity 分離的理由
- 驗證 CRUD、Validation、ProblemDetail、Pagination
- 解釋 Persistence Context、Dirty Checking、Transaction boundary
- 理解並驗證 Optimistic Locking 與 stale update
- 對目前 Task API、測試與 production readiness 做工程判斷

---

## 002｜理解專案與請求流程

挑選：
```http
POST /api/v1/tasks
```

實際追蹤：
```text
HTTP Request
→ Controller
→ Request DTO
→ Service
→ Repository
→ JPA Entity
→ H2
→ Entity
→ Response DTO
→ HTTP Response
```

### 交付
在本章 worklog 中記錄：
1. 每一步對應的 class / method
2. 一張簡單 request-flow 圖
3. 回答：
   - 為什麼 Controller 不直接呼叫 Repository？
   - 為什麼 API DTO 不直接使用 JPA Entity？
   - Spring 如何注入 TaskService？
   - Service 沒有 @Transactional 時，現有程式是否一定會壞？
   - Transaction boundary 為什麼通常放在 Service？

---

## 003｜Task Domain 與 API Contract Review

### Domain Review
檢查 Task 是否可視為 Aggregate Root，並回答：
- 它目前保護哪些 invariant？
- 哪些修改應該只能透過 Task 自身行為完成？
- 現在 Entity 比較像 domain model 還是 data bag？
- `task.setStatus(DONE)` 與 `task.complete()` 的 trade-off 是什麼？

本章不要求為了「更 DDD」而強制重構。

### API Contract Review
檢視：
```text
POST   /api/v1/tasks
GET    /api/v1/tasks/{id}
GET    /api/v1/tasks
PUT    /api/v1/tasks/{id}
PATCH  /api/v1/tasks/{id}/status
DELETE /api/v1/tasks/{id}
```

至少回答：
- POST 成功應回 200 還是 201？是否需要 Location？
- title 空白、title > 200、description > 2000 應如何處理？
- 不存在 UUID 與 UUID 格式錯誤，應各回什麼？
- DELETE 成功後再 GET 應如何？
- 重複 DELETE 的 API semantics 你怎麼看？

---

## 004｜CRUD、Pagination 與 Error Handling 驗證

### 正常流程
實際驗證：
```text
Create
→ Get
→ Update
→ Change status
→ List
→ Delete
→ Get => 404
```

### Validation
至少驗：
```text
title = ""
title = "   "
title > 200
description > 2000
```

記錄：
- HTTP status
- Content-Type
- response body

確認並解釋 `application/problem+json` 的價值。

### Pagination
驗：
```http
GET /api/v1/tasks?page=0&size=5
GET /api/v1/tasks?page=1&size=5
```

再測 `size > 100` 的目前行為。

說明 pagination 除了 DB 效能之外，對 memory、serialization、network、client 與 DoS 風險的影響。

---

## 005｜Persistence、Transaction、Optimistic Lock

### Persistence
找出 local runtime 與 test 的 datasource 設定、Flyway migration 與 H2 模式。

回答：
- 為什麼 test 可以用 memory DB，而 local runtime 用 file DB？
- 為什麼 H2 適合目前階段，但後面仍需要 PostgreSQL？
- 請至少涵蓋 SQL dialect、locking、transaction isolation、query planner、index/constraint behavior。

### Transaction
找出 Service 的 `@Transactional` 使用方式。

解釋：
```java
@Transactional
public void updateTask(...) {
    Task task = repository.findById(...);
    task.changeTitle(...);
}
```

即使沒有再次呼叫 `repository.save(task)`，為什麼仍可能更新 DB？

你必須能說清楚：
- Persistence Context
- Managed Entity
- Dirty Checking
- Transaction commit

### Optimistic Lock
找到 `@Version` 或相對應的 version 欄位。

理解：
```text
A 讀 version=1
B 讀 version=1
A 更新成功 → version=2
B 再更新 → stale update
```

確認目前系統是否能可靠產生：
```text
Optimistic Lock failure → 409 Conflict
```

若現有 automated test 不足，可以補「最小必要測試」。不要為了這題擴大架構。

---

## 006｜Sprint 1 Senior Review

建立：
```text
docs/worklogs/sprint-1-review.md
```

內容至少包含以下六部分。

### 1. Architecture
說明 Controller → Service → Repository → Database 分層解決什麼問題，以及它的成本。

### 2. Transaction
說明 Transaction boundary 為什麼放 Service，並舉一個 Controller 開 Transaction 不佳的例子。

### 3. JPA
說明 Persistence Context、Dirty Checking、Optimistic Locking 三者如何關聯。

### 4. API
列出目前 Task API：
- 做得好的 3 點
- 未來可能改善的 3 點

### 5. Testing
評估目前測試是否足夠。
如果只能新增 3 個測試，說明你會選哪 3 個以及原因。

### 6. Production Readiness
假設 LifeOS 明天有 10 萬使用者，列出至少 5 個目前不能直接上 Production 的具體原因與風險。

---

## Codex 使用規則

可以大量使用 Codex，但採：
```text
Analyze → Explain → You decide → Implement → Verify
```

不要直接下：
```text
幫我完成 002–006
```

建議先問：
```text
請先閱讀 AGENTS.md、docs/sprints/sprint-1.md 與目前 Task 模組。
先不要修改程式。
帶我追蹤 POST /api/v1/tasks 從 Controller → Service → Repository → JPA → DB → Response 的完整流程。
指出相關檔案與 method，但不要替我回答思考題。
```

Optimistic Lock 部分可問：
```text
請檢查目前 optimistic locking 是否有可靠 automated test。
先解釋現有測試是否真的產生 stale update。
如果不足，提出最小測試方案，不要直接修改，等我判斷。
```

---

## 第一章完成條件

- [ ] 能畫出 request flow
- [ ] 能解釋 Controller / Service / Repository responsibility
- [ ] 能說明 DTO 與 Entity 分離
- [ ] CRUD 實際驗證完成
- [ ] Validation / ProblemDetail 驗證完成
- [ ] Pagination 驗證完成
- [ ] 能解釋 Persistence Context
- [ ] 能解釋 Dirty Checking
- [ ] 能解釋 @Transactional boundary
- [ ] 能解釋 optimistic locking
- [ ] 可靠驗證 stale update → 409
- [ ] `mvn verify` 通過
- [ ] `docs/worklogs/sprint-1-review.md` 完成

全部完成後，整章一次 Review；通過後直接進 Chapter 02。
