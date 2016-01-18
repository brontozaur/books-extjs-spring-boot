Ext.define('BM.store.CategorieStore', {
            extend: 'Ext.data.Store',
            model: 'BM.model.CategorieModel',
            proxy: {
                type: 'ajax',
                api: {
                    read: 'books?event=get-categorii'
                }
            },
            sorters: [
                {
                    property: 'numeCategorie',
                    direction: 'ASC'
                }
            ],

            listeners: {
                load: function(store, records, success, operation, options) {
                    console.log('store <<' + store.storeId + '>> was fully reloaded');
                }
            }
        });