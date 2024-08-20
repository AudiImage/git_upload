
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
    };
};