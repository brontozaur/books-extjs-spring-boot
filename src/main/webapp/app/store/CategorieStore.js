Ext.define('BM.store.CategorieStore', {
            extend: 'Ext.data.Store',
            model: 'BM.model.CategorieModel',
            proxy: {
                type: 'ajax',
                api: {
                    read: '/categorie'
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