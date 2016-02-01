Ext.define('BM.model.CategorieModel', {
    extend: 'Ext.data.Model',
    idProperty: 'idCategorie',
    proxy: {
        type: 'rest',
        url: '/categorie'
    },
    fields: [
        'idCategorie',
        {
            name: 'numeCategorie',
            type: 'string'
        }
    ]
});