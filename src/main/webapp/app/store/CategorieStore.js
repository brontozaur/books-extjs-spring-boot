Ext.define('BM.store.CategorieStore', {
    extend: 'Ext.data.Store',
    model: 'BM.model.CategorieModel',
    pageSize: categoriiPerPage,
    proxy: {
        type: 'rest',
        url: '/categorie',
        reader: {
            type: 'json',
            root: 'categorieList',
            totalProperty: 'totalCount'
        }
    },
    sorters: [
        {
            property: 'numeCategorie',
            direction: 'ASC'
        }
    ],

    listeners: {
        load: function (store, records, success, operation, options) {
            console.log('store <<' + store.storeId + '>> was fully reloaded');
        }
    }
});