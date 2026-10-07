# 任務學習紀錄
任務編號：
日期：
狀態：進行中 / 待 review
分支：

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
2. 測試與啟動各自證明的事情：
3. 不提交 data/ 的原因：

## 給 Codex 的問題
可以把AI使用也加進練習中，像是如何使用以及CODEX如何協助開發的部分