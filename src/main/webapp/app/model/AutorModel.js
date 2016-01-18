Ext.define('BM.model.AutorModel', {
            extend: 'Ext.data.Model',
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