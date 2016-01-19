var itemsPerPage = 1;
Ext.define('BM.store.BookStore', {
            extend: 'Ext.data.Store',
            model: 'BM.model.BookModel',
            autoLoad: {
                params: {
                    start: 0,
                    limit: itemsPerPage
                }
            },
            pageSize: itemsPerPage,

            proxy: {
                type: 'ajax',
                api: {
                    read: 'book'
                }
            },
            sorters: [
                {
                    property: 'title',
                    direction: 'ASC'
                }
            ],

            listeners: {
                load: function(store, records, success, operation, options) {
//                    debugger;
                }
            }
        });