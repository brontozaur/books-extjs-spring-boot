Ext.define('BM.model.CategorieModel', {
            extend: 'Ext.data.Model',
            fields: [
                'idCategorie',
                {
                    name: 'numeCategorie',
                    type: 'string'
                }
            ]
        });