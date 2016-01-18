Ext.define('BM.store.TreeAutoriStore', {
            extend: 'Ext.data.TreeStore',
            model: 'BM.model.TreeAutoriModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: 'books?event=get-tree-autori',
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