
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