# 接續開發
在 lifeos/ 工作，先讀 AGENTS.md、查看 git status 與 Sprint 1 checklist。
建議下一個 Codex 任務：「使用 JDK 21 與 Maven 執行 verify，修正實際失敗，再完成 Sprint 1 的重啟持久性與並發衝突驗收；不要增加新 domain 或技術。」

## 初始化驗證記錄（2026-10-06）
執行環境只有 Java 8 JRE，沒有 Maven；尚未編譯、啟動或跑測試。
已靜態檢查檔案與 Git 空白格式，但不能代表 Java 編譯或 integration test 通過。
請安裝 JDK 21（非僅 JRE）與 Maven，設定 JAVA_HOME/PATH 後執行：
```shell
mvn verify
mvn spring-boot:run
```
若 dependency 下載失敗，檢查網路與 Maven repository 設定。不要用降低 Java 版本迴避問題。

## 改架構時
使用 docs/adr/template.md。更新受影響 API、migration、測試與 README。
任何對外部署前必須先完成安全、資料庫與營運缺口的評估。
