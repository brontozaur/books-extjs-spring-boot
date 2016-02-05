Ext.define('BM.store.BookStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.BookModel',
    autoLoad: false,
    pageSize: booksPerPage,

    proxy: {
        type: 'rest',
        url: '/book',
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
            property: 'title',
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