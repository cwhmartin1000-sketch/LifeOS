# 演進 roadmap
每階段都要有可用功能、測試、trade-off 與學習回顧。這是候選順序，不是一次安裝的清單。

| 階段 | 產品 / 工程觸發點 | 學習与完成證據 |
|---|---|---|
| 1 Task | 最小可用行動管理 | API、驗證、交易、migration、錯誤與測試 |
| 2 核心 domain | 真實使用需要目標、重複行動與練習證據 | 逐個實作 Goal、Habit、Skill、Practice Log，確認責任與關聯 |
| 3 Database Engineering | 資料累積及部署需求 | PostgreSQL、Testcontainers、EXPLAIN ANALYZE、索引、N+1、隔離、備份還原 |
| 4 Redis | 測得讀取或限流瓶頸 | cache invalidation、TTL、失效策略與 DB fallback；先量測 |
| 5 Event-driven | 模組間副作用妨礙主交易 | 先本機事件，驗證 after-commit、失敗語義；需要可靠傳遞才加入 outbox |
| 6 Kafka | 跨程序消費及持久重播需求 | at-least-once、冪等、順序、retry、DLQ、schema evolution |
| 7 Resilience | 出現外部依賴 | timeout、有限 retry/backoff、circuit breaker、故障測試 |
| 8 Observability | 問題需被快速定位 | 結構化 log、metrics、trace、SLO 與可行動告警 |
| 9 Security | 多使用者或對外服務 | 認證、授權、ownership、secret、威脅模型；公開部署之前必須完成 |
| 10 Docker / CI-CD | 可重複部署需求 | 建置、verify gate、image、環境設定、migration、回滾 |
| 11 Microservices | 確認獨立擴展或部署的模組 | 用數據說明拆分收益，接受分散式一致性與維運成本 |
| 12 Kubernetes | 多服務部署且運維收益成立 | probes、resources、rollout、故障演練；不為學名詞先上 K8s |
| 13 AI Integration | 有足夠練習證據與回饋規則 | Coach 建議、eval、隱私、成本/延遲、失敗 fallback、人工接受 |

Security 的時間由對外暴露需求決定，不得機械地等到第 9 階段。
每個 sprint 回答：解決哪個需求？練了哪項能力？如何驗證？何時值得換方案？
