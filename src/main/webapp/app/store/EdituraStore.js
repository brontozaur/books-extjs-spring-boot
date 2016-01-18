Ext.define('BM.store.EdituraStore', {
            extend: 'Ext.data.Store',
            model: 'BM.model.EdituraModel',
            proxy: {
                type: 'ajax',
                api: {
                    read: 'books?event=get-edituri'
                }
            },
            sorters: [
                {
                    property: 'numeEditura',
                    direction: 'ASC'
                }
            ],
            listeners: {
                load: function(store, records, success, operation, options) {
                    console.log('store <<' + store.storeId + '>> was fully reloaded');
                }
            }
        });