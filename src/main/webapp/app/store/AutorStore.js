Ext.define('BM.store.AutorStore', {
            extend: 'Ext.data.Store',
            model: 'BM.model.AutorModel',
            proxy: {
                type: 'ajax',
                api: {
                    read: 'books?event=get-autors'
                }
            },
            sorters: [
                {
                    property: 'nume',
                    direction: 'ASC'
                }
            ],
            listeners: {
                load: function(store, records, success, operation, options) {
                    console.log('store <<' + store.storeId + '>> was fully reloaded');
                }
            }
        });