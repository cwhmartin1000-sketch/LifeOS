# 使用 GitHub 在兩台電腦間交付
GitHub 保存 LifeOS 程式碼、講義與學習紀錄。個人電腦準備任務；工作電腦實作；回到 Codex 後 review。
只傳遞本專案內容，不包含公司程式、資料或憑證。

## 日常流程
1. 在乾淨 main 上 git pull --ff-only。
2. 每個任務建立自己的 task/NNN-topic 分支。
3. 閱讀 docs/tasks/ 中的講義，完成程式與 worklog。
4. 查看 git diff，提交並推送任務分支。
5. 建立 PR，回到 Codex 提供 URL 進行 review。
6. review 與合併後，兩台電腦更新 main，再開始下一個任務。

不要兩台電腦同時編輯相同未提交內容；切換電腦之前 commit/push。
Git author 與 GitHub 登入是不同設定：作者記錄 commit；登入決定能否 push。
未指定公開展示前建議 private repo，後續整理完成再評估公開。

## 當前發布狀態
本機 repo 已初始化；尚未設定 remote、建立 commit 或推送 GitHub。
完整 16 章講義目前在聊天中；此次先發布可執行的任務 001。
