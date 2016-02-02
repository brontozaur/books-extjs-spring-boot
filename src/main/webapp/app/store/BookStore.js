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
            root: 'bookList',
            totalProperty: 'totalCount'
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
        console.log('store <<' + store.storeId + '>> was fully reloaded');
    }
}
});