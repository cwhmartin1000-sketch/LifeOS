# 產品與學習目標
LifeOS 是 Personal Growth Operating System，不只記錄打勾，而是形成「行動 → 證據 → 回顧 → 下一個挑戰」的持續成長循環。
產品價值：建立習慣、知道練了什麼、追蹤能力證據、得到下一步建議、重新投入已會的能力。
學習价值：能解釋 Java/Spring 的交易、資料一致性、效能、可靠性、安全與架構選擇，達到 Senior Backend 的判斷能力。

## 第一版 domain
| Domain | 責任 | 未來可能關係（未實作） |
|---|---|---|
| Goal | 可衡量的成長目標與回顧 | 分解為 Habit、Task |
| Habit | 重複行動與完成紀錄 | 支持 Goal |
| Task | 一次性的可完成行動 | 可支持 Goal / Skill |
| Skill | 能力、練習歷史與證據 | 由 Practice Log 支持 |
| Practice Log | 練習主題、時間、結果、錯誤、心得與證據 | 可關聯 Skill / Task |

不要現在替所有關係建 FK、表或 endpoint；逐個 sprint 確認需求再做。
XP、技能樹、理財、AI Coach 都非 Sprint 1。AI 要等有可用資料與評估方式後才導入。

## Sprint 1 暫定假設
本機單人使用、後端 API 優先、無登入。Task 是獨立 aggregate，僅標題、描述、狀態、期限與時間戳。
三種狀態可任意切換，以支持重新開始；不自動計算 Goal / Skill 進度。
多使用者、前端形式、Habit 次數/時間型態、Skill 升級證據與 AI 主動程度仍待確認，不默認為已決需求。
