# API說明
### swagger ui頁面：http://localhost:8080/swagger-ui.html
* [GET] /api/coindesk 呼叫COINDESK API,更新DB資料,並將Response Body紀錄至app.log. 排程每五分鐘執行一次
* [GET] /api/currency 新增貨幣資訊
* [POST] /api/currency 取得所有貨幣資訊
* [PUT] /api/currency/{code} 更新貨幣匯率
* [DELETE] /api/currency/{code} 刪除貨幣資訊

### 創建table sql
```sql
DROP TABLE IF EXISTS CURRENCY;

CREATE TABLE CURRENCY (
  ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  CODE VARCHAR(250),
  CHINESE_NAME VARCHAR(250),
  SYMBOL VARCHAR(250),
  RATE NUMERIC(100000,3),
  DESCRIPTION VARCHAR(250),
  UPDATE_TIME TIMESTAMP
);
```
