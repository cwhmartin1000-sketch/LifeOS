# LifeOS — Codex 工作規範

## 目標與上下文
LifeOS 是 Personal Growth Operating System：建立習慣、追蹤能力、刻意練習、回顧、把已會的知識投入下一個挑戰。
開發同時是提升 Senior Java/Spring Backend 能力的練習。產品需求與學習目的要分別說清楚。
先讀 README.md、docs/product.md、docs/architecture.md、docs/sprints/sprint-1.md。
每次接續工作先查 git status，保留使用者修改。上層 sources/ 是唯讀同步參考，不可修改。

## 範圍與原則
- 第一版 domain 僅 Goal、Habit、Task、Skill、Practice Log；當前優先 Task。
- 不過度設計，不一次引入所有技術。不預建通用 BaseEntity、通用 Service、空接口、抽象工廠、CQRS 或微服務。
- 採 production-grade 工程習慣的 modular monolith；「production-grade」不代表此版本已可公開上線。
- 按功能分 package；模組內 Entity、Repository、Service 優先 package-private。
- Controller 不直接存取 Repository；Entity 不作 API DTO；交易邊界置於 Service。
- 跨模組只用明確公開的應用 API，禁止跨模組直接存取 Entity 或 Repository。需求出現才增加 API。
- 每次架構變更解釋：問題、可選方案、選擇理由、trade-off、成本、何時重新評估。持久決策寫入 docs/adr/。
- 新增依賴必須有當前需求及驗證方式，不以「以後可能需要」為理由。
- 不自動導入 roadmap 的下一階段。完成當前驗收與回顧後再選下一個小步。
- 未定產品需求先列假設；可逆小決策自行推進，涉及授權、資料損失或對外發布才確認。

## 品質與教學
- 工作電腦無 ChatGPT；docs/tasks/ 的講義必須能獨立操作，包含觀念、命令、驗收、常見問題與思考題。
- 每次只展開當前任務；透過任務分支與 worklog review，再更新 docs/progress.md。不要替使用者完成原本安排他練習的內容，除非他要求代做。
- Java 21、Maven、Spring Boot；維持 constructor injection、輸入驗證、穩定 API、ProblemDetail。
- Schema 只用 Flyway migration，已使用的 migration 不改寫；JPA ddl-auto=validate。
- 查詢須有分頁上限，禁止無上限讀取與在交易外 lazy loading。
- 測試驗證 API 行為、失敗路徑及資料保存，避免只鏡像實作。
- 完成相關變更後執行 mvn verify；不能執行要明確報告原因，不能宣稱測試通過。
- 不提交密碼、token、資料庫檔、產物；不記錄敏感 payload。
- 每次交付簡述改了什麼、原因、驗證、限制，並指出一項值得使用者理解的 backend 概念。
- 大變更拆成可 review 的小增量。修改文件使其反映真實狀態。
