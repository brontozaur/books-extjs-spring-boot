Ext.define('BM.store.AutorStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.AutorModel',
    pageSize: autoriPerPage,
    proxy: {
        type: 'rest',
        url: '/autor',
        reader: {
            type: 'json',
            root: 'content',
            totalProperty: 'totalElements'
        },
        listeners : {
            exception : function(proxy, response, operation) {
                createErrorWindow(response);
            }
        }
    },
    sorters: [
        {
            property: 'nume',
            direction: 'ASC'
        }
    ],
    listeners: {
        load: function (store, records, success, operation, options) {
            if (success) {
                console.log(store.storeId + " loaded " + records.length + ' records from a total of ' + store.getTotalCount());
            } else {
                console.log('error loading ' + store.storeId);
            }
        }
    }
});