
let HashMap = function() {

    let hashmap = {};

    return {
        put: function(key, value) {
            hashmap[key] = value;
        },
        keys: function() {
            return Object.keys(hashmap);
        },
        contains: function(key) {
            if (hashmap[key]){
                return true;
            }
            return false;
        },
        get: function(key) {
            return hashmap[key];
        },
        clear: function() {
            hashmap = {};
        },
        printout: function() {
            let text = '';
            for (const [key, value] of Object.entries(hashmap)) {
                text += `${key}: ${value}\n`;
            }
            return text;
        }
    };
};
/*
HashMap
1.建議直接用key-value 結構存取資料,而
非用index抓取
2.put方法內判key值不重複的話,無法更
新value(一般若key重複,value 會新蓋舊)
3.刪除無用程式碼
practice1
1.變數命名(id) 規範為小寫駝峰
2.未做檢核 key不可空白,建議設alert提
示使用者錯誤訊息(key不可空白、不可重
覆)
3.只使用一次可不用抽變數(line 20~22)
practice2
1.只使用一次可不用抽變數
2.條件判斷善用false family(line 122)
3.點擊新增未加製造商、類別不可空白錯誤
訊息,檢核空白可用.trim()
4.點擊清空未清除上方輸入欄位
5.點擊修改未檢核製造商與類別不可空白
6.radio 應修改為單選
7.點擊 radio未帶入修改後新資料
*/