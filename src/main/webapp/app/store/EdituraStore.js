Ext.define('BM.store.EdituraStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.EdituraModel',
    pageSize: edituriPerPage,
    proxy: {
        type: 'rest',
        url: '/editura',
        reader: {
            type: 'json',
            root: 'edituraList',
            totalProperty: 'totalCount'
        }
    },
    sorters: [
        {
            property: 'numeEditura',
            direction: 'ASC'
        }
    ],
    listeners: {
        load: function (store, records, success, operation, options) {
            console.log('store <<' + store.storeId + '>> was fully reloaded');
        }
    }
});