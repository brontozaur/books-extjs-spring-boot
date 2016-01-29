Ext.define('BM.store.TreeAutoriStore', {
            extend: 'Ext.data.TreeStore',
            model: 'BM.model.TreeAutoriModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: 'autor/tree',
                reader: {
                    type: 'json',
                    method: 'POST'
                }
            },
            root: {
                expanded: true,
                name: 'Autori'
            },
            sorters: [
                {
                    property: 'treeItemName',
                    direction: 'ASC'
                }
            ]
        });