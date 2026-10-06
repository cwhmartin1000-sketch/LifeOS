# ADR 0001：單一應用與 Task 垂直切片
狀態：採用。日期：2026-10-06。

問題：產品與資料模型仍在探索，需建立可持續學習且可交付的後端。
選項：微服務；按技術層切整個專案；依功能 package 的單一應用。
選擇：Java 21 / Spring Boot 3.5.16 / Maven；功能分模組、package-private 邊界。
trade-off：簡化部署、交易與除錯，但共享 runtime/DB，模組界線需 review；尚無編譯期獨立 artifact 隔離。
不引入 Modulith/CQRS/Kafka；待真實跨模組事件或邊界失守再評估。

資料先用 H2 file + JPA + Flyway，讓 Sprint 1 不依赖 Docker；代價是 SQL、鎖定與索引行為無法代表 PostgreSQL。
進入 Database Engineering 階段必須新增 PostgreSQL migration 與整合測試，不把 H2 測試當 production 證據。

Task 用樂觀鎖，比悲觀鎖減少等待；代價是寫入衝突需重試，且不保護非重疊 stale-client 更新。
不加 ETag 或 idempotency key，待使用情境需要再設計其協定。
