Ext.define('BM.model.EdituraModel', {
    extend: 'Ext.data.Model',
    idProperty: 'idEditura',
    proxy: {
        type: 'rest',
        url: '/editura',
        reader: {
            type: 'json',
            rootProperty: 'edituraList'
        }
    },
    fields: [
        'idEditura',
        {
            name: 'numeEditura',
            type: 'string'
        }
    ]
});