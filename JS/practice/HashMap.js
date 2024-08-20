
let HashMap = function() {
    
    let keys = [];
    let values = [];

    const contains = function(key) {
        if (keys.indexOf(key) != -1) {
            return true;
        }
        return false;
    }

    return {
        put: function(key, value) {
            if(!contains(key)) {
                keys.push(key);
                values.push(value);
            }
        },
        keys: function() {
            return keys;
        },
        contains: contains,
        get: function(key) {
            if (contains(key)) {
                return values[keys.indexOf(key)];
            }
            return undefined;
        },
        clear: function() {
           keys = [];
           values = [];
        },
        printout: function() {
            let text = '';
            for(let i = 0; i < keys.length; i++) {
                text += keys[i] + ':'+ values[i] + '\n';
            }
            return text;
        }
    };
};