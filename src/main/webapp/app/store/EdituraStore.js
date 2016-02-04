Ext.define('BM.store.EdituraStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.EdituraModel',
    pageSize: edituriPerPage,
    proxy: {
        type: 'rest',
        url: '/editura',
        reader: {
            type: 'json',
            root: 'content',
            totalProperty: 'totalElements'
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
            if (success) {
                console.log(store.storeId + " loaded " + records.length + ' records from a total of ' + store.getTotalCount());
            } else {
                console.log('error loading ' + store.storeId);
            }
        }
    }
});