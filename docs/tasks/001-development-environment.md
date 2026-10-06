# 任務 001：在工作電腦啟動 LifeOS
Sprint 1 / 任務 1。狀態：待開始。預估 60–120 分鐘，可分段完成。
目標：不用 ChatGPT，也能取得專案、完成測試並啟動 Task API。

## 完成後你會理解
JDK 是編譯與執行 Java 的工具；Maven 負責依賴、建置與測試。
JAVA_HOME 指向 JDK 根目錄，PATH 決定命令使用哪個工具。
mvn verify 會經過編譯、測試與打包；BUILD SUCCESS 才是建置成功的證據。
Spring Boot 啟動成功與測試通過是兩個不同的驗收項目。

## 0. 準備
在公司允許的位置保存個人專案，例如 C:\Projects\lifeos。
安裝 Git、JDK 21、Maven 3.6.3+。若使用公司既有工具，先確認版本。
使用 GitHub 頁面的 Code → HTTPS 取得 clone URL。下方尖括號內容必須換成真實值：
```powershell
git clone <你的GitHub-repo-HTTPS-URL>
cd lifeos
git switch -c task/001-environment
```
若已 clone，先確認 git status 沒有未保存修改，再在 main 執行 git pull --ff-only。
GitHub 密碼不能直接當 Git HTTPS 密碼；需要時使用官方登入流程，勿把 token 寫進 URL 或紀錄。

## 1. 檢查工具（約 15 分鐘）
```powershell
git --version
java -version
javac -version
mvn -version
```
驗收：java、javac 及 Maven 顯示使用 Java 21。
若要設定 JAVA_HOME，值是 JDK 根目錄，不包含 bin；PATH 加入 JDK 的 bin 與 Maven 的 bin。
設定後重新開啟 PowerShell。不要把工具安裝位置寫死在專案文件中。

## 2. 執行測試（約 20–40 分鐘）
在含 pom.xml 的 repo 根目錄：
```powershell
mvn verify
```
第一次會下載依賴，可能較慢。完成後查看 target/surefire-reports/。
驗收：BUILD SUCCESS，測試沒有 failures 或 errors。
若失敗，記錄第一個具體錯誤與完整命令；不要先改 Spring Boot 版本或跳過測試。
本任務不要求你自行理解所有測試程式，下一個任務才會追蹤請求流程。

## 3. 啟動並建立 Task（約 20 分鐘）
第一個 PowerShell 視窗：
```powershell
mvn spring-boot:run
```
保持開啟。第二個 PowerShell 視窗：
```powershell
$taskPayload = @{
    title = '完成 LifeOS 環境任務'
    description = '在工作電腦完成驗收'
} | ConvertTo-Json
$taskCreated = Invoke-RestMethod -Method Post -Uri 'http://127.0.0.1:8080/api/v1/tasks' -ContentType 'application/json; charset=utf-8' -Body ([System.Text.Encoding]::UTF8.GetBytes($taskPayload))
$taskCreated
Invoke-RestMethod -Uri "http://127.0.0.1:8080/api/v1/tasks/$($taskCreated.id)"
```
驗收：回傳 id、title、status=TODO；第二次查詢取得相同 id。
API 啟動於本機，先不用開放公司網路存取。

## 4. 驗證重啟後資料仍在（約 10 分鐘）
記錄 Task id。第一個視窗按 Ctrl+C，再在同一 repo 根目錄執行：
```powershell
mvn spring-boot:run
```
使用剛才 id 查詢：
```powershell
Invoke-RestMethod -Uri 'http://127.0.0.1:8080/api/v1/tasks/替換成剛才的UUID'
```
驗收：重啟後仍能找到任務。測試使用記憶體 DB；本機執行使用 data/ 的檔案 DB。
若換了工作目錄，可能使用另一個 data/。不要將 data/ 上傳 GitHub。

## 5. 寫紀錄並交付（約 15 分鐘）
複製 docs/worklogs/template.md 為 docs/worklogs/001-environment.md。
填入版本、測試摘要、啟動與重啟結果、阻礙、三個思考題。
通過全部验收才更新 docs/progress.md 為「待 review」；有阻礙則標記「進行中」。
```powershell
git status
git add docs/worklogs/001-environment.md docs/progress.md
git diff --cached
git commit -m "docs: record task 001 environment verification"
git push -u origin task/001-environment
```
若 Git 要求作者資訊，設定自己的 name/email；email 可使用 GitHub 帳號提供的 noreply 地址。
在 GitHub 建立 Pull Request 到 main，把工作紀錄摘要放進描述。先留待 review，不需自行合併。
回家後告訴 Codex repo 或 PR URL，要求 review 任務 001。

## 驗收清單
- [ ] java、javac、Maven 使用 Java 21。
- [ ] mvn verify 成功；記錄測試數量與 failures/errors。
- [ ] Spring Boot 可啟動。
- [ ] 可建立與讀取 Task。
- [ ] 重啟後 Task 仍存在。
- [ ] 學習紀錄與進度已推送至任務分支。

## 卡住時的處理
| 現象 | 先檢查 |
|---|---|
| 找不到 java、javac、mvn | PATH，安裝的是 JDK 或 JRE，重開終端 |
| invalid target release / release version not supported | mvn -version 實際使用的 JDK |
| 無法下載依賴 | 網路、公司 proxy、Maven 設定；不要關閉 TLS 驗證 |
| 找不到 pom.xml | 目前是否位於 repo 根目錄 |
| 8080 已被使用 | 用 mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"，查詢 URL 也改 8081 |
| migration/schema 或測試錯誤 | 保存錯誤，記錄為阻礙；不要刪 DB 或跳過測試掩蓋問題 |
| 無法 push | remote URL、GitHub 登入與權限 |

若程式本身出錯，記下重現步驟與你猜測的原因，完成能獨立完成的紀錄後交回 review。

## 思考題
1. java -version 正確，為什麼 mvn -version 仍可能使用另一個 Java？
2. 測試通過與應用可啟動，分別證明了什麼？
3. 為什麼不能把 data/ 的資料庫檔推到 GitHub？

範圍：只準備與驗證環境，不新增 domain、Redis、Kafka 或前端。
