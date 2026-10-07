# 任務學習紀錄
任務編號：001
日期：2026-10-07
狀態： 待 review
分支：task/001-environment

## 執行環境
- 作業系統：
- git --version：
```bash
git version 2.34.1.windows.1
```
- java -version：
```bash
openjdk version "21.0.12.1" 2026-08-18 LTS
OpenJDK Runtime Environment Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS, mixed mode, sharing)
```
- javac -version：
```bash
javac 21.0.12.1
```
- mvn -version（Maven 與 Java 版本）：
```bash
Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)
Maven home: C:\Users\FG9196\git\apache-maven-3.10.0
Java version: 21.0.12.1, vendor: Eclipse Adoptium, runtime: C:\Users\FG9196\git\OpenJDK21U-jdk_x64_windows_hotspot_21.0.12.1_1\jdk-21.0.12.1+1
Default locale: zh_TW, platform encoding: UTF-8, time zone: Asia/Taipei
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```
## 驗收證據
- mvn verify 結果（測試數量、failures/errors、BUILD SUCCESS 或錯誤摘要）：
```bash
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.023 s -- in com.lifeos.task.TaskApiTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ lifeos ---
[INFO] Building jar: C:\Users\FG9196\git\LifeOS\target\lifeos-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot:3.5.16:repackage (repackage) @ lifeos ---
[INFO] Replacing main artifact C:\Users\FG9196\git\LifeOS\target\lifeos-0.0.1-SNAPSHOT.jar with repackaged archive, adding nested dependencies in BOOT-INF/.
[INFO] The original artifact has been renamed to C:\Users\FG9196\git\LifeOS\target\lifeos-0.0.1-SNAPSHOT.jar.original
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  18.409 s
[INFO] Finished at: 2026-10-07T13:33:09+08:00
```
- 啟動結果：
```bash
2026-10-07T13:34:33.187+08:00  INFO 35424 --- [lifeos] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2026-10-07T13:34:34.231+08:00  INFO 35424 --- [lifeos] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-10-07T13:34:34.241+08:00  INFO 35424 --- [lifeos] [           main] com.lifeos.LifeOsApplication             : Started LifeOsApplication in 5.509 seconds (process running for 6.022)
```
- 建立與查詢 Task：
http://127.0.0.1:8080/api/v1/tasks/6c324bc8-0caf-4e7e-ac42-c0b1adf70c36
{
    "id": "6c324bc8-0caf-4e7e-ac42-c0b1adf70c36",
    "title": "完成 LifeOS 環境任務",
    "description": "在工作電腦完成驗收",
    "status": "TODO",
    "dueDate": null,
    "createdAt": "2026-10-07T05:11:15.688477Z",
    "updatedAt": "2026-10-07T05:11:15.688477Z"
}
- 重啟後查詢結果：
{
    "id": "6c324bc8-0caf-4e7e-ac42-c0b1adf70c36",
    "title": "完成 LifeOS 環境任務",
    "description": "在工作電腦完成驗收",
    "status": "TODO",
    "dueDate": null,
    "createdAt": "2026-10-07T05:11:15.688477Z",
    "updatedAt": "2026-10-07T05:11:15.688477Z"
}
## 修改內容與原因
無修改

## 阻礙與嘗試
重現命令、錯誤、你已嘗試的方法。勿貼 token、公司資料或秘密。
建置VS環境的Setting花了不少時間，因為要區分公司環境的JAVA版本，
讓VS CODE以及build in vs code的terminal都使用java21

## 思考題回答
1. Java 與 Maven 使用不同版本的原因：
```bash
核心差異：java -version 是作業系統 PATH 變數預設指向的 JDK；而 Maven（mvn）有自己的環境變數與指令檔，會優先讀取 JAVA_HOME。

常見原因：

變數未同步：系統設定了 JAVA_HOME 指向 JDK 21，但系統 PATH 中有舊版 Java（例如 JDK 17 或 11）排在前面。

Maven 工具建置（Toolchains）設定：Maven 可以透過 toolchains.xml 指定編譯時使用的 JDK，不受系統全域 java 命令影響。

IDE 或多套 JDK 共存：電腦安裝了多套 JDK，不同工具抓取的環境變數順序不同。
```
2. 測試與啟動各自證明的事情：
```bash
測試成功（mvn verify / mvn test）證明：

單元與整合邏輯正確性：程式碼的商業邏輯、資料庫 mappings（JPA/Entity）、與測試案例預期的結果一致。

靜態建置品質：程式碼可被正常編譯、依賴關係（Dependencies）無衝突，且符合自動化驗收條件。

啟動成功（mvn spring-boot:run / 執行 Application）證明：

運行時環境（Runtime Environment）健全：應用程式的 Context（Spring ApplicationContext）能成功載入，Bean 的注入與設定無誤。

外部資源連線：服務開埠（如 Port 8080）正常、資料庫實際連線與初始化（Schema Migration/H2 啟動）順利，且能在伺服器環境中持續監聽與接收 HTTP 請求。
```
3. 不提交 data/ 的原因：
```bash
資料安全與隱私（Security & Privacy）：data/ 通常存放本機測試或開發時產生的 SQLite/H2 資料庫檔案，可能包含敏感資料、個人測試帳密或公司機密。

環境隔離與獨立性（Environment Isolation）：資料庫內容屬於「動態產生的狀態」，不同開發者或 CI/CD pipeline 應該各自擁有乾淨獨立的測試資料，提交資料庫檔會導致團隊成員間的資料衝突（Git Conflict）。

版本庫肥大（Repository Bloat）：資料庫檔案屬於二進位檔（Binary），每次修改都會產生極大的變動紀錄，提交會導致 Git 版控庫容量迅速膨脹、降低 git pull/clone 效率。
```


## 給 Codex 的問題
可以把AI使用也加進練習中，像是如何使用以及CODEX如何協助開發的部分
```
ssh方式可以把公司電腦完成的作業上傳
# 將 remote url 切換成 SSH 格式
git remote set-url origin git@github.com:cwhmartin1000-sketch/LifeOS.git

# 驗證 remote 網址是否已更新
git remote -v
````