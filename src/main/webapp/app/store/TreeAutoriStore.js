Ext.define('BM.store.TreeAutoriStore', {
            extend: 'Ext.data.TreeStore',
            model: 'BM.model.TreeAutoriModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: 'autor/tree-load',
                reader: {
                    type: 'json',
                    method: 'POST'
                }
            },
            root: {
                expanded: true,
                name: 'AUTORI'
            },
            sorters: [
                {
                    property: 'treeItemName',
                    direction: 'ASC'
                }
            ]
        });