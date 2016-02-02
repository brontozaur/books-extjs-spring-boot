Ext.define('BM.store.AutorStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.AutorModel',
    pageSize: autoriPerPage,
    proxy: {
        type: 'rest',
        url: '/autor',
        reader: {
            type: 'json',
            root: 'autorList',
            totalProperty: 'totalCount'
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
            console.log('store <<' + store.storeId + '>> was fully reloaded');
        }
    }
});