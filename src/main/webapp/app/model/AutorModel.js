Ext.define('BM.model.AutorModel', {
            extend: 'Ext.data.Model',
            idProperty: 'autorId',
            proxy: {
                type: 'rest',
                url: '/autor'
            },
            fields: [
                'autorId',
                {
                    name: 'nume',
                    type: 'string'
                },
                {
                    name: 'dataNasterii',
                    type: 'date'
                }
            ]
        });